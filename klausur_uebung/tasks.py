import math

# 1. Mersenne numbers


def mersenne(n):
    return pow(2, n) - 1


def pow(n0, n1):
    if n0 <= 0:
        return 1
    elif n0 == 1:
        return n1
    else:
        return n1 * pow(n0-1, n1)


def pow_sub(n0, n1, res):
    if n0 <= 0:
        return 1
    if n0 == 1:
        return res
    else:
        return pow_sub(n0-1, n1, res*n1)


def pow_tail_rec(n0, n1):
    return pow_sub(n0, n1, n1)
