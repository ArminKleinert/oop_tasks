

# SECTION task 1

# 1a
#TODO
def shellsort(lst):
    return lst

# 1b
#TODO
def shellsort_ops(lst):
    return 0

# 1c
#TODO
def insertionsort_ops(lst):
    return 0

def comp_shellsort_insertionsort_ops(lst):
    return None


# SECTION task 2

# TODO
# Funktionen in task 2 sollen mit einer Heap-Struktur eine Priority-Queue simulieren.
# https://www.tutorialspoint.com/python_data_structure/python_heaps.htm ???

# 2a
# TODO Zeigen, dass Heapsort nicht stabil ist

# 2b
# TODO Mit yield bei jedem Aufruf eine Nachricht mit Priority und Zeitstempel generieren
def next_message():
    while True:
        yield (,)

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

# TODO
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

