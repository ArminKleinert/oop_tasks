
# {P}        = {n > 0}
# {INV}      = {r == S(k-1) ^ (S(k-1) + S(k-2)) < n}
# {B}        = {(r+r+k) < n}
# {INV ^ B}  = {r == S(k-1) ^ (S(k-1) + S(k-2)) < n ^ (r+r+k) < n)}
# {INV ^ ~B} = {r == S(k-1) ^ (S(k-1) + S(k-2)) < n ^ (r+r+k) >= n)}
# {Q}        = {((k - 1) ** 2) < n <= (k**2)}

def root(n):
    # {P = n > 0}
    r = 0
    k = 1
    # {INV} = {r == S(k-1) ^ (S(k-1) + S(k-2)) < n}
    while (r+r+k) < n:
        r = r + k
        k = k + 1

    # {INV ^ ~B} = {r == S(k-1) ^ (S(k-1) + S(k-2)) < n ^ (r+r+k) >= n}
    # {Q = ((k - 1) ** 2) < n <= (k**2)}

    return k


