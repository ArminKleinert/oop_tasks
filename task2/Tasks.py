# task 1
def task_1(a, b, c):
    if a < b and b < c:
        return "Stark ansteigend"
    elif a > b and b > c:
        return "Stark absteigend"
    else:
        return "Weder noch."

# task 2
def list_product(lst):
    if len(lst) == 0:
        return 0

    n = 1
    for x in lst:
        n *= x

    return n

# task 3
def weekday(day, month, year):
    y0 = year - ((14 - month) // 12)
    x = y0 + (y0 // 4) - (y0 // 100) + (y0 // 400)
    m0 = month + (12 * ((14 - month) // 12)) - 2
    n = (day + x + ((31 * m0) // 12)) % 7
    days = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"]
    return days[n]

import random
# task4
def gluecksspieler(starting_money):
    spins = 0
    current_money = starting_money

    while current_money > 0 and current_money < (starting_money * 2):
        spins += 1
        if random.randint(0,1):
            current_money -= 1
        else:
            current_money += 1

    if current_money <= 0:
        print("You lost after " + str(spins) + " tries.")
    else:
        print("You doubled your money after " + str(spins) + " tries.")

    return spins

def sum_of_divisors(num):
    return sum([i for i in range(1, num) if num % i == 0])

# task 5
def amicable_numbers(m, n):
    return (sum_of_divisors(m) == n) and (m == sum_of_divisors(n))

# task 6
def word_for_speed_of_wind(knots):
    result = ""
    knots = int(knots)
    if knots < 1:
        result = "Windstille"
    elif knots in range(1,4):
        result = "leiser Zug"
    elif knots in range(4,7):
        result = "leichte Brise"
    elif knots in range(7,11):
        result = "schwache Brise"
    elif knots in range(11,16):
        result = "mäßige Brise"
    elif knots in range(16, 22):
        result = "frische Brise"
    elif knots in range(22, 28):
        result = "starker Wind"
    elif knots in range(28, 34):
        result = "steifer Wind"
    elif knots in range(34, 41):
        result = "stürmischer Wind"
    elif knots in range(41, 48):
        result = "Sturm"
    elif knots in range(48, 56):
        result = "schwerer Sturm"
    elif knots in range(56, 65):
        result = "orkanartiger Sturm"
    elif knots > 64:
        result = "Orkan"
    return result

# ------------------------------------------

def task_1_test():
    print("Testfunction for task 1")
    arg_a = int(input("a: "))
    arg_b = int(input("b: "))
    arg_c = int(input("c: "))
    print(task_1(arg_a, arg_b, arg_c))

def task_2_test():
    print("Testfunction for task 2")
    print("Input numbers, divided by ',' and confirm with enter.")
    temp = input("") # Get input
    temp1 = temp.split(",") # Split by comma
    temp2 = map(lambda s: s.strip(), temp1) # Remove whitespace
    arg_list = list(map(int, temp2)) # Turn into list of numbers
    print(list_product(arg_list))
    
def task_3_test():
    print("Testfunction for task 3 (weekday)")
    day   = int(input("Day:   "))
    month = int(input("Month: "))
    year  = int(input("Year:  "))
    print(weekday(day, month, year))

def task_4_test():
    print("Testfunction for task 4 (gluecksspieler)")
    money = int(input("Starting money: "))
    gluecksspieler(money)

def task_5_test():
    print("Testfunction for task 5 (befreundete Zahlen)")
    n1 = int(input("Number 1: "))
    n2 = int(input("Number 2: "))
    print(amicable_numbers(n1, n2))
    
def task_6_test():
    print("Testfunction for task 6 (Windgeschwindigkeit)")
    speed = int(input("Knots: "))
    print(word_for_speed_of_wind(speed))

