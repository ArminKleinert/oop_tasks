

import random
from functools import reduce
import operator
import math
import statistics # In task 4 for statistics.median


# SECTION Task 1

# Input:
#   ab A tuple with two numbers a and b
#   n An integer greater than or equal to 0
def random_list(ab, n):
    a, b = ab[0], ab[1]
    # Create a range from 0 to n-1, make each element into a random element
    # in ab and turn that iteratable into a list.
    return list(map(lambda _: random.randint(a, b), range(0, n)))


# op can any function that takes 2 args and returns True or False depending on a comparison of the operants
def sorted(op, lst):
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


# SECTION Task 2

def rindex(mylist, myvalue):
    return len(mylist) - mylist[::-1].index(myvalue) - 1

def index(mlst, value):
    return mlst.index(value)

def bad_bubbles_helper(sorted_lst, element, new_index, starting_pos):
    if sorted_lst[new_index] == element:
        return 0
    
    # Going from high index to low index and not in range of matching indices in sorted list
    if (starting_pos - new_index) > 0 and index(sorted_lst, element) > new_index:
        return 1
    
    # Going from low index to high index and not in range of matching indices in sorted list
    if (new_index - starting_pos) > 0 and rindex(sorted_lst, element) < new_index:
        return 1
    
    return 0

def bad_bubbles(lst):
    lstcheck = lst[:]
    lstcheck.sort()
    num_bad_swaps = 0

    swap = True
    stop = len(lst) - 1
    print(lst)
    while swap:
        swap = False
        for i in range(stop):
            if lst[i] > lst[i+1]:
                lst[i], lst[i+1] = lst[i+1], lst[i]
                num_bad_swaps += bad_bubbles_helper(lstcheck, lst[i], i, i+1)
                num_bad_swaps += bad_bubbles_helper(lstcheck, lst[i+1], i+1, i)
                print(lst, lstcheck, num_bad_swaps)
                swap = True
        stop = stop - 1

    return num_bad_swaps


# SECTION Task 3

# 3a
def insertsort(seq):
    j = 1
    while j < len(seq):
        key = seq[j]
        k = j - 1
        while k >= 0 and seq[k] > key:
            seq[k+1] = seq[k]
            k = k - 1
        seq[k+1] = key
        j += 1

# 3b
def test_sorted():
    n = 1000
    while n:
        lst = random_list((-1000, 1000), 1000)
        if not sorted(operator.le, lst):
            n -= 1
            insertsort(lst)
            if not sorted(operator.le, lst):
                print("List was sorted but was not sorted!")
                return None

# 3c
"""
Insertion-sort ist besser für kleine Sequenzen geeignet als
Quicksort. Das liegt daran, dass die übliche Implementation von
Quicksort unnötig viel Rekursion nutzt, während Intersionsort
ohne Rekursion läuft. Bei jeder Rekursion muss der Callstack
angepassst werden.
Als "kleine Sequenzen" bezeichne ich hier Sequenzen mit 15 oder
weniger Elementen.
Deshalb greifen weniger naive Quicksort-Varianten für die kleinen
Sequenzen, bei unweigerlich beim Quicksort zustande kommen, auf
Insertionsort zurück. (Lustig, genau das wird ja in 4f gemacht :D)
"""


# SECTION Task 4

# 4a

def quicksort(arr, start , stop): 
    if(start < stop):
        # Select 3 random elements and get the median
        templst = arr[start:stop]
        random.shuffle(templst)
        randpivot = int(statistics.median(templst[0:2]))
        
        pivotindex = partitionrand(arr, randpivot, start, stop)
        quicksort(arr , start , pivotindex - 1) 
        quicksort(arr, pivotindex + 1, stop) 


def partitionrand(arr, piv, start, stop):
    randpivot = 0
    i = start + 1
    for j in range(start + 1, stop + 1): 
        if arr[j] <= piv: 
            arr[i] , arr[j] = arr[j] , arr[i] 
            i = i + 1
    piv, arr[i - 1] = arr[i - 1], piv
    return i - 1

"""
lst = [1, 2, 4, 9, 10, 1, 1, 5, 6, 3, 2, 5, 8, 3, 4, 2, 1]
#lst = [1, 2]
print(lst)
quicksort(lst, 0, len(lst)-1)
print(lst)
"""

# 4b
# TODO An Beispiel erklären, wieso der Quicksort aus der Vorlesung
# nicht stabil ist.
"""
'Stabil' bedeutet, dass ein Sortier-Algorithmus niemals gleiche Elemente tauscht. 
Dieses Verhalten kann für Probleme sorgen, wenn die Daten nicht einfache Zahlen oder Strings, sondern Nutzer-definierte Typen sind und die Reihenfolge Zählt.
Außerdem sorgt das Vertauschen von gleichen Objekten für mehr Aufwand für das Programm.

Die Standard-Implementation von Quicksort ist nicht stabil.
"""


# 4c
# TODO Erklären, ob man Quicksort stabil machen kann.


# 4d

"""
Tabelle zusätzlich erstellter Listen je nach Anzahl der Elemente in der zu sortierenden Liste:
 1  0
 2  3
 3  6
 4  9
 5 12
 6 15
 7 18
 8 21
 9 24
10 27
11 30
12 33

(n-1)*3
=> O(n)


Genutzter Testcode:
count1 = 0

def quick_sort(seq):
    global count1
    
    if len(seq)>1:
        q1 = [s for s in seq[1:] if s<seq[0]]
        q2 = [s for s in seq[1:] if s>=seq[0]]
        count1 += 3 # Both lines above and [seq[0]]
        return quick_sort(q1) + [seq[0]] + quick_sort(q2)
    else:
        return seq

seq = []
for x in range(0, 12):
    seq.append(x)
    quick_sort(seq)
    print(len(seq), count1)
    count1 = 0
"""

# 4e
# TODO Wie oft kann Quicksort das kleinste Element bewegen? (Begründen)


# 4f
def qi_partition(lst, low, high):
    pivot = lst[low]
    i = low
    for j in range(low+1, high+1):
        if lst[j] < pivot:
            i = i + 1
            lst[i], lst[j] = lst[j], lst[i]
    lst[i], lst[low] = lst[low], lst[i]
    return i


def insertsort_low_high(seq, low, high):
    j = low
    while j < high:
        key = seq[j]
        k = j - 1
        while k >= 0 and seq[k] > key:
            seq[k+1] = seq[k]
            k = k - 1
        seq[k+1] = key
        j += 1


def quick_insert(lst, low, high, k):
    if len(lst) <= k:
        insertsort_low_high(lst, low, high)
    elif low < high:
        m = qi_partition(lst, low, high)
        quick_insert(lst, low, m-1,  k)
        quick_insert(lst, m+1, high, k)
    return lst


# SECTION 5

"""
Complexity:
n + log(n) + ((n-2)*ein haufen trivialer operationen)
n + log(n) + (n-2)
=> O(log(n))
"""
def min_diff(seq):
    lst = list(seq)
    lst.sort()
    min_difference = lst[1] - lst[0]
    md_index = 1
    for i in range(2, len(lst)):
        md_2 = lst[i] - lst[i-1]
        if md_2 < min_difference:
            min_difference = md_2
            md_index = i
    return lst[md_index-1], lst[md_index]



# SECTION Task 6

# TODO Make iterative Mergesort without producing more than 1 additional collection

# Iterative mergesort function to 
# sort arr[0...n-1]  
def mergeSort(lst): 
      
    helper_lst = []
    current_size = 1
      
    # Outer loop for traversing Each  
    # sub array of current_size 
    while current_size < len(lst) - 1: 
          
        left = 0
        # Inner loop for merge call  
        # in a sub array 
        # Each complete Iteration sorts 
        # the iterating sub array 
        while left < len(a)-1: 
              
            # mid index = left index of  
            # sub array + current sub  
            # array size - 1 
            mid = left + current_size - 1
              
            # (False result,True result) 
            # [Condition] Can use current_size 
            # if 2 * current_size < len(lst)-1 
            # else len(lst)-1 
            right = ((2 * current_size + left - 1, 
                    len(lst) - 1)[2 * current_size  
                          + left - 1 > len(lst)-1]) 
                            
            # Merge call for each sub array 
            merge(a, left, mid, right, helper_lst) 
            left = left + current_size*2
              
        # Increasing sub array size by 
        # multiple of 2 
        current_size = 2 * current_size 
  
# Merge Function 
def merge(lst, l, m, r, helper_lst): 
    n1 = m - l + 1
    n2 = r - m 
    
    # Hier werden neue Listen generiert!!!
    L = [0] * n1
    R = [0] * n2
    
    for i in range(0, n1): 
        L[i] = lst[l + i]
    for i in range(0, n2): 
        R[i] = lst[m + i + 1]
  
    i, j, k = 0, 0, l 
    while i < n1 and j < n2: 
        if L[i] > R[j]: 
            lst[k] = R[j]
            j += 1
        else: 
            a[k] = L[i]
            i += 1
        k += 1
  
    while i < n1: 
        lst[k] = L[i]
        i += 1
        k += 1
  
    while j < n2: 
        lst[k] = R[j]
        j += 1
        k += 1


#a = [12, 11, 13, 5, 6, 7]
#print("Given array is ")
#print(a)

#mergeSort(a)

#print("Sorted array is ")
#print(a)

# SECTION Task 7

# -> Erst sortieren -> je n*log(n) -> O(n*(log(n)) + n*(log(n))) = O((2*n)*log(n)) = O(n*log(n))
# -> Dann binary-search -> je Element O(log(n)) -> O(n*log(n))
# O(n*log(n)) + O(n*log(n)) = O(2n*log(n)) = O(n*log(n))
