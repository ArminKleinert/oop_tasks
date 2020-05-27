#!/usr/bin/env python3

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

def iterative_linear_search(elem, lst):
    for i in lst:
        if i == elem:
            return True
    return False

def recursive_linear_search(elem, lst):
    if len(lst) == 0:
        return False
    elif lst[0] == elem:
        return True
    else:
        return recursive_linear_search(elem, lst[1:])

def iterative_binary_search(elem, lst):
    lowerBound = 0
    upperBound = len(lst) - 1
    
    while lowerBound <= upperBound:
        current = (lowerBound + upperBound) // 2
        if lst[current] == elem:
            return True
        else:
            if lst[current] < elem:
                lowerBound = current + 1
            else:
                upperBound = current - 1
    
    return True

def recursive_binary_search(elem, lst):
    if len(lst) > 1:
        m = len(lst) // 2
        if lst[m] == elem:
            return True
        elif elem < lst[m]:
            return recursive_binary_search(elem, lst[0:m])
        else:
            return recursive_binary_search(elem, lst[(m+1):])
    elif len(lst) == 1:
        return lst[0] == elem
    else:
        return False

def test_time_searches():
    lst_size = 500
    range_last = lst_size-1
    lst = list(range(0, lst_size))
    num_search_elem = 1000
    
    ils_time1 = 0
    ils_time2 = 0
    average_time = 0
    
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        iterative_linear_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Iterative linear search: " + str(average_time) + " sec")
    
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        recursive_linear_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Recursive linear search: " + str(average_time) + " sec")
    
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        iterative_binary_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Iterative binary search: " + str(average_time) + " sec")
    
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        recursive_binary_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Recursive binary search: " + str(average_time) + " sec")

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

def revDigits(n):

    def rev_digits_helper(n, res):
        res = (res * 10) + (n % 10)
        if n < 10:
            return res
        else:
            return rev_digits_helper(n // 10, res)
        
    return rev_digits_helper(n, 0)

# Task 6

import turtle

def paint_star(x, y, size):
    angle = 120
    turtle.penup()
    turtle.speed(0)
    turtle.tracer()
    turtle.goto(x, y)
    turtle.color("yellow")
    turtle.fillcolor("yellow")
    turtle.pendown()
    turtle.begin_fill()

    for side in range(5):
        turtle.forward(size)
        turtle.right(angle)
        turtle.forward(size)
        turtle.right(72 - angle)
        
    turtle.end_fill()
    turtle.penup()
    return

def sky(n):
    min_x, min_y = -450, -450
    max_x, max_y = -min_x, -min_y

    turtle.bgcolor("black")
    for _ in range(0, n):
        x = random.randint(min_x, max_x)
        y = random.randint(min_y, max_y)
        size = random.randint(5, 25)
        paint_star(x, y, size)

def squares_helper(mid_x, mid_y, size, step):
    turtle.penup()
    turtle.speed(0)
    turtle.tracer(None, None)

    half_size = size / 2
    iteration = 0
    pos = -(size // 2)

    for i in range(pos, -pos, step):
        col = "red" if (iteration % 2 == 0) else "black"
        turtle.color(col)
        x = mid_x
        y = mid_y
        sz = half_size - i
        turtle.goto(x + sz, y + sz)
        turtle.pendown()
        turtle.goto(x - sz, y + sz)
        turtle.goto(x - sz, y - sz)
        turtle.goto(x + sz, y - sz)
        turtle.goto(x + sz, y + sz)
        turtle.penup()
        iteration += 1

    return None # just a marker for where the function ends

def squares():
    squares_helper(0, 0, 100, 10)

def fract_squares_help(mid_x, mid_y, size, step, depth):
    squares_helper(mid_x, mid_y, size, 10)

    sz_halved = size // 2
    tmp = size + sz_halved
    newstep = step // 2
    newdepth = depth - 1

    if depth > 0:
        fract_squares_help(mid_x + tmp, mid_y + tmp, sz_halved, newstep, newdepth)
        fract_squares_help(mid_x - tmp, mid_y + tmp, sz_halved, newstep, newdepth)
        fract_squares_help(mid_x - tmp, mid_y - tmp, sz_halved, newstep, newdepth)
        fract_squares_help(mid_x + tmp, mid_y - tmp, sz_halved, newstep, newdepth)

def fractal_squares():
    fract_squares_help(0, 0, 150, 10, 2)

# Task 7

def revDigitsIterative(n):
    res = n % 10
    
    while n >= 10:
        n = n // 10
        res = (res * 10) + (n % 10)
    
    return res

# Task 8

def foldl(f, default, lst):
    res = default
    for e in lst:
        res = f(res, e)
    return res

def foldr(f, default, lst):
    return foldl(f, default, reversed(lst))
