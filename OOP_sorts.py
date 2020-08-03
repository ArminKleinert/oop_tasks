# Binary search (Rekursiv) (Vorlesung 5)

def bin_search(key, seq):
    if len(seq) > 1:
        m = len(seq) // 2
        if seq[m] == key:
            return True
        elif key < seq[m]:
            return bin_search(key, seq[0:m])
        else:
            return bin_search(key, seq[(m + 1):])
    elif len(seq) == 1:
        return seq[0] == key
    else:
        return False


# ------------------------------------------------

# Binary search (Iterativ) (Vorlesung 5)

def binary_search(nums, key):
    lowerBound = 0
    upperBound = len(nums) - 1

    while lowerBound <= upperBound:
        current = (lowerBound + upperBound) // 2
        if nums[current] == key:
            return True
        else:
            if nums[current] < key:
                lowerBound = current + 1
            else:
                upperBound = current - 1
    return False


# ------------------------------------------------

# Bubble-Sort

def bubblesort(A):
    swap = True
    stop = len(A) - 1
    while swap:
        swap = False
        for i in range(stop):
            if A[i] > A[i + 1]:
                A[i], A[i + 1] = A[i + 1], A[i]
                swap = True
        stop = stop - 1


# -------------------------------------------------

# Chocktail Sort

def cocktail_sort(a):
    n = len(a)
    swapped = True
    start = 0
    end = n - 1
    while (swapped == True):

        swapped = False

        for i in range(start, end):
            if (a[i] > a[i + 1]):
                a[i], a[i + 1] = a[i + 1], a[i]
                swapped = True

        # Nothing changed, array is sorted
        if (swapped == False):
            break

        swapped = False

        end = end - 1

        for i in range(end - 1, start - 1, -1):
            if (a[i] > a[i + 1]):
                a[i], a[i + 1] = a[i + 1], a[i]
                swapped = True

        start = start + 1


# ------------------------------------------------

# Insert-Sort

def insertsort(seq):
    for j in range(1, len(seq)):
        key = seq[j]
        k = j - 1
        while k >= 0 and seq[k] > key:
            seq[k + 1] = seq[k]
            k = k - 1
        seq[k + 1] = key


# ------------------------------------------------

# Quicksort

def partition(A, low, high):
    pivot = A[low]
    i = low
    for j in range(low + 1, high + 1):
        if (A[j] < pivot):
            i = i + 1
            A[i], A[j] = A[j], A[i]
    A[i], A[low] = A[low], A[i]
    return i


def quicksort(A, low, high):
    if low < high:
        m = partition(A, low, high)
        quicksort(A, low, m - 1)
        quicksort(A, m + 1, high)


# ------------------------------------------------

# Quicksort mit insertionsort als Fallback

def qi_partition(lst, low, high):
    pivot = lst[low]
    i = low
    for j in range(low + 1, high + 1):
        if lst[j] < pivot:
            i = i + 1
        lst[i], lst[j] = lst[j], lst[i]
    lst[i], lst[low] = lst[low], lst[i]
    return i


# Mini-insertionsort
def insertsort_low_high(seq, low, high):
    j = low
    while j <= high:
        key = seq[j]
        k = j - 1
        while k >= 0 and seq[k] > key:
            seq[k + 1] = seq[k]
            k = k - 1
        seq[k + 1] = key
        j += 1


# Quicksort-implementation which falls back on insertionsort
# if the list has a size <= k.
def quick_insert(lst, low=0, high=None, k=15):
    if high is None:
        high = len(lst) - 1  # Set up parameter high

    if low < high:  # Do quicksort
        m = qi_partition(lst, low, high)
        if ((m - 1) < k):
            insertsort_low_high(lst, low, m - 1)
            insertsort_low_high(lst, m + 1, high)
        else:
            quick_insert(lst, low, m - 1, k)
            quick_insert(lst, m + 1, high, k)

    return lst


# ------------------------------------------------

# Mergesort

def merge(low, high):
    res = []
    i, j = 0, 0
    while i < len(low) and j < len(high):
        if low[i] <= high[j]:
            res.append(low[i])
            i = i + 1
        else:
            res.append(high[j])
            j = j + 1
    res = res + low[i:]
    res = res + high[j:]
    return res


def mergesort(A):
    if len(A) < 2:
        return A
    else:
        m = len(A) // 2
        return merge(mergesort(A[:m]), mergesort(A[m:]))


# -----------------

# Mergesort - Merge (Recursiv)

def merge_rec(low, high, res=[], i=0, j=0):
    if i < len(low) and j < len(high):
        if low[i] <= high[j]:
            return merge_rec(low, high, res + [low[i]], i + 1, j)
        else:
            return merge_rec(low, high, res + [high[j]], i, j + 1)
    return res + low[i:] + high[j:]


# ------------------------------------------------

# Mergesort (Iterativ)

def merge(lst, l, m, r, helper_lst):
    # n1 is the size of the first part of the helper-list
    # and also the offset for the second part
    n1 = m - l + 1

    # n2 is the size of the second part of the helper-list
    n2 = r - m

    # Re-fill helper-list with data from lst (no, a direct code will not work)
    for i in range(0, n1):
        helper_lst[i] = lst[l + i]
    for i in range(0, n2):
        helper_lst[i + n1] = lst[m + i + 1]

    # Do the merging.

    i, j, k = 0, 0, l
    while i < n1 and j < n2:
        if helper_lst[i] > helper_lst[j + n1]:
            lst[k] = helper_lst[j + n1]
            j += 1
        else:
            lst[k] = helper_lst[i]
            i += 1
        k += 1

    while i < n1:
        lst[k] = helper_lst[i]
        i += 1
        k += 1

    while j < n2:
        lst[k] = helper_lst[j + n1]
        j += 1
        k += 1


def mergesort(lst):
    helper_lst = [0 for x in lst]  # Helper-list filled with zeroes
    current_size = 1
    while current_size < len(lst) - 1:
        left = 0
        while left < len(lst) - 1:
            mid = left + current_size - 1
            right = 0
            if 2 * current_size + left - 1 > len(lst) - 1:
                right = len(lst) - 1
            else:
                right = 2 * current_size + left - 1
            # Call merge for sub-list
            merge(lst, left, mid, right, helper_lst)
            left = left + current_size * 2
        current_size = 2 * current_size


# ------------------------------------------------

# Shell-Sort

def shellSort(A):
    seg_size = len(A) // 2
    while seg_size > 0:
        for start_i in range(seg_size):
            jump_insertsort(A, start_i, seg_size)
        seg_size = seg_size // 2


def jump_insertsort(A, start, step):
    for i in range(start + step, len(A), step):
        value = A[i]
        j = i
        while j >= step and A[j - step] > value:
            A[j] = A[j - step]
            j = j - step
        A[j] = value


# ------------------------------------------------

# Heap-Sort

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


# Nochmal kontrollieren! Muss die letzte Zeile da sein?
def heapsort(H):
    build_max_heap(H)
    for i in range(heap_size(H), 1, -1):
        H[i], H[1] = H[1], H[i]
        dec_heap_size(H)
        max_heapify(H, 1)
    dec_heap_size(H)


def parent(i):
    return i // 2


def left(i):
    return i * 2


def right(i):
    return i * 2 + 1


def heap_size(H):
    return H[0]


def dec_heap_size(H):
    H[0] = H[0] - 1


# ------------------------------------------------

# Counting-Sort

# k is the maximum value in A

def counting_sort(arr, k):
    size = len(arr)
    output = [0 for i in range(0, size)]
    counter = [0 for i in range(0, k + 1)]
    for i in range(0, size):
        counter[arr[i]] += 1
    for i in range(1, k + 1):
        counter[i] += counter[i - 1]
    for i in range(size - 1, -1, -1):
        counter[arr[i]] -= 1
        output[counter[arr[i]]] = arr[i]
    return output

# ------------------------------------------------

# Radix Sort

"""
def radixsort(A, maximum):
    for i in range(maximum):
        counting_sort(A, i)  # Or any other stable sort
"""

# exp ist eine Zahl 1, 10, 100, ... und gibt die Ziffer an, nach der sortiert werden soll
def in_place_countingSort(arr, exp1):
    n = len(arr)
    output = [0] * (n)
    count = [0] * (10)
    for i in range(0, n):
        index = int(arr[i] / exp1)
        count[(index) % 10] += 1
    for i in range(1, 10):
        count[i] += count[i - 1]
    i = n - 1
    while i >= 0:
        index = int(arr[i] / exp1)
        output[count[(index) % 10] - 1] = arr[i]
        count[(index) % 10] -= 1
        i -= 1
    i = 0
    for i in range(0, len(arr)):
        arr[i] = output[i]
    return arr

def radixsort(arr):
    exp = 1
    maximum = max(arr)
    while maximum / exp > 0:
        in_place_countingSort(arr, exp)
        exp *= 10
    return arr

# ------------------------------------------------

# Bucket-Sort

# k ist z.B. == 10, wenn nur eine Ziffer nach dem Komma für die
# Erstellung des Buckets berücksichtigt werden soll.
def bucketsort(A, k):
    B = [[] for j in range(k)]
    n = len(A)
    for i in range(n):
        B[int((A[i]) * k)].append(A[i])
    for i in range(k):
        insertsort(B[i])
    R = []
    for i in range(k):
        R += B[i]
    return R



"""
Selection-Sort
Counting-Sort
Radix-Sort
Bucket-Sort

Eigenschaften

Bubble-Sort (Vorlesung 7)
- O(n**2)
- In-Place
- Stabil
- Zu naiv und ineffizient
- Gut für verkettete Listen
- Beispiel
  - [5, 7, 7, 0]
  - [(5, 7), 7, 0]
  - [5, (7, 7), 0]
  - [5, 7, (7, 0)]
  - [5, 7, (0, 7)]
  - [5, (7, 0), 7]
  - [5, (0, 7), 7]
  - [(5, 0), 7, 7]
  - [(0, 5), 7, 7]
  - [0, (5, 7), 7]
  - [0, 5, (7, 7)]
  - [0, 5, 7, 7]

Insert-Sort (Vorlesung 7)
- Best case: Liste schon sortiert (O(n))
- Average und Wort-case: O(n**2)
- In-Place
- Stabil
- Gut für kleine Sequenzen
- Geeignete Position wird gesucht und die Elemente des Bereichs verschoben
- Die einzusortierende Zahl wird in den gefundenen Platz kopiert
- Vorn in der Liste gibt es einen sortierten Bereich, der wächst
- Beispiel
  (Sortierter Bereich mit _..._ markiert, aktuell gecheeckter Wert mit ())
  - [5, 2, 4, 6, 1, 3]
  - [_5_, (2), 4, 6, 1, 3]
  - [_2, 5_, (4), 6, 1, 3]
  - [_2, 4_, (5), 6, 1, 3]
  - [_2, 4, 5_, (6), 1, 3]
  - [_2, 4, 5, 6_, (1), 3] # Die ersten 4 stellen müssen verschoben werden
  - [_1, 2, 4, 5_, (6), 3]
  - [_1, 2, 4, 5, 6_, (3)]
  - [_1, 2, 3, 4, 5_, (6)]
  - [_1, 2, 3, 4, 5, 6_]

Quicksort (Vorlesung 7)
- In-Place
- Instabil
- Einfache implementierung
- O(n*log(n)) Durchschnitt, O(n**2) worst case
- Kann optimiert werden, indem man für die erschaffenen
  Sub-arrays insertionsort nutzt
- Beispiel (Pivot mit () markiert)
  - [5, 7, 7, 0]
  - [(5), 7, 7, 0] # Pivot = 5
  - [(0)], [5], [7, 7] # Teilung in < Pivot und >= Pivot
  - [0, 5, 7, 7] # Zusammenfügen
- Beispiel 2
  - [5, 2, 4, 6, 1, 3]
  - [(5), 2, 4, 6, 1, 3]
  - [2, 4, 1, 3], [5], [6]
  - [(2), 4, 1, 3], [5], [(6)]
  - [[1], [2], [4, 3]], [5], [6]
  - [[1], [2], [(4), 3]], [5], [6]
  - [[1], [2], [[3], [4]]], [5], [6]
  # Zusammenführen:
  - [[1], [2], [3, 4]], [5], [6]
  - [1, 2, 3, 4], [5], [6]
  - [1, 2, 3, 4, 5 6]

Mergesort (Vorlesung 7)
- In-place
- Stabil
- O(n*log(n))
- Führt ingesamt n Vergleiche durch
- Halbiert Array immer wieder, bis len(sub)==1, sortiert dann direkte Subs (merge) und fügt Listen wieder zusammen (merge)
- Beispiel
  - [5, 7, 7, 0]
  - [5, 7], [7,0]
  - [5], [7], [7], [0]
  - [5, 7], [0, 7]
  - [0, 5, 7, 7]

Shell-Sort (Vorlesung 8)
- In-place
- Instabil
- Komplexität hängt von Segmentierung ab (O(n**1.5))

Heapsort (Vorlesung 8)
- O(n * log(n))
- Instabil, weil die Reihenfolge von gleichen Daten während der heapify-Funktion verändert werden kann.

Counting Sort
- Stabil
- Not in-place
- O(N+k) worst case (performance und space)
- Nimmt zwei Parameter: Einen Array A und eine Zahl K
  - K ist der größte wert in A
  - A kann nur ganze, positive Zahlen enthalten

Radix sort
- Keine Vergleiche
- In-Place
- Stabil, wenn intern ein stabiler Algorithmus genutzt wird
- O(n) average case (Wenn man, wie oben, einen Algorithmus mit O(n) nutzt)
- O(w * n) worst case (performance) (w ist Anzahl der Bits in each key)
- O(w + n) worst case (space)
- Sortiert Elementee (ganze Integer) nach Ziffern in Buckets

Bucket Sort
- Keine Vergleiche
- Nutzt temporäre sub-arrays
- O(n**2) worst case (Zahlen sind gleich verteilt)
- O(n) average case (Zahlen sind gleich verteilt)
- Kann Zahlen mit Nachkommestellen handlen

"""

"""

Memoization is a term describing an optimization technique where you cache previously computed results, and return the cached result when the same computation is needed again.

Dynamic programming is a technique for solving problems of recursive nature, iteratively and is applicable when the computations of the subproblems overlap.

Dynamic programming is typically implemented using tabulation, but can also be implemented using memoization. So as you can see, neither one is a "subset" of the other.

"""


# Suche längste Sub-Liste


