

import random
from functools import reduce
import operator
import math
import statistics # In task 4 for statistics.median


# SECTION Task 1

# Creates a list of n elements which must all be numbers contained in 
# the pair (ab) (first argument)
#
# Input:
#   ab A tuple with two numbers a and b
#   n An integer greater than or equal to 0
def random_list(ab, n):
    a, b = ab[0], ab[1]
    # Create a range from 0 to n-1, make each element into a random element
    # in ab and turn that iteratable into a list.
    return list(map(lambda _: random.randint(a, b), range(0, n)))

# Checks if a sequential collection lst is sorted using an operator op.
# 
# op can actually be any function that takes 2 args
# and returns True or False depending on a comparison of the operants.
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

# Gets the index of the right-most occurance of an element in a list
def rindex(mylist, myvalue):
    return len(mylist) - mylist[::-1].index(myvalue) - 1

# Get the first index of an element in a list
def index(mlst, value):
    return mlst.index(value)

# Helper function.
# Checks if the movement of an element was in the wrong direction.
# If the move is bad, (0,1) is returned. Otherwise, (1,0) is returned.
# This way, the result can simply be added to a counter.
def bad_bubbles_helper(sorted_lst, element, new_index, starting_pos):
    # The new index is the destination => ok
    if sorted_lst[new_index] == element:
        return 1, 0
    
    # Going from high index to low index and not in range of matching indices in sorted list => bad
    if (starting_pos - new_index) > 0 and index(sorted_lst, element) > new_index:
        return 0, 1
    
    # Going from low index to high index and not in range of matching indices in sorted list => bad
    if (new_index - starting_pos) > 0 and rindex(sorted_lst, element) < new_index:
        return 0, 1
    
    # Ok
    return 1, 0

# Sorts a list lst using the bubblesort-algorithm and checks how 
# often elements were moved into the wrong direction.
#
# The comments were left in intentionally for your enjoyment.
#
# Returns a pair of numbers (good swaps first, then bad swaps)
def bad_bubbles(lst):
    lstcheck = lst[:]
    lstcheck.sort()
    num_bad_swaps = 0
    num_good_swaps = 0
    temp = (0,0)

    swap = True
    stop = len(lst) - 1
    #print(lst) # Uncomment to see the original list
    while swap:
        swap = False
        for i in range(stop):
            if lst[i] > lst[i+1]:
                lst[i], lst[i+1] = lst[i+1], lst[i]
                temp = bad_bubbles_helper(lstcheck, lst[i], i, i+1)
                num_good_swaps += temp[0]
                num_bad_swaps += temp[1]
                temp = bad_bubbles_helper(lstcheck, lst[i+1], i+1, i)
                num_good_swaps += temp[0]
                num_bad_swaps += temp[1]
                #print(lst, lstcheck, num_bad_swaps) # Uncomment to see detailed information each step
                swap = True
        stop = stop - 1

    return (num_good_swaps, num_bad_swaps)


# SECTION Task 3

# 3a

# Sorts a list via insertionsort. But, get this, it does not use a 
# for-loop but a while-loop instead.
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

# Creates a lot of random lists with a lot of elements each and tests insertsort
# from above on each of them. 
# Sadly, the function from task 1 had to be called 'sorted'. Had this not been the 
# case, then the build-in function 'sorted' could have been used but oh well oh well.
# If one of the lists was not sorted after using insertsort, the function
# complains and returns.
def test_sorted():
    n = 1000
    while n:
        lst = random_list((-1000, 1000), 1000)
        n -= 1
        insertsort(lst)
        if not sorted(operator.le, lst):
            print("List was sorted but was not sorted!")
            return False # Failure

    return True # Success

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
Sequenzen, die unweigerlich beim Quicksort zustande kommen, auf
Insertionsort zurück. (Lustig, genau das wird ja in 4f gemacht :D)
"""


# SECTION Task 4

# 4a

# A quicksort-variant which uses the median of 3 random numbers and calculates 
# the median. The result iss then used as the pivot.
def quicksort(arr, start, stop): 
    if(start < stop):
        # Select 3 random elements and get the median
        e0 = arr[random.randint(start, stop)]
        e1 = arr[random.randint(start, stop)]
        e2 = arr[random.randint(start, stop)]
        randpivot = int(statistics.median((e0, e1, e2)))

        pivotindex = partition(arr, randpivot, start, stop)
        quicksort(arr, start, pivotindex - 1) 
        quicksort(arr, pivotindex + 1, stop) 

# The partition function takes the pivot as a new argument instead of using
# the first element.
def partition(arr, piv, start, stop):
    i = start
    for j in range(start, stop + 1): 
        if arr[j] <= piv: 
            arr[i], arr[j] = arr[j], arr[i] 
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
"""
'Stabil' bedeutet, dass ein Sortier-Algorithmus niemals gleiche Elemente tauscht. 
Dieses Verhalten kann für Probleme sorgen, wenn die Daten nicht einfache Zahlen oder Strings, sondern Nutzer-definierte Typen sind und die Reihenfolge Zählt.
Außerdem sorgt das Vertauschen von gleichen Objekten für mehr Aufwand für das Programm.

Die Standard-Implementation von Quicksort ist nicht stabil.

In der Pivot-Funktion kommen diese Zeilen vor:

    for j in range(start + 1, stop + 1): 
        if arr[j] <= piv: 
            arr[i] , arr[j] = arr[j] , arr[i] 
            i = i + 1

Bei der Liste [5, 2, 1, 1, 7]
Würde die mittlere 1 (Index 2) als Pivot gewählt. Die zweite 1 (Index 3) würde 
nun an einen niedrigeren Index verschoben. Also wurden zwei gleiche Werte getauscht. 
Per Definition ist Quicksort also instabil.
"""


# 4c
# TODO Erklären, ob man Quicksort stabil machen kann.

"""
Ja, denn 
"""


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

# Partition-function for quick_insert.
# It does what every partition-function for quicksort ever did.
def qi_partition(lst, low, high):
    pivot = lst[low]
    i = low
    for j in range(low+1, high+1):
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
            seq[k+1] = seq[k]
            k = k - 1
        seq[k+1] = key
        j += 1


# Quicksort-implementation which falls back on insertionsort
# if the list has a size <= k.
# Input:
#   lst  A mutable collection
#   low  The lower bound for sorting (default = 0)
#   high The higher bound for sorting (default = len(lst))
#   k    The size from which insertionsort will be used instead. (default = 15)
def quick_insert(lst, low=0, high=None, k=15):
    if high is None:
        high = len(lst)-1 # Set up parameter high

    if len(lst) <= k: # Check fallback
        insertsort_low_high(lst, low, high)
    elif low < high: # Do quicksort
        m = qi_partition(lst, low, high)
        quick_insert(lst, low, m-1,  k)
        quick_insert(lst, m+1, high, k)

    return lst


# SECTION 5

"""
Gibt die Werte aus einer numerischen Liste zurück, deren Abstand am kleinsten ist.
min_diff((0, 9, 9, 10)) # => (9, 9)
min_diff((10, 15, 20, 25, 17)) # => (15, 17)

Das erste solche Paar wird bevorzugt.
min_diff((1, 2, 3, 4, 5, 6, 7)) # => (1, 2)

Complexity:
n + n*log(n) + ((n-2)*ein haufen trivialer operationen)
n + n*log(n) + (n-2)
=> O(n*log(n))
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
        while left < len(lst)-1: 
              
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
            merge(lst, left, mid, right, helper_lst) 
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
            lst[k] = L[i]
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

"""
-> Erst sortieren -> je n*log(n) -> O(n*(log(n)) + n*(log(n))) = O((2*n)*log(n)) = O(n*log(n))
-> Dann binary-search -> je Element O(log(n)) -> O(n*log(n))

Gesamt: O(n*log(n)) + O(n*log(n)) = O(2n*log(n))
Ergebnis = O(n*log(n))


Theoretischer Beispielcode unten:
"""

from bisect import bisect_left

# Eine binary-search Variante aus dem Internet, modifiziert um True oder False zurückzugeben:
def binary_search(a, x, lo=0, hi=None):
    hi = hi if hi is not None else len(a)
    pos = bisect_left(a, x, lo, hi)
    return pos != hi and a[pos] == x

# Die eigentliche Funktion

def list_intersection(lst1, lst2):
    lst1.sort() # Nutzt build-in, in-place sort mit geschätzter Komplexität O(nlog(n))
    lst2.sort() # Same
    
    result = [] # Variable für das Ergebnis

    # Safe-guard, mit dem Elemente nicht doppelt gecheckt werden
    last_checked = None

    # Iteration über Elemente von Liste 1
    for x in lst1:
        if last_checked is not x: # Element wurde noch nicht kontrolliert
            if binary_search(lst2, x): # Ist das Element auch in Liste 2?
                result.append(x) # Ja -> Hinzufügen zum Ergebnis
            last_checked = x

    return result

##################################################
# SECTION Tests                                  #
##################################################

def test_random_list():
    print("Testing random_list")
    
    ab = (0, 1000)
    n = 1000
    lst = random_list(ab, n)
    b = True
    for x in lst:
        b = b and (x in lst)
        if not b:
            break
    
    print("  Generating list with arguments ab=", ab, " n=", n)
    print("   Size of list is correct?", len(lst) == n)
    print("   All numbers in the list are in", ab, "?", b)
    
    ab = (-1000, 1)
    n = 100000
    lst = random_list(ab, n)
    b = True
    for x in lst:
        b = b and (x in lst)
        if not b:
            break
    
    print("  Generating list with arguments ab=", ab, " n=", n)
    print("   Size of list is correct?", len(lst) == n)
    print("   All numbers in the list are in", ab, "?", b)

def test_t1_sorted():
    print("Testing sorted")
    lst = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    print("  True case: lst=", lst, " op='<' correct result=", sorted(operator.lt, lst))
    print("  True case: lst=", lst, " op='<=' correct result=", sorted(operator.le, lst))
    print("  False case: lst=", lst, " op='>' correct result=", not sorted(operator.gt, lst))
    print("  False case: lst=", lst, " op='>=' correct result=", not sorted(operator.ge, lst))
    lst = [1, 1, 1, 1]
    print("  True case: lst=", lst, " op='==' correct result=", sorted(operator.eq, lst))
    print("  True case: lst=", lst, " op='<=' correct result=", sorted(operator.le, lst))
    print("  False case: lst=", lst, " op='<' correct result=", not sorted(operator.lt, lst))
    print("  False case: lst=", lst, " op='>' correct result=", not sorted(operator.gt, lst))
    lst = ["1", "2", "3"]
    print("  True case: lst=", lst, " op='<' correct result=", sorted(operator.lt, lst))
    print("  False case: lst=", lst, " op='>' correct result=", not sorted(operator.gt, lst))
    lst = [5, 4, 3, 2, 1]
    print("  True case: lst=", lst, " op='>' correct result=", sorted(operator.gt, lst))
    print("  True case: lst=", lst, " op='>=' correct result=", sorted(operator.ge, lst))
    print("  False case: lst=", lst, " op='<' correct result=", not sorted(operator.lt, lst))
    print("  False case: lst=", lst, " op='<=' correct result=", not sorted(operator.le, lst))
    lst = [1, 2, 3]
    fn = lambda x, y: x < y
    print("  True case: lst=", lst, " op='lambda <' correct result=", sorted(fn, lst))
    lst = [3, 2, 1]
    print("  False case: lst=", lst, " op='lambda <' correct result=", not sorted(fn, lst))

"""
Testet die Verschiebungen, die beim Bubblesort in die falsche Richtung laufen,
mit folgender Liste:
[3, 10, 6, 9, 5, 1, 2, 7, 6, 8]

Hier Eine Tabelle mit der Liste, der Ergebnisliste und der Anzahl der
gezählten Verschlechterungen:
Liste                           Ergebnis                        Vs
[3, 10, 6, 9, 5, 1, 2, 7, 6, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 0
[3, 6, 10, 9, 5, 1, 2, 7, 6, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 1
[3, 6, 9, 10, 5, 1, 2, 7, 6, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 2
[3, 6, 9, 5, 10, 1, 2, 7, 6, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 2
[3, 6, 9, 5, 1, 10, 2, 7, 6, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 2
[3, 6, 9, 5, 1, 2, 10, 7, 6, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 2
[3, 6, 9, 5, 1, 2, 7, 10, 6, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 2
[3, 6, 9, 5, 1, 2, 7, 6, 10, 8] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 2
[3, 6, 9, 5, 1, 2, 7, 6, 8, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 2
[3, 6, 5, 9, 1, 2, 7, 6, 8, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 3
[3, 6, 5, 1, 9, 2, 7, 6, 8, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 3
[3, 6, 5, 1, 2, 9, 7, 6, 8, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 3
[3, 6, 5, 1, 2, 7, 9, 6, 8, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 4
[3, 6, 5, 1, 2, 7, 6, 9, 8, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 4
[3, 6, 5, 1, 2, 7, 6, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 4
[3, 5, 6, 1, 2, 7, 6, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
[3, 5, 1, 6, 2, 7, 6, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
[3, 5, 1, 2, 6, 7, 6, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
[3, 5, 1, 2, 6, 6, 7, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
[3, 1, 5, 2, 6, 6, 7, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
[3, 1, 2, 5, 6, 6, 7, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
[1, 3, 2, 5, 6, 6, 7, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
[1, 2, 3, 5, 6, 6, 7, 8, 9, 10] [1, 2, 3, 5, 6, 6, 7, 8, 9, 10] 5
"""
def test_bad_bubbles():
    lst = [3, 10, 6, 9, 5, 1, 2, 7, 6, 8]
    print("Testing bad_bubbles")
    print("  Expecting result (39,5) for list", lst)
    print("  Result:", bad_bubbles(lst))

def test_insertsort():
    print("Testing insertsort")
    
    lst = [3, 10, 6, 9, 5, 1, 2, 7, 6, 8]
    res = lst[:]
    insertsort(res)
    print("  In=", lst, " Out=", res, "Sorted=", sorted(operator.le, res))
    
    lst = [0, 9, 8, 7, 6, 5, 4, 3, 3, 2]
    res = lst[:]
    insertsort(res)
    print("  In=", lst, "Out=", res, "Sorted=", sorted(operator.le, res))
    
def test_test_sorted():
    print("Testing test_sorted")
    print("  Creating 1000 lists with 1000 elements each.\n    Each element is a number between -1000 and 1000.\n    This will take a while.....")
    res = test_sorted()
    print("  Success?", res)

def test_my_quicksort():
    print("Test quicksort (median)")
    lst = [3, 10, 6, 9, 5, 1, 2, 7, 6, 8, 1, 2, 7]
    
    res = lst[:]
    quicksort(res, 0, len(res)-1)
    print("  In=", lst, " Out=", res, "Sorted=", sorted(operator.le, res))
    
    random.shuffle(lst)
    res = lst[:]
    quicksort(res, 0, len(res)-1)
    print("  In=", lst, " Out=", res, "Sorted=", sorted(operator.le, res))
    
    random.shuffle(lst)
    res = lst[:]
    quicksort(res, 0, len(res)-1)
    print("  In=", lst, " Out=", res, "Sorted=", sorted(operator.le, res))
    
    random.shuffle(lst)
    res = lst[:]
    quicksort(res, 0, len(res)-1)
    print("  In=", lst, " Out=", res, "Sorted=", sorted(operator.le, res))

def test_quick_insert():
    print("Test quick_insert")
    
    lst = [3, 10, 6, 9, 5, 1, 2, 7, 6, 8, 1, 2, 7]
    
    res = lst[:]
    quick_insert(res, k = 15)
    print("  In=", lst, "k=15 Out=", res, "Sorted=", sorted(operator.le, res))
    
    res = lst[:]
    quick_insert(res, k = 10)
    print("  In=", lst, "k=15 Out=", res, "Sorted=", sorted(operator.le, res))
    
    res = lst[:]
    quick_insert(res, k = 5)
    print("  In=", lst, "k=15 Out=", res, "Sorted=", sorted(operator.le, res))
    
    res = lst[:]
    quick_insert(res, k = 1)
    print("  In=", lst, "k=15 Out=", res, "Sorted=", sorted(operator.le, res))
    
    res = lst[:]
    quick_insert(res, k = 0)
    print("  In=", lst, "k=15 Out=", res, "Sorted=", sorted(operator.le, res))
    
    res = lst[:]
    quick_insert(res, k = -10)
    print("  In=", lst, "k=15 Out=", res, "Sorted=", sorted(operator.le, res))
    
def test_min_diff():
    print("Test min_diff")
    seq = (1, 1, 1)
    res = min_diff(seq)
    print("  In=", seq, "Out=", res, "Correct? ", res == (1, 1))
    seq = (200, 1, 200)
    res = min_diff(seq)
    print("  In=", seq, "Out=", res, "Correct? ", res == (200, 200))
    seq = (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    res = min_diff(seq)
    print("  In=", seq, "Out=", res, "Correct? ", res == (1, 2))
    seq = (10, 15, 20, 25, 17)
    res = min_diff(seq)
    print("  In=", seq, "Out=", res, "Correct? ", res == (15, 17))
    
def test_mergeSort():
    print("Test mergesort")
    lst = [3, 10, 6, 9, 5, 1, 2, 7, 6, 8, 1, 2, 7]
    
    res = lst[:]
    mergeSort(res)
    print("  In=", lst, "Out=", res, "Sorted=", sorted(operator.le, res))
    
    random.shuffle(lst)
    res = lst[:]
    mergeSort(res)
    print("  In=", lst, "Out=", res, "Sorted=", sorted(operator.le, res))
    
    random.shuffle(lst)
    res = lst[:]
    mergeSort(res)
    print("  In=", lst, "Out=", res, "Sorted=", sorted(operator.le, res))
    
    random.shuffle(lst)
    res = lst[:]
    mergeSort(res)
    print("  In=", lst, "Out=", res, "Sorted=", sorted(operator.le, res))

def test_task_7():
    print("Test task 7")
    print("(This test is theoretical and shall only determine the correctness of the example code.)")
    
    print("  Simple example.")
    i =    [1, 2, 3, 4, 5, 6, 7]
    o = [0, 1, 2, 3, 4, 5]
    print("   In=",i,"Out=",o, "Result=", list_intersection(i, o))
    
    print("  Longer example. (Generating 2 long lists...)")
    i = random_list((-1000, 1000), 1000)
    o = random_list((-1000, 1000), 1000)
    res = list_intersection(i, o)
    b = True
    for x in res:
        b = b and (x in i)
    print("   All numbers in result are in list 1?",b)
    b = True
    for x in res:
        b = b and (x in o)
    print("   All numbers in result are in list 2?",b)
    print("   Gives same result for same input?",res == list_intersection(i,o))
    
    print("  Longer example. (Generating 2 long lists...)")
    i = random_list((-1000, 1000), 1000)
    o = random_list((-1000, 1000), 1000)
    res = list_intersection(i, o)
    b = True
    for x in res:
        b = b and (x in i)
    print("   All numbers in result are in list 1?",b)
    b = True
    for x in res:
        b = b and (x in o)
    print("   All numbers in result are in list 2?",b)
    print("   Gives same result for same input?",res == list_intersection(i,o))

# SUBSECT Test-calls

print("-------------------- Test task 1\n")
test_random_list()
print()
test_t1_sorted()
print()
print("-------------------- Test task 2\n")
test_bad_bubbles()
print()
print("-------------------- Test task 3\n")
test_insertsort()
print()
test_test_sorted()
print()
print("-------------------- Test task 4\n")
test_my_quicksort()
print()
test_quick_insert()
print()
print("-------------------- Test task 5\n")
test_min_diff()
print()
print("-------------------- Test task 6\n")
test_mergeSort()
print()
print("-------------------- Test task 7\n")
test_task_7()

