import random
import time

# Task 1

"""
Decrement a number by 1
"""
def dec(x):
    m = 1
  
    # Flip all the set bits 
    # until we find a 1 
    while ((x & m) == 0): 
        x = x ^ m 
        m = m << 1
      
    # flip the rightmost 1 bit 
    x = x ^ m 
    return x 

def mersenne_prime(n):
    # Equivalent to (2**n)-1
    return dec(2 << dec(n))

# Task 2a
def repeats(m, n):
    lst = list(map(lambda x: random.randint(0, m), range(0, n)))
    res = map(lambda x: lst.count(x), range(0, m+1))
    return list(res)

# Task 2b
def repeats_dict(m, n):
    lst = list(map(lambda x: random.randint(0, m), range(0, n)))
    res = {}
    for x in lst:
        if x in res:
            res[x] += 1
        else:
            res[x] = 1
    return list(map(lambda x: res[x], range(0, m+1)))

# Task 3
time.perf_counter()
def iterative_linear_search(lst, elem):
    return None

def recursive_linear_search(lst, elem):
    return None

def iterative_binary_search(lst, elem):
    return None

def recursive_binary_search(lst, elem):
    return None

def test_time_searches():
    lst_size = 1000000
    range_last = lst_size-1
    lst = list(range(0, lst_size))
    num_search_elem = 10000
    
    ils_time1 = 0
    ils_time2 = 0
    average_time = 0
    
    ils_time1 = time.perf_counter()
    for _ in itertools.repeat(None, num_search_elem):
        iterative_linear_search(lst, random.randint(0, range_last))
    ils_time2 = time.perf_counter()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Iterative linear search: " + str(average_time) + " sec")
    
    ils_time1 = time.perf_counter()
    for _ in itertools.repeat(None, num_search_elem):
        recursive_linear_search(lst, random.randint(0, range_last))
    ils_time2 = time.perf_counter()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Recursive linear search: " + str(average_time) + " sec")
    
    ils_time1 = time.perf_counter()
    for _ in itertools.repeat(None, num_search_elem):
        iterative_binary_search(lst, random.randint(0, range_last))
    ils_time2 = time.perf_counter()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Iterative binary search: " + str(average_time) + " sec")
    
    ils_time1 = time.perf_counter()
    for _ in itertools.repeat(None, num_search_elem):
        recursive_binary_search(lst, random.randint(0, range_last))
    ils_time2 = time.perf_counter()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Iterative binary search: " + str(average_time) + " sec")

# Task 4

def odd(n):
    return n % 2 == 1

def fact(n):
    if n == 0:
        return 1
    else:
        return n * fact(n-1)

def apply_if(f, p, lst):
    if len(lst) == 0:
        return []
    
    e = lst[0]
    if p(e):
        e = f(e)
    
    return [e] + apply_if(f, p, lst[1:])

# Task 5

def revDigitsHelper(n, res):
    res = (res * 10) + (n % 10)
    if n < 10:
        return res
    else:
        return revDigitsHelper(n // 10, res)

def revDigits(n):
    return revDigitsHelper(n, 0)

# Task 6

