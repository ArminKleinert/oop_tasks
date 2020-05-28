#!/usr/bin/env python3

import random # For multiple tasks
import time # For task 3
import turtle # For task 6

## SECTION Task 1

"""
Decrement a number by 1 using only binary operations &, ^, <<
"""
def dec(x):
    # Handle the case that x is 0
    if not x:
        return -1
    
    m = 1

    # Flip all the set bits 
    # until we find a 1 
    while ((x & m) == 0): 
        x = x ^ m 
        m = m << 1

    # Flip the rightmost 1 bit 
    x = x ^ m 
    return x 

def mersenne_prime(n):
    # Equivalent to (2**n)-1
    return dec(2 << dec(n))

## SECTION Task 2

# SUBSECT Task 2a

# Generate a list of n random numbers between 0 and m
# Then, another list is generates which stores the occurances of 
# each number in the first list.
def repeats(m, n):
    lst = list(map(lambda x: random.randint(0, m), range(0, n)))
    res = map(lambda x: lst.count(x), range(0, m+1))
    return list(res)

# SUBSECT Task 2b

# Does the same thing as repeats(...) but uses a dictionary instead.
def repeats_dict(m, n):
    lst = list(map(lambda x: random.randint(0, m), range(0, n)))
    res = {} # Empty dictionary
    for x in lst:
        if x in res:
            res[x] += 1
        else:
            res[x] = 1
    return list(map(lambda x: res[x], range(0, m+1)))

## SECTION Task 3

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
    # Set up the list, number of elements and so on
    lst_size = 500
    range_last = lst_size-1
    lst = list(range(0, lst_size))
    num_search_elem = 1000
    
    # Variables that will help keep track of time
    ils_time1 = 0
    ils_time2 = 0
    average_time = 0
    
    # Test Iterative linear search
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        iterative_linear_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Iterative linear search: " + str(average_time) + " sec")
    
    # Test Recursive linear search
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        recursive_linear_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Recursive linear search: " + str(average_time) + " sec")
    
    # Test Iterative binary search
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        iterative_binary_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Iterative binary search: " + str(average_time) + " sec")
    
    # Test Recursive binary search
    ils_time1 = time.time()
    for _ in range(0, num_search_elem):
        recursive_binary_search(random.randint(0, range_last), lst)
    ils_time2 = time.time()
    average_time = (ils_time2 - ils_time1) / num_search_elem
    print("Recursive binary search: " + str(average_time) + " sec")

## SECTION Task 4

# Checks if a number is odd, return True or False
def odd(n):
    return n % 2 == 1

# Calculate factorial of a number using recursion
def fact(n):
    if n == 0:
        return 1
    else:
        return n * fact(n-1)

# Iterates over the list lst and runs the function f on each element
# for which the predicate-function p is true.
# The elements are then concatenated into a list again.
def apply_if(f, p, lst):
    # If the list is empty, return an empty list.
    if len(lst) == 0:
        return []
    
    # Get the first element from the list, check p(e)
    # and then maybe run f(e)
    e = lst[0]
    if p(e):
        e = f(e)
    
    # Append e to the start of the result of a recursive call.
    return [e] + apply_if(f, p, lst[1:])

# Task 5

# Reverses the digits of an integer.
# If the input is a negative integer, the output will be negative as well
def revDigits(n):

    # Tail-recursive helper-function
    def rev_digits_helper(n, res):
        res = (res * 10) + (n % 10)
        if n < 10:
            return res
        else:
            return rev_digits_helper(n // 10, res)
    
    # Store a bool to tell if n is negative
    negative = n < 0
    if negative:
        n *= -1 # If n was negative, make it positive

    # Use the helper funtion
    abs_result = rev_digits_helper(n, 0)
    
    # Make the result positive or negative depending on the original value of n
    result = (abs_result * -1) if negative else abs_result
    
    return result

## SECTION Task 6

# SUBSECT Task 6a

# Draws a little star at the position x,y with a size.
# The background is set to black.
def paint_star(x, y, size):
    angle = 120
    turtle.penup()
    
    # Try to make turtle as fast as possible
    turtle.speed(0)
    turtle.tracer()
    
    # Go to the start
    turtle.goto(x, y)
    
    # Set up the color of the stars
    turtle.color("yellow")
    turtle.fillcolor("yellow")
    
    # Start drawing
    turtle.pendown()
    turtle.begin_fill()

    # Draw the star's shape
    for side in range(5):
        turtle.forward(size)
        turtle.right(angle)
        turtle.forward(size)
        turtle.right(72 - angle)
    
    # Stop drawing
    turtle.end_fill()
    turtle.penup()

# SUBSECT Task 6b

# Uses the paint_star(...) function n times at randomized positions.
def sky(n):
    # Set up a field of minimum and maximum coordinates.
    # These will be used as boundries for the random positions.
    min_x, min_y = -450, -450
    max_x, max_y = -min_x, -min_y

    # Put the lights out
    turtle.bgcolor("black")
    
    # Draw n stars at random positions with slightly randomized sizes.
    for _ in range(0, n):
        x = random.randint(min_x, max_x)
        y = random.randint(min_y, max_y)
        size = random.randint(5, 25)
        paint_star(x, y, size)

# SUBSECT Task 6c

# A helper function used by both squares(...) and fractal_squares(...)
def squares_helper(mid_x, mid_y, size, step):
    turtle.penup()
    turtle.speed(0)
    turtle.tracer(None, None)

    # Set up a bunch of short names and temporary variables
    half_size = size / 2
    iteration = 0
    pos = -(size // 2)

    for i in range(pos, -pos, step):
        # Change the color with each 2nd iteration of the loop (red or black)
        col = "red" if (iteration % 2 == 0) else "black"
        turtle.color(col)
        iteration += 1
        
        # Temporary variable as a shortcut for half_size-1
        sz = half_size - i
        
        # Go to starting position (Lower right)
        turtle.goto(mid_x + sz, mid_y + sz)
        turtle.pendown()

        turtle.goto(mid_x - sz, mid_y + sz) # Upper left
        turtle.goto(mid_x - sz, mid_y - sz) # Lower left
        turtle.goto(mid_x + sz, mid_y - sz) # Upper right
        turtle.goto(mid_x + sz, mid_y + sz) # Lower right
        
        turtle.penup()

# The real function for task 6c. It just calls squares_helper using the 
# middle of the screen as the middle of the squares.
def squares():
    squares_helper(0, 0, 500, 10)

# SUBSECT Task 6d

# Helper function for task 6d
# Calls squares_helper(...) using the given parameters.
# It then calls itself again with different values for 
# mid_x and mid_y, halved values for size and step and depth-1.
# Once depth reaches 0, recursion stops
def fract_squares_help(mid_x, mid_y, size, step, depth):
    # Draw rectangles
    squares_helper(mid_x, mid_y, size, step)

    # Set up the new parameters
    sz_halved = size // 2
    tmp = size + sz_halved
    newstep = step // 2
    newdepth = depth - 1

    # Recursive calls for each corner using the parameters.
    if depth > 0:
        fract_squares_help(mid_x + tmp, mid_y + tmp, sz_halved, newstep, newdepth)
        fract_squares_help(mid_x - tmp, mid_y + tmp, sz_halved, newstep, newdepth)
        fract_squares_help(mid_x - tmp, mid_y - tmp, sz_halved, newstep, newdepth)
        fract_squares_help(mid_x + tmp, mid_y - tmp, sz_halved, newstep, newdepth)

# The actual function for task 6d. Calls fract_squares_help(...) using 
# the middle of the screen and a depth value of 2.
def fractal_squares():
    fract_squares_help(0, 0, 150, 10, 2)

## SECTION Task 7

# A version of the algorithm from task 5 which uses 
# iteration instead of recursion.
def revDigitsIterative(n):
    res = n % 10
    
    while n >= 10:
        n = n // 10
        res = (res * 10) + (n % 10)
    
    return res

## SECTION Task 8

def foldl(f, default, lst):
    res = default
    for e in lst:
        res = f(res, e)
    return res

# Uses foldl(...) but lst is reversed beforehand.
def foldr(f, default, lst):
    return foldl(f, default, reversed(lst))

## SECTION Task 9

def mersenne_prime_test():
    print("Test task 1")
    seq = [2, 3, 5, 7, 13, 17, 31, 61, 89, 107, 521, 607, 1279, 2203, 2281, 3217, 4253, 4423, 9689, 9941, 11213, 19937]
    
    for n in seq:
        res = ((2 ** n) - 1) == mersenne_prime(n)
        print("  mersenne_prime(", str(n), "): ", str(res))

def repeats_test():
    print("Test task 2 (This is a bit hard, as it is based on random numbers)")
    print("  Working...")
    for n in range(100, 150):
        m = n // 5
        res = repeats(m, n)
        if (len(res) == (m + 1)) and (sum(res) == n):
            True # Success
        else:
            print("Failed  repeats(" + str(m) + ", " + str(n) + ")")
            print("    Length correct?         " + str(len(res) == (m + 1)))
            print("    Sum of numbers correct? " + str(sum(res) == n))
            return
    print("  Success!")

def test_time_searches_test():
    print("Test task 3")
    for i in range(0, 3):
        print("  Iteration", str(i))
        test_time_searches()
        
        
def apply_if_test():
    print("Test task 4")
    # Example from the pdf
    res = apply_if(fact, odd, [2, 5, 7, 4, 9, 6]) == [2, 120, 5040, 4, 362880, 6]
    print("  ", res)
    # For positive numbers, turns the number into a string.
    temp = apply_if(str, lambda n: n > 0, [-1, -15, 2, 5, 7, 4, 9, 6])
    res = temp == [-1, -15, "2", "5", "7", "4", "9", "6"]
    print("  ", res)

def rev_digits_recursive_test():
    print("Test task 5")
    print("  ", revDigits(15) == 51)
    print("  ", revDigits(51) == 15)
    print("  ", revDigits(51) == revDigits(revDigits(revDigits(51))))
    print("  ", revDigits(revDigits(51)) == 51)
    print("  ", revDigits(115) == 511)
    print("  ", revDigits(61000) == 16)
    print("  ", revDigits(-15) == -51)
    print("  ", revDigits(123456789) == 987654321)

def rev_digits_iterative_test():
    print("Test task 7")
    print("  ", revDigitsIterative(15) == 51)
    print("  ", revDigitsIterative(51) == 15)
    print("  ", revDigitsIterative(51) == revDigitsIterative(revDigitsIterative(revDigitsIterative(51))))
    print("  ", revDigitsIterative(revDigitsIterative(51)) == 51)
    print("  ", revDigitsIterative(115) == 511)
    print("  ", revDigitsIterative(61000) == 16)
    print("  ", revDigitsIterative(-15) == -51)
    print("  ", revDigitsIterative(123456789) == 987654321)

def paint_star_test():
    print("Test task 6a")
    paint_star(0, 0, 100)

def sky_test():
    print("Test task 6b (Draw 25 stars)")
    sky(25)

def squares_test():
    print("Test task 6c")
    squares()

def fractal_squares_test():
    print("Test task 6c")
    fractal_squares()
    
def foldl_foldr_test():
    print("Test task 8")
    print("  foldl")
    print("    ", foldl(lambda x,y: x+y, 64, [7, 8, 9, 10]) == 98)
    print("    ", foldl(lambda x,y: x/y, 64, [7, 8, 9, 10]) == 0.012698412698412698)
    print("    ", foldl(lambda x,y: x + str(y), "", [7, 8, 9, 10]) == "78910")
    print("  foldr")
    print("    ", foldr(lambda x,y: x+y, 64, [7, 8, 9, 10]) == 98)
    print("    ", foldr(lambda x,y: x/y, 64, [7, 8, 9, 10]) == 0.012698412698412698)
    print("    ", foldr(lambda x,y: x + str(y), "", [7, 8, 9, 10]) == "10987")

mersenne_prime_test()
print()
repeats_test()
print()
test_time_searches_test()
print()
apply_if_test()
print()
rev_digits_recursive_test()
print()
input("Press a button to start test for task 6a...")
paint_star_test()
print()
input("Press a button to start test for task 6b...")
turtle.clear()
sky_test()
print()
input("Press a button to start test for task 6c...")
turtle.clear()
turtle.bgcolor("white")
squares_test()
print()
input("Press a button to start test for task 6d...")
turtle.clear()
fractal_squares_test()
input("")
rev_digits_iterative_test()
print()
foldl_foldr_test()
print()
