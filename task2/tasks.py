from functools import reduce

def task1(a, b, c):
    if a < b && b < c:
        return "Stark ansteigend"
    elif a > b && b > c:
        return "Stark absteigend"
    else
        return "Weder noch."

def task2(lst):
    if len(lst) == 0:
        return 0

    n = 1
    for x in lst
        n *= x

    return n

# task3
def weekday(year, month, day):
    y0 = year - (14-month) / 12
    x = y0 + (y0 / 4) - (y0 / 100) + (y0 / 400)
    m0 = month + 12 * ((14-month) / 12) - 2
    n = (day + x + ((31 * m0) / 12)) % 7
    n = int(n)
    days = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"]
    return days[n]

import random
# task4
def gluecksspieler(startingMoney):
    spins = 0
    currentMoney = startingMoney
    
    while currentMoney > 0 && currentMoney < (startingMoney * 2):
        spins += 1
        if random.randint(0,1):
            currentMoney -= 1
        else
            currentMoney += 1
    
    if currentMoney <= 0:
        print("You lost after " + spins + " tries.")
    else:
        print("You doubled your money after " + spins + " tries.")
    
    return spins

def sum_of_divisors(num):
    return sum([i for i in range(1, num) if num % i == 0])

def task5(m, n):
    return sum_of_divisors(m) == sum_of_divisors(n)

def task6(speed):
    result = ""
    speed = int(speed)
    if speed < 1:
        result = "Windstille"
    elif speed in range(1,4):
        result = "leiser Zug"
    elif speed in range(4,7):
        result = "leichte Brise"
    elif speed in range(7,11):
        result = "schwache Brise"
    elif speed in range(11,16):
        result = "mäßige Brise"
    elif speed in range(16, 22):
        result = "frische Brise"
    elif speed in range(22, 28):
        result = "starker Wind"
    elif speed in range(28, 34):
        result = "steifer Wind"
    elif speed in range(34, 41):
        result = "stürmischer Wind"
    elif speed in range(41, 48):
        result = "Sturm"
    elif speed in range(48, 56):
        result = "schwerer Sturm"
    elif speed in range(56, 65):
        result = "orkanartiger Sturm"
    elif speed > 64:
        result = "Orkan"
    return result



