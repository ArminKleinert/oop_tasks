import sys

def charPicture(chooseChar, size):
    for y in range( 0, size):
        for x in range( 0, size):
            sys.stdout.write( chooseChar( x, y, size) )
        print()


def rectangles(x, y, size):
    if x <= (size//3) and y <= (size//3):
        return '-'
    elif x >= (size//3)*2 and y >= (size//3)*2:
        return '_'
    elif x % 3 == 0:
        return '*'
    else:
        return '.'


def odd(n):
    return (n % 2) != 0


# a)
def delete_while(f, lst):
    output = []
    i = 0
    while i < len(lst):
        if not f(lst[i]):
            output.append(lst[i])
        i += 1
    return output


def delete_while_rec(f, lst, i = 0, res = []):
    if i == len(lst):
        return res
    if f(lst[i]):
        return delete_while_rec(f, lst, i+1, res)
    else:
        return delete_while_rec(f, lst, i+1, res + [lst[i]])


def fact(n):
    res = 1
    for i in range(1, n + 1):
        res = res * i
    return res


def ssum(a, k):
    res = 0
    for i in range(0, k + 1):
        res += (i ** 3) * (a ** i / fact(i))
    return res


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


a = [5, 6, 5, 2, 7, 1, 9, 0, 5]