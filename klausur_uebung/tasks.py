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


def zipWith(f, xs, ys):
    if len(xs) == 0 or len(ys) == 0:
        return []
    return [f(xs[0], ys[0])] + zipWith(f, xs[1:], ys[1:])

def zipWithIter(f, xs, ys):
    output = []
    for i in range(min(len(xs), len(ys))):
        output.append(f(xs[0], ys[1]))
    return output


def fib(n, a = 0, b = 1):
    if n == 0:
        return a
    if n == 1:
        return b
    return fib(n - 1, b, a + b)


