
def root(n):
    # {P = n > 0}
    r = 0
    k = 1
    # {INV} = {r == S(k-1) ^ (S(k-1) + S(k-2)) < n}
    while (r+r+k) < n:
        r = r + k
        k = k + 1

    # {INV v ~B} = {()r == S(k-1) ^ (S(k-1) + S(k-2)) < n)}
    # {Q = ((k - 1) ** 2) < n <= (k**2)}

    return k