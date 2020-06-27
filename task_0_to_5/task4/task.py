import random
import operator
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
        while j >= 0 and key < lst[j] :
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

# SUBSECT 2a
"""
Heapsort lässt sich aufteilen in die Erstellung des Heaps und das Einfügen
in einen neuen Array.
1. Teil:
  Input: [1, 5, 2(2), 3, 2(4), 6, 2(6)] (Zahlen in Klammern bezeichnen 
  den Index im Input)
  Nach der Erstellung des Heaps wird die Liste so aussehe:
  [1, 2(4), 2(2), 3, 5, 6, 2(6)]
  2(2) und 2(4) wurden hier schon vertauscht. Wichtiger ist aber, 
  ob sie im 2. Teil wieder die richtige Reihenfolge erhalten.
2. Teil
  Das kleinste Element (hier 1) wird mit dem letzten Element vertauscht 
  und in einen zusätzlichen Array gepackt.
  [2(6), 2(4), 2(2), 3, 5, 6]
  Das wird nun mit jedem Element wirderholt und ergibt am Ende
  [1, 2(6), 2(4), 2(2), 3, 5, 6]
  Das ist auch die Ausgabe.
Da die Reihenfolge von 2(2), 2(4) und 2(6) sich geändert hat, ist Heapsort instabil.
"""

# SUBSECT 2b

"""
random priority -> O(log(N)) where N is the number of bits in an integer
timestamp       -> O(1)
tuple creation  -> O(3)
                => O(log(N))
"""
def next_message():
    while True:
        # Random priority of 0 to 5 (inclusive). We chose this number so 
        # that some priority collisions can be seen in the tests.
        priority = random.randint(0, 5) # O(log(N)) where N is the number of bits in 5; But depends on implementation
        timestamp = time.time()         # O(1)
        yield (priority, timestamp, "") # O(n) (n = 3)

# SUBSECT 2c

# SUBSECT Helpers for heap-structure

def heap_size(H):
    return H[0]

"""
O(1+1)
= O(1)
"""
def dec_heap_size(H):
    H[0] = H[0]-1

# O(1) # Same as above
def inc_heap_size(H):
    H[0] = H[0]+1

# O(1)
def parent(i):
    return i//2

# O(1)
def left(i):
    return i*2

# O(1)
def right(i):
    return i*2+1

"""
Comparison function for 2 message-tuples.

If the priorities (at m1[0] and m2[0]) are equal, the timestamps (at m1[1] and m2[1]) are compared.

Complexity: O(1)
"""
def prio(m1, m2):
    if m1[0] == m2[0]: # O(1)
        if m1[1] == m2[1]: # O(1)
            return 0
        else:
            return -1 if m1[1] < m2[1] else 1 # O(1)
    else:
        return -1 if m1[0] < m2[0] else 1 # O(1)

"""
Typical max_heapify modified to use the function prio(...) from above 

Complexity: O(n)
"""
def max_heapify(H, pos):
    left_t = left(pos)
    right_t = right(pos)
    if left_t <= heap_size(H) and prio(H[left_t], H[pos]) > 0:
        biggest = left_t
    else:
        biggest = pos

    if right_t <= heap_size(H) and prio(H[right_t], H[pos]) > 0:
        biggest = right_t

    if biggest != pos:
        H[pos], H[biggest] = H[biggest], H[pos]
        max_heapify(H, biggest)

"""
max_heapify is ran n/2 times, where n is the result of heap_size(H)

(n/2) * max_heapify
O((n/2) * n)
= O(n)
"""
def build_max_heap(H):
    H[0] = len(H) - 1
    for i in range(heap_size(H) // 2, 0, -1):
        max_heapify(H, i)

"""
2 Operationen:
- is_empty -> O(1)
- pqueue[1] -> O(1)
O(1)
"""
def peek(pqueue):
    if is_empty(pqueue):
        return None
    return pqueue[1]

"""
The while-loop runs a maximum of n/2 times.
index starts at the heap_size(pqueue) (which I will call n for convenience).
With each iteration, index is halved (by setting it to the parent index).

All other operations are trivial.

Complexity: O(n/2)
= O(n)
"""
def reorder_for_last_message(pqueue):
    index = heap_size(pqueue)
    par_idx = parent(index)
    while index > 1 and prio(pqueue[par_idx], pqueue[index]) < 0:
        pqueue[index], pqueue[par_idx] = pqueue[par_idx], pqueue[index]
        index = par_idx
        par_idx = parent(index)

# SUBSECT Priority-queue functions

"""
message is a Tuple of priority and timestamp

O(n+n) for re-ordering and 1 call to max_heapify
Complexity: O(n)
"""
def insert(pqueue, message):
    pqueue.append(message) # O(1)
    inc_heap_size(pqueue) # O(1)
    pqueue[heap_size(pqueue)] = message # O(1)
    reorder_for_last_message(pqueue) # O(n)
    max_heapify(pqueue, 1) # O(n)
    return pqueue

"""
O(1+1) # Array access and comparison
Complexity: O(1)
"""
def is_empty(pqueue):
    return heap_size(pqueue) == 0

"""
Complexity: O(n) where n is heap_size(pqueue)
"""
def delete(pqueue):
    if is_empty(pqueue): # O(1)
        return None

    result = pqueue[1] # O(1)
    pqueue[1] = pqueue[heap_size(pqueue)] # O(1+1+1)
    dec_heap_size(pqueue) # O(1)
    max_heapify(pqueue, 1) # O(n)
    return result

"""
Messagess are sorted on insertion or deletion.
This function can be used to re-sort a queue if it was corrupted
by evil programmers.

Complexity: O(n)
"""
def sort_messages(pqueue):
    hsize = heap_size(pqueue) # O(1)
    
    # Return if there is no sorting to be done
    if hsize < 2:             # O(1)
        return pqueue         # ignored for worst case

    # Do the sorting
    max_heapify(pqueue, 1)    # O(n)

    return pqueue

# SUBSECT 2d

"""
Generates 30 random actions (insertion or removal of messages) on a priority queue.
For each action, a random number between 0 and 2 (inclusive) is generated.
On a 0 or 1, a new message is inserted. On a 2, the first message is deleted.
After each action, a bit of information about the current state of the queue is
shown.
There is a random delay of 0 to 1 seconds in between each action.

Complexity: O(N + M + n)
N is the number of bits in a float
M is the number of bits in an int
n is the size of the heap.
"""
def sim_message_traffic():
    # Setup generator aand queue
    generator = next_message() # O(1)
    pqueue = [0]               # O(1)
    
    actions = 30 # Do 30 actions, each can be insertion or deletioon
    
    for _ in range(actions):
        # Wait random time between 0 and 1 seconds
        time.sleep(random.random()) # O(N) where N is the number of bits in float)
        
        # Insertion should be done 2 thirds of the time to show a little traffic
        if random.randint(0, 2) < 2:
            m = next(generator) # O(M)
            insert(pqueue, m) # O(n)
            print("Inserting", m)
        else:
            m = delete(pqueue) # O(n)
            print("Deleting", m)
        
        # Show information about the current state.
        print("  Size:", heap_size(pqueue), "Empty?", is_empty(pqueue), "Next:", peek(pqueue))

    # Uncomment to clear the queue and show each action
    #while not is_empty(pqueue):
        #m = delete(pqueue)
        #print("Deleting", m)
        #print("        Size:", heap_size(pqueue), "Empty?", is_empty(pqueue), "Next:", peek(pqueue))

    # Return the queue.
    return pqueue

# SUBSECT 2e

# Complexity of next_message        O(log(N))
# Complexity of insert              O(n)
# Complexity of is_empty            O(1)
# Complexity of delete              O(n)
# Complexity of sort_messages       O(n)
# Complexity of sim_message_traffic O(N + M + n)


# SECTION task 3

# SUBSECT 3a

def counting_sort_in_place(lst, k):
    C = [0 for i in range(0, k+1)] # k
    x = 0                          # 1
    for a in lst: 
        C[a] += 1                  # n
        
    # Die Bedingung von while begrenzt die maximale
    # Anzahl der Iterationen der Loops auf len(lst)
    # insgesamt.
    # Das kommt daher, dass x bei 0 beginnt, bis len(lst)
    # steigt und bei jeder iteration um 1 inkrementiert
    # wird.
    # Alle anderen Operationen sind trivial. Damit
    # kommt dieser Code-Abschnitt auf O(n)
    for i in range(0, k):
        while C[i] > 0 and x < len(lst):
            lst[x] = i
            x += 1
            C[i] -= 1

# SUBSECT 3b

"""
Komplexität:
Von oben zusammengezählt:
  O(k + 1 + n + n)
  O(k + 2*n)
  O(k+n)
  
  Ergebnis: O(k+n)
"""

# SECTION task 4

"""
Als erstes fällt auf, dass für so große Mengen an ganzen Zahlen vergleichslose Sortier-Algorithmen eignen. 

Aus einem kurzen, wenig wissenschaftlichen Test, haben wir anhand von 2048 Zahlen (im Bereich 0 bis 1000) die Algorithmen "Counting Sort", "Pigeonhole Sort" und "Radix Sort" getestet.
Alle Algorithmen schnitten gut ab. Bei Erhöhung der Zahlenmenge auf eine Millionen Elemente mit einem höheren Zahlenbereich stürzten Counting Sort und Radix Sort aber bald ab. Eine alternative Implementation von Radix Sort, die bei Listen bis zu 2000 Elementen auch gut war, funktionierte, benötigte aber zu lange.
Schon bei einer Listen-Größe von einer Million Elementen im Bereich -2**28 bis 2**28 funktionierte also nur noch Pigeonholesort (auch wenn es ein paar Sekunden benötigte).
Beim Test von vollen 32-Bit Zahlen gaben unsere Python3-Implementationen leider auf.

Während das Internet Radix-Sort bevorzugt, konnte bei uns also nur Pigeonholesort die Anforderungen erfüllen.

Probleme beim Pigeonhole Sort sind, dass er
  1. Sehr stark von der Differenz zwischen den Werten abhängig ist
  2. Bei sortierten Listen keinen Vorteil bietet. (Auch hier domiert 1.)
  3. Bei leeren Listen nicht funktioniert
     (Dieses Problem ist hier unwichtig und ist leicht zu beheben)
"""

"""
Pigeonhole Sort.
Dieser Algorithmus kommt nicht in den Vorlesungen vor.
Wir haben ihn in einem YouTube-Video von Musicombo im Vergleich zu Counting-Sort
und den Radix-Sort Varianten gesehen.
(wahrscheinlich dieses: https://youtu.be/wVLeC4B76jk )
Der Code kommt aus einem Projekt von Armins Arbeit bei jService.
Input:
    a Sequential collection of integral values
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
    my_min = min(a)                   # n
    my_max = max(a)                   # n
    size = my_max - my_min + 1        # 2
    holes = [0] * size                # 2 (Time complexity O(2) aber Space O(m))
  
    for x in a:                     # n
        holes[x - my_min] += 1        # n * 3 (3 operationen)
  
    i = 0                             # 1
    for count in range(size):       # m
        while holes[count] > 0:     # k (m im worst case)
            holes[count] -= 1         # m * k * 2 -> m*m * 2
            a[i] = count + my_min     # m * k * 2 -> m*m * 2
            i += 1                    # m * k * 1 -> m*m * 1
    return a
    
                                      # O(2n + 5 + 3n + 5mk)
                                      # O(5 + 5n + 5mk)
                                      # O(n + mk)
                                      # O(n + m*m) (da im Worst case k == m ist)
                                      # O(n + m**2)
"""

##################################################
# SECTION Tests                                  #
##################################################

"""
Helper function for sorting algorithms that work on full numbers.
It tests a variety of possible compositions of values a numeric list might have-
"""
def ensure_sorting_works(sorting_fn_name, sorting_fn):
    
    """
    Helper function. Shows the size of the list and whether or not it is sorted.
    (lowest to highest).
    """
    def show_message(l):
        print("    Size:", len(l), "Sorted?", is_sorted(operator.le, l))
    
    print("Testing algorithm:", sorting_fn_name)
    print("----------------------------------------------")
    
    print("  Testing on simple lists.")
    
    #l = []
    #sorting_fn(l)
    #show_message(l)
    
    # 1 Element
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
    pqueue = [0] # [0 for _ in range(0, 500)]
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

def test_sim_message_traffic():
    pqueue = sim_message_traffic()
    print("Output:", pqueue)

def test_counting_sort_in_place():
    lm = lambda l: counting_sort_in_place(l, 100000)
    ensure_sorting_works("Counting sort in place", lm)
    
def test_pigeonhole_sort():
    ensure_sorting_works("Pigeonhole Sort", pigeonhole_sort)

def test_million_sort():
    print("Testing 1,000,000 numbers")
    print("----------------------------------------------")
    
    # Our python3 implementations couldn't handle numbers bigger than this.
    n_max = int((2**28) * 1.5)
    n_min = int((2**28) * 1.5)-1
    
    print("1,000,000 non-unique shuffled\n  Creating...")
    l = [random.randint(n_min, n_max) for _ in range(0, 1000000)]
    print("  Sorting...")
    t1 = time.time()
    pigeonhole_sort(l)
    t2 = time.time()
    print("  Size:", len(l), "Time:", t2-t1, "Sorted?", is_sorted(operator.le, l))
    
    print("1,000,000 unique sorted\n  Creating...")
    l = [x for x in range(0, 1000000)]
    print("  Sorting...")
    t1 = time.time()
    pigeonhole_sort(l)
    t2 = time.time()
    print("  Size:", len(l), "Time:", t2-t1, "Sorted?", is_sorted(operator.le, l))
    
    print("1,000,000 unique shuffled\n  Creating...")
    l = [x for x in range(0, 1000000)]
    random.shuffle(l)
    print("  Sorting...")
    t1 = time.time()
    pigeonhole_sort(l)
    t2 = time.time()
    print("  Size:", len(l), "Time:", t2-t1, "Sorted?", is_sorted(operator.le, l))
    
    print("1,000,000 non-unique sorted\n  Creating...")
    l = [random.randint(n_min, n_max) for _ in range(0, 1000000)]
    l.sort()
    print("  Sorting...")
    t1 = time.time()
    pigeonhole_sort(l)
    t2 = time.time()
    print("  Size:", len(l), "Time:", t2-t1, "Sorted?", is_sorted(operator.le, l))
    
    # 100 million because why not?
    print("100,000,000 non-unique shuffled (this will take a while)\n  Creating...")
    l = [random.randint(n_min, n_max) for _ in range(0, 100000000)]
    print("  Sorting...")
    t1 = time.time()
    pigeonhole_sort(l)
    t2 = time.time()
    print("  Size:", len(l), "Time:", t2-t1, "Sorted?", is_sorted(operator.le, l))

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
    print("")
    test_million_sort()
