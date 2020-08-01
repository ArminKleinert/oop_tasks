def is_prime(n):
    if n < 2: 
         return False;
    if n % 2 == 0:             
         return n == 2
    k = 3
    while k*k <= n:
         if n % k == 0:
             return False
         k += 2
    return True

def prime_factors(n):
  assert n > 1
  ls = []
  primes = [i for i in range(1, n) if (is_prime(i))]
  while True:
    for i in primes:
      if is_prime(i) and n % i == 0:
       # print(i)
        ls.append(i)
        n //= i
        if n // i == 0:
          return ls
        break

#print(prime_factors(250))

def odd(i):
  return i % 2


def filter_rec(pred, lst, res = []):
  if len(lst) == 0:
    return res
  if pred(lst[0]):
    res.append(lst[0])
  return filter_rec(pred, lst[1:], res)


def filter_iter(pred, lst):
  res = []
  for i in lst:
    if pred(i):
      res.append(i)
  return res


#print(filter_iter(odd, [2, 4, 3, 7]))


def majority(lst):
  lres = [0 for _ in lst]
  lres = {}
  for i in range(0, len(lst)):
    if lst[i] in lres:
      lres[lst[i]] += 1
    else:
      lres[lst[i]] = 1
  for k in lres.keys():
    if lres[k] > len(lst) // 2:
      return k
  return "There is no majority"

#print(majority(["a", "b", "c", "a", "a"]))

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
		print(H)
	dec_heap_size(H)

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

#heapsort([3, 2, 0, 9, 4, 1, 6])




# P = n >= 0
# INV = { prod == i! }
# B = n-i > 0
# INV ^ B = prod == i! ^ n-i > 0
# INV ^ ~B = prod == i! ^ n-i <= 0
# Q = prod == n!









