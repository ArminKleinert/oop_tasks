import random
import operator

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

l = list(range(0, 1000000))
random.shuffle(l)
cmps = shellsort_ops(l)
print("Size:", len(l), "Compares:", cmps)

# 1c
# TODO Schreiben Sie ein Python-Programm, das bei gleichen Zahleneingaben die Anzahl der Vergleichs-Operation des Shellsort- und Insertsort-Algorithmus in eine tabellarische Ausgabe ausgibt. Sie sollen dabei sortierte und unsortierte Zahlen verwenden. Interessant wird, wenn die Eingabezahlen umgekehrt sortiert sind. 
def insertionsort_ops(lst):
    return 0

def comp_shellsort_insertionsort_ops(lst):
    return None


# SECTION task 2

# TODO
# Funktionen in task 2 sollen mit einer Heap-Struktur eine Priority-Queue simulieren.

# 2a
# TODO Zeigen, dass Heapsort nicht stabil ist

# 2b
# TODO Mit yield bei jedem Aufruf eine Nachricht mit Priority und Zeitstempel generieren
def next_message():
    while True:
        yield (0,)

# 2c

# TODO
def insert(priority_queue, message):
    return priority_queue

# TODO
def is_empty(priority_queue):
    return False

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
def counting_sort_in_place(lst):
    return lst

# 3b
# TODO Complexity of counting_sort_in_place

# SECTION task 4

"""
Was ist der effizienteste Weg, um eine Million 32-Bit-Ganzzahlen zu sortieren?. Die Frage hat Google an Obama im Jahr 2008 gestellt. Begründen Sie Ihre Antwort.

Implementieren Sie Ihre Lösung und testen Sie diese mit einer Million zufällig erzeugter 32-Bit Zahlen. Erläutern Sie Vor- und Nachteile Ihrer Lösung.
"""

