

import random
from functools import reduce
import operator
import math


# SECTION Task 1

# Input:
#   ab A tuple with two numbers a and b
#   n An integer greater than or equal to 0
def random_list(ab, n):
    a, b = ab[0], ab[1]
    # Create a range from 0 to n-1, make each element into a random element
    # in ab and turn that iteratable into a list.
    return list(map(lambda _: random.randint(a, b), range(0, n)))


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

# TODO
def bad_bubbles(lst):
    swap = True
    stop = len(lst) - 1
    bad_steps = 0
    while swap:
        swap = False
        for i in range(stop):
            if lst[i] > lst[i+1]:
                lst[i], lst[i+1] = lst[i+1], lst[i]
                swap = True
        stop = stop - 1
    return lst


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
# Insertion-sort ist besser für kleine Sequenzen geeignet als
# Quicksort. Das liegt daran, dass die übliche Implementation von
# Quicksort unnötig viel Rekursion nutzt, während Intersionsort
# ohne Rekursion läuft. Bei jeder Rekursion muss der Callstack
# angepassst werden.
# Als "kleine Sequenzen" bezeichne ich hier Sequenzen mit 10 oder
# weniger Elementen.
# Deshalb greifen weniger naive Quicksort-Varianten für die kleinen
# Sequenzen, bei unweigerlich beim Quicksort zustande kommen, auf
# Insertionsort zurück. (Lustig, genau das wird ja in 4f gemacht :D)


# SECTION Task 4

# 4a
# TODO Schreiben Sie eine Variante des Quicksort-Algorithmus aus der Vorlesung, die den Median-Wert (mittleren Wert) aus drei zufällig gewählten Elementen des zu sortierenden Teilarrays berechnet und diesen Wert als Pivot verwendet.
def quicksort(lst):
    return


# 4b
# TODO An Beispiel erklären, wieso der Quicksort aus der Vorlesung
# nicht stabil ist.


# 4c
# TODO Erklären, ob man Quicksort stabil machen kann.


# 4d
# TODO Mit O-Notation den Overheaad dess Quick-sort in Haskell analysieren


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
        quick_insert(lst, low, m-1, k)
        quick_insert(lst, m+1, high, k)
    return lst


# 4g
# TODO Can I even be bothered doing this???


# SECTION 5

# TODO Make this work for any sequential type
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


# SECTION Task 7

# TODO 
# Gegeben seien zwei Listen mit jeweils n ganzen Zahlen. Entwerfen Sie einen
# effizienten Algorithmus, der die Liste aller Elemente, die in beiden Listen
# enthalten sind, berechnet. Die Ausgabe sollte in sortierter Reihenfolge erfolgen.
# Ihr Algorithmus sollte mit einer O(n(log(n)) Komplexität ausgeführt werden. 

