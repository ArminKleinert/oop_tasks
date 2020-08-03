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


# Quersumme

def crossSum(n):
    res = 0
    while n >= 10:
        res += n % 10
        n //= 10
    res += n
    if res >= 10:
        res = crossSum(res)
    return res





"""
def parent(i): return i//2

def left(i): return i*2

def right(i): return i*2+1

def heap_size(H): return H[0]

def dec_heap_size(H): H[0] = H[0]-1

def max_heapify(H,pos):
     left_t = left (pos)
     right_t = right(pos)
     if left_t<=heap_size(H) and H[left_t]>H[pos]:
          bigest = left_t
     else:
          bigest = pos
     if right_t<=heap_size(H) and H[right_t]>H[bigest]:
          bigest = right_t
     if bigest != pos:
          H[pos], H[bigest] = H[bigest], H[pos]
          max_heapify( H, bigest )

def build_max_heap(H):
     H[0] = len(H)-1
     for i in range(heap_size(H)//2,0,-1):
          max_heapify( H, i)

def heapsort(H):
    build_max_heap(H)
    for i in range(heap_size(H), 1, -1):
        H[i], H[1] = H[1], H[i]
        dec_heap_size(H)
        max_heapify(H, 1)
    dec_heap_size(H)
"""

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
	H[0] = len(H)-1
	for i in range(heap_size(H)//2, 0, -1):
		max_heapify(H, i)

def heapsort(H):
	build_max_heap(H)
	for i in range(heap_size(H), 1, -1):
		H[i], H[1] = H[1], H[i]
		dec_heap_size(H)
		max_heapify(H, 1)
	#dec_heap_size(H)

def parent(i):
	return i // 2

def left(i):
	return i*2

def right(i):
	return i*2+1

def heap_size(H):
	return H[0]

def dec_heap_size(H):
	H[0] = H[0]-1