import random
import operator
import math
import time

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

# 1a
"""
def shellSort(A):     
    seg_size = len(A)//2 
    while seg_size > 0:
        for start_i in range(seg_size):
            jump_InsertSort(A, start_i, seg_size)
        seg_size = seg_size // 2
"""

def jump_InsertSort(A, start, step):
    for i in range(start+step, len(A), step):
        value = A[i]
        j = i
        while  j >= step  and  A[j-step] > value:
            A[j] = A[j-step]
            j = j - step
        A[j] = value


def shellsort(lst):
    magic = (1391376, 463792, 198768, 86961, 33936, 13776, 4592, 1968, 861, 336, 112, 48, 21, 7, 3, 1)
    magic_idx = 0
    
    for i in range(0, len(magic)):
        magic_idx = i
        if magic[i] <= len(lst):
            break
        
    magic_idx -= 1
    if magic_idx < 0:
        print("list to big.")
        return
    
    while magic_idx < len(magic):
        for start_i in range(magic[magic_idx]):
            jump_InsertSort(lst, start_i, magic[magic_idx])
        magic_idx += 1
    
    return lst


"""
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

def test_shellsort_with_magic():
    print("Testing shellsort with magic-list.")
    
    l = []
    print("  Size   ", 0)
    random.shuffle(l)
    shellsort(l)
    print("  Sorted?", is_sorted(operator.le, l))

    l = list(range(0, 1))
    print("  Size   ", len(l))
    random.shuffle(l)
    shellsort(l)
    print("  Sorted?", is_sorted(operator.le, l))

    l = list(range(0, 10))
    print("  Size   ", len(l))
    random.shuffle(l)
    shellsort(l)
    print("  Sorted?", is_sorted(operator.le, l))

    l = list(range(0, 100))
    print("  Size   ", len(l))
    random.shuffle(l)
    shellsort(l)
    print("  Sorted?", is_sorted(operator.le, l))

    l = list(range(0, 1000))
    print("  Size   ", len(l))
    random.shuffle(l)
    shellsort(l)
    print("  Sorted?", is_sorted(operator.le, l))

    l = list(range(0, 10000))
    print("  Size   ", len(l))
    random.shuffle(l)
    shellsort(l)
    print("  Sorted?", is_sorted(operator.le, l))

    l = list(range(0, 100000))
    print("  Size   ", len(l))
    random.shuffle(l)
    shellsort(l)
    print("  Sorted?", is_sorted(operator.le, l))

# 1b
# TODO Schreiben Sie eine Variante der Funktion, die die Anzahl der Vergleichs-Operationen zählt und als Rückgabewert zurückgibt. 

def jump_InsertSort(A, start, step):
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
    

def shellsort_ops(A):
    cmp_ops = 0
    seg_size = len(A)//2 
    while seg_size > 0:
        cmp_ops += 1
        for start_i in range(seg_size):
            cmp_ops += jump_InsertSort(A, start_i, seg_size)
        seg_size = seg_size // 2
    return cmp_ops
"""
l = []
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)

l = [17]
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)

l = list(range(0, 10))
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)

l = list(range(0, 100))
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)

l = list(range(0, 1000))
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)

l = list(range(0, 10000))
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)

l = list(range(0, 100000))
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)
"""
#l = list(range(0, 1000000))
#random.shuffle(l)
#cmps = shellsort_ops(l)
#print("Size:", len(l), "Compares:", cmps)

# 1c
# TODO Schreiben Sie ein Python-Programm, das bei gleichen Zahleneingaben die Anzahl der Vergleichs-Operation des Shellsort- und Insertsort-Algorithmus in eine tabellarische Ausgabe ausgibt. Sie sollen dabei sortierte und unsortierte Zahlen verwenden. Interessant wird, wenn die Eingabezahlen umgekehrt sortiert sind. 
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
  
def comp_shellsort_insertionsort_ops_helper(lst):
    l1 = lst[:]
    is_ops = insertion_sort_ops(lst)
    ss_ops = shellsort_ops(l1)
    return (is_ops, ss_ops)

def comp_shellsort_insertionsort_ops(lst):
    is_ops, ss_ops = comp_shellsort_insertionsort_ops_helper(lst)
    print(ss_ops, is_ops)
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

# 2a
# TODO Zeigen, dass Heapsort nicht stabil ist

# 2b
# TODO Mit yield bei jedem Aufruf eine Nachricht mit Priority und Zeitstempel generieren
def next_message():
    while True:
        priority = random.randint(1, 51)
        timestamp = time.time()
        yield (priority, timestamp)

# 2c

# SUBSECT Helpers for heap-structure

def  heap_size(H):
    return H[0]

def  dec_heap_size(H):
    H[0] = H[0]-1

def parent(i):
    return i//2

def left(i):
    return i*2

def right(i):
    return i*2+1

def max_heapify(H, pos):
    left_t = left(pos)
    right_t = right(pos)
    if left_t <= heap_size(H) and H[left_t] > H[pos]:
        biggest = left_t
    else:
        biggest = pos
    
    if right_t <= heap_size(H) and H[right_t] > H[biggest]:
        biggest = right_t
        
    if biggest != pos:
        H[pos], H[biggest] = H[biggest], H[pos]
        max_heapify(H, biggest)

def build_max_heap(H):
    H[0] = len(H) - 1
    for i in range(heap_size(H) // 2, 0, -1):
        max_heapify(H, i)

# Complexity = O(n*log(n))
def heapsort(H):
    build_max_heap(H)
    for i in range(heap_size(H), 1, -1):
        H[i], H[1] = H[1], H[i]
        dec_heap_size(H)
        max_heapify(H, 1)
    dec_heap_size(H)

# Complexity = O(n*log(n))
def heapsort(H):
    build_max_heap(H)
    for i in range(heap_size(H), 1, -1):
        H[i], H[1] = H[1], H[i]
        dec_heap_size(H)
        max_heapify(H, 1)
    dec_heap_size(H)

# SUBSECT Priority-queue functions

"""
message is a Tuple of priority and timestamp
"""
def insert(priority_queue, message):
    priority_queue.append(message)
    return priority_queue

# TODO
def is_empty(priority_queue):
    return heap_size(priority_queue) == 0

# TODO
def delete(priority_queue):
    return None

# TODO As heapsort
def sort_messages(priority_queue):
    return priority_queue

# 2d

# TODO
def sim_message_traffix():
    return None

# 2e

# TODO Complexity of next_message
# TODO Complexity of insert
# TODO Complexity of is_empty
# TODO Complexity of delete
# TODO Complexity of sort_messages
# TODO Complexity of sim_message_traffix


# SECTION task 3

# 3a

# TODO Definieren Sie eine Variante des Countingsort-Algorithmus aus der Vorlesung, bei dem Sie die Elemente in-Place sortieren. Ihre Countingsort Variante muss nicht stabil sein. Sie dürfen als einzigen zusätzlichen Speicher das Hilfsrarray C verwenden (siehe Vorlesungsfolien). Das Hilfsarray B darf nicht mehr verwendet werden.

"""
Vorlesung:
def counting_sort(A, k):
    size = len(A)
    B = [0 for i in range(0, size)]
    C = [0 for i in range(0, k+1)]

    for j in range(0, size):
        C[A[j]] += 1
    for i in range(1, k+1):
        C[i] += C[i-1]
    for j in range(size-1, -1, -1):
        C[A[j]] -= 1
        B[C[A[j]]] = A[j]
    return B

Wikibooks:
def counting_sort_in_place(array, maxval):
    m = maxval + 1
    count = [0] * m               # init with zeros
    for a in array:
        count[a] += 1             # count occurences
    i = 0
    for a in range(m):            # emit
        for c in range(count[a]): # - emit 'count[a]' copies of 'a'
            array[i] = a
            i += 1
    return (array,count)
"""
def counting_sort_in_place(lst, k):
    C = [0] * k               # k
    for a in lst:
        C[a] += 1             # n*(1+1)
    i = 0
    for a in range(k):
        for c in range(C[a]):
            lst[i] = a        # n*k*(1+1)
            i += 1            # n*k*1
# 
# Von oben zusammengezählt:
#   k + n*(1+1) + n*k*(1+1) + n*k*1
#   k + 2*n + 2*(k*n) + n*k
#   k + 2*n + 3*(k*n)
#   3*k*n
#   O(3*k*n)


# SECTION task 4

"""
Was ist der effizienteste Weg, um eine Million 32-Bit-Ganzzahlen zu sortieren?. Die Frage hat Google an Obama im Jahr 2008 gestellt. Begründen Sie Ihre Antwort.

Implementieren Sie Ihre Lösung und testen Sie diese mit einer Million zufällig erzeugter 32-Bit Zahlen. Erläutern Sie Vor- und Nachteile Ihrer Lösung.
"""

"""
Als erstes fällt auf, dass für so große Mengen an ganzen Zahlen vergleichslose Sortier-Algorithmen eignen. 

Aus einem kurzen, wenig wissenschaftlichen Test, haben wir anhand von 2048 Zahlen (im Bereich 0 bis 1000) die Algorithmen "Counting Sort", "Pigeonhole Sort" und "Radix Sort" getestet.
Alle Algorithmen schnitten gut ab. Bei Erhöhung der Zahlenmenge auf eine Millionen Elementemit einem höheren Zahlenbereich stürzten Counting Sort und Radix Sort aber bald ab. Eine alternative Implementation von Radix Sort, die bei Listen bis zu 2000 Elementen auch gut war, funktionierte, benötigte aber zu lange.
Schon bei einer Listen-Größe von einer Million Elementen im Bereich -2**28 bis 2**28 funktionierte also nur noch Pigeonholesort (auch wenn es ein paar Sekunden benötigte).
Beim Test von vollen 32-Bit Zahlen gaben unsere Python3-Implementationen leider auf.

"""

num_min = int(-(2**28) * 1.5)
num_max = -num_min #0x7FFFFFFF
k = 1000000
r = range(0, k) # range(0, 1000000)

def counting_sort(A, k):
    size = len(A)
    B = [0 for i in range(0, size)]
    C = [0 for i in range(0, k+1)]

    for j in range(0, size):
        C[A[j]] += 1
    for i in range(1, k+1):
        C[i] += C[i-1]
    for j in range(size-1, -1, -1):
        C[A[j]] -= 1
        B[C[A[j]]] = A[j]
    return B

"""
lst = [random.randint(0, num_max) for _ in r]
#t0 = time.time()
#k = max(lst)
#t1 = time.time()
#print("Find max for counting sort:", t1-t0)

k = r[1]

t0 = time.time()
lst = counting_sort(lst, k)
t1 = time.time()
print("counting_sort:", t1-t0)
print("  Sorted?", sorted(lst))
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

lst = [random.randint(num_min, num_max) for _ in r]
t0 = time.time()
lst = pigeonhole_sort(lst)
t1 = time.time()
print("pigeonhole_sort:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

def radix_sort(a,n,maxLen):
    bins = [[] for i in range(n)]
    for x in range(maxLen):
        for b in bins:
            b.clear()
        for y in a:
            bins[(y//n**x)%n].append(y)
        a=[]
        for section in bins:
            a.extend(section)
    return a

def radix_sort_1(A):
    RADIX = 10
    maxLength = False
    tmp, placement = -1, 1
    
    while not maxLength:
        buckets = [list() for _ in range(RADIX)]
        for i in A:
            tmp = i
            buckets[tmp % RADIX].append(i)
            if maxLength and tmp > 0:
                maxLength = False
                
        a = 0
        for b in range(RADIX):
            buck = buckets[b]
            for i in buck:
                A[a] = i
                a += 1
        # move to next digit
        placement *= RADIX
    return A

def radix_sort_2(A):
    n = len(A)
    B = [[] for i in range(n)]
    for i in range(n):
        B[math.floor((A[i])*10)].append(A[i])
    for i in range(0, n):
        pigeonhole_sort(B[i])
    R = []
    for i in range(n):
        R = R + B[i]
    return R

"""
lst = [random.randint(num_min, num_max) for _ in r]
t0 = time.time()
lst = radix_sort(lst, 100, 50)
t1 = time.time()
print("Radix LSD (10, 5):", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

lst = [random.randint(num_min, num_max) for _ in r]
t0 = time.time()
lst = radix_sort(lst, 50, 50)
t1 = time.time()
print("Radix LSD (10, 5):", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

lst = [random.randint(num_min, num_max) for _ in r]
t0 = time.time()
lst = radix_sort(lst, 100, max(lst))
t1 = time.time()
print("Radix LSD (100, 10):", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))
"""
"""
lst = [random.randint(num_min, num_max) for _ in r]
t0 = time.time()
lst = radix_sort_2(lst)
t1 = time.time()
print("Radix2:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))

lst = [random.randint(num_min, num_max) for _ in r]
t0 = time.time()
lst = radix_sort_1(lst)
t1 = time.time()
print("Radix1:", t1-t0)
print("  Sorted?", is_sorted(operator.le, lst))
"""
