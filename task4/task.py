import random
import operator
import math
import time

"""
Helper function for the whole file
"""
def is_sorted(op, lst):
    if len(lst) <= 1:
        return True

    prev = lst[0]
    iterator = iter(lst)
    next(iterator)
    for elem in iterator:
        if not op(prev, elem):
            return False
        prev = elem

    return True

# SECTION task 1

# SUBSECT 1a

"""
Insertionsosrt. Helper function for Shellsort below.
"""
def jump_InsertSort(A, start, step):
    for i in range(start+step, len(A), step):
        value = A[i]
        j = i
        while  j >= step  and  A[j-step] > value:
            A[j] = A[j-step]
            j = j - step
        A[j] = value

"""
Sorts a list using Shellsort.
For the segment-size, prime numbers are used instead of half of the size of the list.

"""
def shellsort(lst):
    magic = (1391376, 463792, 198768, 86961, 33936, 13776, 4592, 1968, 861, 336, 112, 48, 21, 7, 3, 1)
    magic_idx = 0
    
    # Find biggest number in the magic list that can work for the algorithm.
    for i in range(0, len(magic)):
        magic_idx = i
        if magic[i] <= len(lst):
            break
    magic_idx -= 1

    # Check if the list is to big
    if magic_idx < 0:
        print("list to big.")
        return
    
    # Iterate through the numbers in the magic list, 
    # starting at the index calculated above.
    while magic_idx < len(magic):
        for start_i in range(magic[magic_idx]):
            jump_InsertSort(lst, start_i, magic[magic_idx])
        magic_idx += 1
    
    return lst

"""
Eine alternative Implementation, die besser ist als die obige,
sich aber aber nicht an der Vorlesung orientiert:

def shellsort(lst):
    magic = (1391376, 463792, 198768, 86961, 33936, 13776, 4592, 1968, 861, 336, 112, 48, 21, 7, 3, 1)
    
    for mi in range(0, len(magic)):
        num = magic[mi]
        for i in range(num, len(lst)):
            j = i
            t = lst[j]
            while j >= num and lst[j-num] > t:
                lst[j] = lst[j-num]
                j -= num
            lst[j] = t
    return lst
"""



# SUBSECT 1b

"""
Performs insertionsort and counts comparison operations (<, >, <=, >=)
"""
def jump_insert_sort_ops(A, start, step):
    cmp_ops = 0
    for i in range(start+step, len(A), step):
        value = A[i]
        j = i
        while  j >= step  and  A[j-step] > value:
            cmp_ops += 2
            A[j] = A[j-step]
            j = j - step
        A[j] = value
    return cmp_ops
    
"""
Performs Shellsort and counts comparison operations (<, >, <=, >=).
Internally, insertionsort is used. The comparisons performed in insertionsort 
are counted in the total as well.
"""
def shellsort_ops(A):
    cmp_ops = 0
    seg_size = len(A)//2 
    while seg_size > 0:
        cmp_ops += 1
        for start_i in range(seg_size):
            cmp_ops += jump_insert_sort_ops(A, start_i, seg_size)
        seg_size = seg_size // 2
    return cmp_ops

# SUBSECT 1c

"""
Insertion sort.
Returns the numer of comparisons that were performed while sorting the list.
This is a helper-function for comp_shellsort_insertionsort_ops_helper(...)
"""
def insertion_sort_ops(lst):
    ops = 0
    for i in range(1, len(lst)):
        key = lst[i]
        j = i-1
        while j >=0 and key < lst[j] :
            ops += 2
            lst[j+1] = lst[j]
            j -= 1
        lst[j+1] = key
    return ops

"""
Sorts a list using insertionsort and then shellsort and returns a Tuple of the number of operations.
Both algorithms should work on even terms because the list is copied beforehand. The original is sorted via. insertionsort and the copy using shellsort.
This is a helper function for comp_shellsort_insertionsort_ops(...)
"""
def comp_shellsort_insertionsort_ops_helper(lst):
    l1 = lst[:] # Copy
    is_ops = insertion_sort_ops(lst) # Insertionsort
    ss_ops = shellsort_ops(l1) # Shellsort
    # Tuple containing the number of comparisons in Insertionsort and Shellsort
    return (is_ops, ss_ops)

"""
Takes a list and prints a table of the operations that 
Insertionsort and Shellsort needed to sort the list. 
After that, sorted, shuffled, reversed and other variants of the 
list afte tested too.
"""
def comp_shellsort_insertionsort_ops(lst):
    is_ops, ss_ops = comp_shellsort_insertionsort_ops_helper(lst)
    print("             | Shellsort | Insertion")
    print("-------------+-----------+----------")
    print("original     | {:9d} | {:9d}".format(ss_ops, is_ops))
    is_ops, ss_ops = comp_shellsort_insertionsort_ops_helper(lst)
    print("sorted       | {:9d} | {:9d}".format(ss_ops, is_ops))
    random.shuffle(lst)
    is_ops, ss_ops = comp_shellsort_insertionsort_ops_helper(lst)
    print("shuffled     | {:9d} | {:9d}".format(ss_ops, is_ops))
    lst[0], lst[-1] = lst[-1], lst[0]
    is_ops, ss_ops = comp_shellsort_insertionsort_ops_helper(lst)
    print("first<=>last | {:9d} | {:9d}".format(ss_ops, is_ops))
    lst = list(reversed(lst))
    is_ops, ss_ops = comp_shellsort_insertionsort_ops_helper(lst)
    print("reversed     | {:9d} | {:9d}".format(ss_ops, is_ops))
    lst = lst*2
    random.shuffle(lst)
    is_ops, ss_ops = comp_shellsort_insertionsort_ops_helper(lst)
    print("2*size       | {:9d} | {:9d}".format(ss_ops, is_ops))


# SECTION task 2

# TODO
# Funktionen in task 2 sollen mit einer Heap-Struktur eine Priority-Queue simulieren.

# SUBSECT 2a
# TODO Zeigen, dass Heapsort nicht stabil ist

# SUBSECT 2b
def next_message():
    while True:
        priority = random.randint(0, 1) # O(log(N)) where N is the number of bits in 50; But depends on implementation
        timestamp = time.time()
        yield (priority, timestamp, "")

# SUBSECT 2c

# SUBSECT Helpers for heap-structure

def  heap_size(H):
    return H[0]

def  dec_heap_size(H):
    H[0] = H[0]-1

def  inc_heap_size(H):
    H[0] = H[0]+1

def parent(i):
    return i//2

def left(i):
    return i*2

def right(i):
    return i*2+1

"""
Comparison function for 2 message-tuples.

if the priorities (at m1[0] and m2[0]) are equal, the timestamps (at m1[1] and m2[1]) are compared.
"""
def prio(m1, m2):
    if m1[0] == m2[0]:
        if m1[1] == m2[1]:
            return 0
        else:
            return -1 if m1[1] < m2[1] else 1
    else:
        return -1 if m1[0] < m2[0] else 1

def max_heapify(H, pos):
    left_t = left(pos)
    right_t = right(pos)
    #if left_t <= heap_size(H) and (H[left_t][0] > H[pos][0] or (H[left_t][0] == H[pos][0] and H[right_t][1] > H[pos][1])):
    if left_t <= heap_size(H) and prio(H[left_t], H[pos]) > 0:
        biggest = left_t
    else:
        biggest = pos
    
    #if right_t <= heap_size(H) and (H[right_t][0] > H[pos][0] or (H[right_t][0] == H[pos][0] and H[right_t][1] > H[pos][1])):
    if right_t <= heap_size(H) and prio(H[right_t], H[pos]) > 0:
        biggest = right_t
        
    if biggest != pos:
        H[pos], H[biggest] = H[biggest], H[pos]
        max_heapify(H, biggest)

def build_max_heap(H):
    H[0] = len(H) - 1
    for i in range(heap_size(H) // 2, 0, -1):
        max_heapify(H, i)

def peek(pqueue):
    if is_empty(pqueue):
        return None
    return pqueue[1]

def reorder_for_last_message(pqueue):
    index = heap_size(pqueue)
    par_idx = parent(index)
    #while index > 1 and (pqueue[par_idx][0] < pqueue[index][0] or (pqueue[par_idx][0] == pqueue[index][0] and pqueue[par_idx][1] < pqueue[index][1])):
    while index > 1 and prio(pqueue[par_idx], pqueue[index]) < 0:
        pqueue[index], pqueue[par_idx] = pqueue[par_idx], pqueue[index]
        index = par_idx
        par_idx = parent(index)

# SUBSECT Priority-queue functions

"""
message is a Tuple of priority and timestamp
"""
def insert(pqueue, message):
    pqueue.append(message)
    inc_heap_size(pqueue)
    pqueue[heap_size(pqueue)] = message
    reorder_for_last_message(pqueue)
    return pqueue

def is_empty(pqueue):
    return heap_size(pqueue) == 0

def delete(pqueue):
    if is_empty(pqueue):
        return None

    result = pqueue[1]
    pqueue[1] = pqueue[heap_size(pqueue)]
    dec_heap_size(pqueue)
    max_heapify(pqueue, 1)
    return result

# Messagess are sorted on insertion or deletion.
def sort_messages(pqueue):
    hsize = heap_size(pqueue)
    
    if hsize < 2:
        return pqueue

    cpqueue = pqueue[1:hsize]
    gap = hsize// 2
    while gap > 0:
        for i in range(gap, hsize-1):
            temp = cpqueue[i]
            j = i
            while j >= gap and prio(cpqueue[j-gap], temp) < 0:
                cpqueue[j] = cpqueue[j-gap]
                j -= gap
            cpqueue[j] = temp
        gap //= 2

    pqueue[1:hsize] = cpqueue
    
    return pqueue

# SUBSECT 2d

# TODO
def sim_message_traffic():
    
    time.sleep(random.random())
    
    
    return None

# SUBSECT 2e

# TODO Complexity of next_message
# TODO Complexity of insert
# TODO Complexity of is_empty
# TODO Complexity of delete
# TODO Complexity of sort_messages
# TODO Complexity of sim_message_traffix


# SECTION task 3

# SUBSECT 3a

def counting_sort_in_place(lst, k):
    C = [0] * k               # k
    for a in lst:
        C[a] += 1             # n*2
    i = 0
    for a in range(k):
        for c in range(C[a]):
            lst[i] = a        # n*k*2
            i += 1            # n*k*1

# SUBSECT 3b

"""
Komplexität:
Von oben zusammengezählt:
  k + n*(1+1) + n*k*(1+1) + n*k*1
  k + 2*n + 2*(k*n) + n*k
  k + 2*n + 3*(k*n)
  3*k*n
  O(3*k*n)
  
  Ergebnis: O(k*n)
"""

# SECTION task 4

"""
Als erstes fällt auf, dass für so große Mengen an ganzen Zahlen vergleichslose Sortier-Algorithmen eignen. 

Aus einem kurzen, wenig wissenschaftlichen Test, haben wir anhand von 2048 Zahlen (im Bereich 0 bis 1000) die Algorithmen "Counting Sort", "Pigeonhole Sort" und "Radix Sort" getestet.
Alle Algorithmen schnitten gut ab. Bei Erhöhung der Zahlenmenge auf eine Millionen Elementemit einem höheren Zahlenbereich stürzten Counting Sort und Radix Sort aber bald ab. Eine alternative Implementation von Radix Sort, die bei Listen bis zu 2000 Elementen auch gut war, funktionierte, benötigte aber zu lange.
Schon bei einer Listen-Größe von einer Million Elementen im Bereich -2**28 bis 2**28 funktionierte also nur noch Pigeonholesort (auch wenn es ein paar Sekunden benötigte).
Beim Test von vollen 32-Bit Zahlen gaben unsere Python3-Implementationen leider auf.

Während das Internet Radix-Sort bevorzugt, konnte bei uns also nur Pigeonholesort die Anforderungen erfüllen.

Probleme beim Pigeonhole Sort sind, dass er
  1. Sehr stark von der Differenz zwischen den Werten abhängig ist
  2. Bei sortierten Listen keinen Vorteil bietet. (Auch hier domiert 1.)
  3. Bei leeren Listen nicht funktioniert
     (Dieses Problem ist hier unwichtig und ist leicht zu beheben)
"""

num_min = int(-(2**28) * 1.5) #int(-(2**28) * 1.5)
num_max = -num_min #0x7FFFFFFF
k = 10000000
r = range(0, k) # range(0, 1000000)

"""
Pigeonhole Sort.
Input:
    a Multible sequential collection
Output:
    Sorted list (a is sorted in-place)
"""
def pigeonhole_sort(a): 
    my_min = min(a) 
    my_max = max(a) 
    size = my_max - my_min + 1
  
    holes = [0] * size 
  
    for x in a:
        holes[x - my_min] += 1
  
    i = 0
    for count in range(size):
        while holes[count] > 0:
            holes[count] -= 1
            a[i] = count + my_min
            i += 1
    return a

"""
Die Zeitkomplexität des Algorithmus ist O(n+m) wobei n die Länge der Input-Liste ist und m die Differenz zwischen dem kleinsten und dem größten Wert in der Liste.
Die Space-Komplexität ist ebenso auch O(n+m), da eine neue Liste der Größe m generiert wird.

Komplexität: O(n+m)

def pigeonhole_sort(a): 
    my_min = min(a)                   # 1
    my_max = max(a)                   # 1
    size = my_max - my_min + 1        # 2
    holes = [0] * size                # 2 (Time complexity O(2) aber Space O(m))
  
    for x in a:                     # n
        holes[x - my_min] += 1        # n * 3 (3 operationen)
  
    i = 0                             # 1
    for count in range(size):       # m
        while holes[count] > 0:     # k
            holes[count] -= 1         # m * k * 2
            a[i] = count + my_min     # m * k * 2
            i += 1                    # m * k * 1
    return a
    
                                      # O(6 + 3n + 5mk)
                                      # O(n + mk)
                                      # O(n + m) (da m in m*k der dominierende Ausdruck ist)
                                      # O(n+m)
"""

"""
lst = [random.randint(num_min, num_max) for _ in r]
t0 = time.time()
lst = pigeonhole_sort(lst)
t1 = time.time()
print("pigeonhole_sort:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

t0 = time.time()
lst = pigeonhole_sort(lst)
t1 = time.time()
print("pigeonhole_sort:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

lst = [random.randint(0, num_max) for _ in r]
t0 = time.time()
lst = pigeonhole_sort(lst)
t1 = time.time()
print("pigeonhole_sort:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

lst = [random.randint(num_min, 0) for _ in r]
t0 = time.time()
lst = pigeonhole_sort(lst)
t1 = time.time()
print("pigeonhole_sort:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

lst = [0 for _ in r]
t0 = time.time()
lst = pigeonhole_sort(lst)
t1 = time.time()
print("pigeonhole_sort:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))
"""

##################################################
# SECTION Tests                                  #
##################################################

"""
Helper function for sorting algorithms that work on full numbers.
It tests a variety of 
"""
def ensure_sorting_works(sorting_fn_name, sorting_fn):
    """
    def esw_sub(n_min, n_max, rng, shuffler):
        l = []
        if rng:
            l = [random.randint(n_min, n_max) for _ in range(n_min, n_max)]
        else:
            l = list(range(n_min, n_max))
        
        if shuffler is not None:
            shuffler(l)

        if is_in_place:
            sorting_fn(l)
        else:
            l = sorting_fn(l)
        print("    Size:", len(l), "Sorted?", is_sorted(operator.le, l))
    """
    
    def show_message(l):
        print("    Size:", len(l), "Sorted?", is_sorted(operator.le, l))
    
    print("Testing algorithm:", sorting_fn_name)
    print("----------------------------------------------")
    
    print("  Testing on simple lists.")
    
    #l = []
    #sorting_fn(l)
    #show_message(l)
    
    l = [17]
    sorting_fn(l)
    show_message(l)
    
    print("  Testing on shuffled lists of uniques.")
    n = 10
    while n <= 10000:
        l = list(range(0, n))
        random.shuffle(l)
        sorting_fn(l)
        show_message(l)
        n *= 10
    
    print("  Testing on shuffled lists.")
    n = 10
    while n <= 10000:
        l = [random.randint(0, n) for _ in range(0, n)]
        random.shuffle(l)
        sorting_fn(l)
        show_message(l)
        n *= 10
    
    print("  Testing on shuffled lists (including < 0).")
    n = 10
    while n <= 10000:
        l = [random.randint(-(n//2), n//2) for _ in range(0, n)]
        random.shuffle(l)
        sorting_fn(l)
        show_message(l)
        n *= 10

    print("  Testing on pre-sorted lists of uniques.")
    n = 10
    while n <= 10000:
        l = list(range(0, n))
        sorting_fn(l)
        show_message(l)
        n *= 10

    print("  Testing on pre-sorted lists.")
    n = 10
    while n <= 10000:
        l = [random.randint(0, n) for _ in range(0, n)]
        sorting_fn(l)
        show_message(l)
        n *= 10

    print("  Testing on list with first and last swapped.")
    n = 10
    while n <= 10000:
        l = list(range(0, n))
        l[0], l[-1] = l[-1], l[0]
        sorting_fn(l)
        show_message(l)
        n *= 10

    print("  Testing on reversed list.")
    n = 10
    while n <= 10000:
        l = list(reversed(range(0, n)))
        l[0], l[-1] = l[-1], l[0]
        sorting_fn(l)
        show_message(l)
        n *= 10

def test_shellsort_with_magic():
    ensure_sorting_works("Shellsort with magic", shellsort)
    print("Operations needed for 0 Elements:")
    l = []
    print("  Sorted:", shellsort_ops(l))
    random.shuffle(l)
    print("  Shuffled:", shellsort_ops(l))
    print("Operations needed for 10 Elements:")
    l = list(range(0, 10))
    print("  Sorted:", shellsort_ops(l))
    random.shuffle(l)
    print("  Shuffled:", shellsort_ops(l))
    print("Operations needed for 1024 Elements:")
    l = list(range(0, 1024))
    print("  Sorted:", shellsort_ops(l))
    random.shuffle(l)
    print("  Shuffled:", shellsort_ops(l))
    print("Operations needed for 2048 Elements:")
    l = list(range(0, 2048))
    print("  Sorted:", shellsort_ops(l))
    random.shuffle(l)
    print("  Shuffled:", shellsort_ops(l))

def test_shellsort_ops():
    ensure_sorting_works("Shellsort without magic", shellsort_ops)

def test_insertion_sort_ops():
    ensure_sorting_works("Insertionsort", insertion_sort_ops)

def test_comp_shellsort_insertionsort_ops():
    print("Testing comp_shellsort_insertionsort_ops")
    print("Numbers 0...1000:")
    comp_shellsort_insertionsort_ops(list(range(0, 1000)))
    print("Numbers -100...1000:")
    comp_shellsort_insertionsort_ops(list(range(-1000, 1000)))
    print("Numbers -1000...0:")
    comp_shellsort_insertionsort_ops(list(range(-1000, 0)))

def test_next_message():
    generator = next_message()
    num_msgs = 500

    print("Now generating", num_msgs, "messages.")
    msgs = [next(generator) for _ in range(0, num_msgs)]
    print("Number of elements correct?   ", len(msgs) == num_msgs)
    msg_types_correct = all(isinstance(m, tuple) for m in msgs)
    prio_type_correct = all(isinstance(m[0], int) for m in msgs)
    time_type_correct = all(isinstance(m[1], float) for m in msgs)
    text_type_correct = all(isinstance(m[2], str) for m in msgs)
    print("  Type is correct?            ", msg_types_correct)
    print("  Type of priority is correct?", prio_type_correct)
    print("  Type of time is correct?    ", time_type_correct)
    print("  Type of text is correct?    ", text_type_correct)

"""
test_peek()
test_reorder_for_last_message()
test_insert()
test_is_empty()
test_delete()
test_sort_messages()
"""
def test_priority_queue_functions():
    print("Initializing empty queue.")
    pqueue = [0 for _ in range(0, 500)]
    print("  Size  =", heap_size(pqueue))
    print("  Empty?=", is_empty(pqueue))
    print("  Peek  =", peek(pqueue))
    print("  Delete=", delete(pqueue))
    pq2 = pqueue[:]
    sort_messages(pq2)
    print("  Sorted?", pqueue == pq2)
    
    msg_generator = next_message()
    
    print("Insert 5 new messages")
    for _ in range(0, 5):
        insert(pqueue, next(msg_generator))
    
    print("Current:")
    print(pqueue[0:heap_size(pqueue)])

    print("  Size  =", heap_size(pqueue))
    print("  Empty?=", is_empty(pqueue))
    print("  Peek  =", peek(pqueue))
    pq2 = pqueue[:]
    sort_messages(pq2)
    print("  Sorted?", pqueue == pq2)
    
    print("Deleting first element:")
    print("  Delete=", delete(pqueue))
    print("  Size  =", heap_size(pqueue))
    print("  Empty?=", is_empty(pqueue))
    print("  Peek  =", peek(pqueue))
    print("Deleting second element:")
    print("  Delete=", delete(pqueue))
    print("  Size  =", heap_size(pqueue))
    print("  Empty?=", is_empty(pqueue))
    print("  Peek  =", peek(pqueue))
    print("Deleting third element:")
    print("  Delete=", delete(pqueue))
    print("  Size  =", heap_size(pqueue))
    print("  Empty?=", is_empty(pqueue))
    print("  Peek  =", peek(pqueue))
    pq2 = pqueue[:]
    sort_messages(pq2)
    print("  Sorted?", pqueue == pq2)
    
    print("Insert 5 new messages")
    for _ in range(0, 5):
        insert(pqueue, next(msg_generator))
    
    print("Current:")
    print(pqueue[0:heap_size(pqueue)])
    
    print("  Size  =", heap_size(pqueue))
    print("  Empty?=", is_empty(pqueue))
    print("  Peek  =", peek(pqueue))
    print("  Delete=", delete(pqueue))
    pq2 = pqueue[:]
    sort_messages(pq2)
    print("  Sorted?", pqueue == pq2)
    
    print(pq2[0:heap_size(pqueue)])
    print(pqueue[0:heap_size(pqueue)])

def test_sim_message_traffic():
    sim_message_traffic()

def test_counting_sort_in_place():
    lm = lambda l: counting_sort_in_place(l, 100000)
    ensure_sorting_works("Counting sort in place", lm)
    
def test_pigeonhole_sort():
    ensure_sorting_works("Pigeonhole Sort", pigeonhole_sort)

if __name__ == '__main__':
    print("\n### Testing task 1a ###")
    test_shellsort_with_magic()
    print("\n### Testing task 1b ###")
    test_shellsort_ops()
    print("\n### Testing task 1c ###")
    test_insertion_sort_ops()
    print("")
    test_comp_shellsort_insertionsort_ops()
    print("\n### Testing task 2b ###")
    test_next_message()
    print("\n### Testing task 2c ###")
    test_priority_queue_functions()
    print("\n### Testing task 2d ###")
    test_sim_message_traffic()
    print("\n### Testing task 3  ###")
    test_counting_sort_in_place()
    print("\n### Testing task 4  ###")
    test_pigeonhole_sort()
