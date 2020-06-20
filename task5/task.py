
# SECTION Task 1

"""
P = { a > 0 ^ b > 0 ^ c < 0 }

a = a + b - c # a > 0 bewiesen
# R = { a > 0 ^ b > 0 ^ c < 0 }
d = b
# R = { a > 0 ^ b > 0 ^ c < 0 }
b = a - b - c # b > 0 bewiesen, Ausdruck wird von a dominiert
# R = { a > 0 ^ b > 0 ^ c < 0 & ^ == a - d - c }
c = -c # hier wird c positiv; c > 0 bewiesen

# d > 0
Q = { a > 0 & b > 0 & c > 0 & b == a - d + c }
"""

# SECTION Task 2

"""
{P} = { x >= 0 ^ (x * y + z) == c }
if x % 2 == 0: # Branch 1
    y = y + y
    x = x // 2
else:          # Branch 2
    z = z + y
    x = x - 1
{Q} = { x >= 0 ^ (x * y + z) == x }

Branch 1:
{P} = { x >= 0 ^ (x * y + z) == c }
{ (x % 2) == 0 } # x is 0 or a natural number divisble by 2.
y = y + y
# ???
x = x // 2 # if x == 0 then x == 0, else x == 1
{ (x == 0) v (x % 2 == 1) }
{Q} = { x >= 0 ^ (x * y + z) == x }


Branch 2:
{P} = { x >= 0 ^ (x * y + z) == c }
{ (x % 2) == 1 } # x is an un-even number.
z = z + y
# ???
x = x - 1
{ (x % 2) == 0 } # x is even now.
{Q} = { x >= 0 ^ (x * y + z) == x }

"""

# SECTION Task 3

"""
{P} = { b > 0 ^ a > 0 }
counter = 1
power = b
{INV} = { (b > 0) ^ (power == b**counter) ^ (a >= counter >= 0) }
# {INV} = { (b > 0) ^ (power == b**counter) ^ (a > counter > 0) }
while counter < a:
    power = power * b
    counter = counter + 1
# { a == counter }
{Q} = { power == b ** a }

loop unrolls to:
    power = b ** a
        Because power starts with the value of b and is multiplied with b exactly a times.
        The invariant holds because in the end a is equal to counter.
    counter = a
"""

# SECTION Task 4

"""
{P} = { Zahl >= 0 }
hilf = Zahl
bits = 1
while hilf > 1:
    hilf = hilf // 2
    bits = bits + 1
{Q] = { bits == BinaryDigits(Zahl) }
"""

def binaryDigits(n):
    return len("{0:b}".format(n))


def binaryDigits1(Zahl):
    hilf = Zahl
    bits = 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)
    while hilf > 1:
        #assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)
        hilf = hilf // 2
        #assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)
        bits = bits + 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)
    return bits


def binaryDigits2(Zahl):
    hilf = Zahl
    bits = 1
    assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)
    while hilf > 1:
        #assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)
        hilf = hilf // 2
        #assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)
        bits = bits + 1
    assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)
    return bits


def binaryDigits3(Zahl):
    hilf = Zahl
    bits = 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)
    while hilf > 1:
        #assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)
        hilf = hilf // 2
        #assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)
        bits = bits + 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)
    return bits

binaryDigits1(37)
binaryDigits2(37)
binaryDigits3(37)

# SUBSECT 4a
"""
1. assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)

2. assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)

3. assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)

Die richtige Antowrt ist 1.

hilf wird bei jedem Durchlauf der Schleife halbiert, während bits inkrementiert wird.
Nachdem hilf einmal halbiert wurde, muss binaryDigits(Zahl) schon einen höheren Wert ergeben als binaryDigits(hilf).
Laut 3. müsste also nach der Schleife gelten, dass
   (hilf == 0 ^ binaryDigits(hilf) < binaryDigits(Zahl) ^ bits > 1 ^ (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits))
   Was offensichtlich nie stimmen kann.

Laut 2. müsste gelten
   (hilf == 0 ^ binaryDigits(hilf) < binaryDigits(Zahl) ^ bits > 1 ^ (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits))

Laut 1.
   (hilf == 0 ^ binaryDigits(hilf) < binaryDigits(Zahl) ^ bits > 1 ^ (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1))
   Dies ist richtig.


Alle oben genannten Beschreibung trifft nur zu, wenn Zahl größer als 1 ist.
Andernfalls treffen 1., 2. und 3. zu, da Zahl == hilf und bits == 1.

Nach der Schleife gilt in jedem Fall:
{ (Zahl == 0 v Zahl == 1) v (hilf == 0 ^ (binaryDigits(hilf) == 1) ^ (binaryDigits(Zahl) == bits)) }
"""

# SUBSECT 4b

"""
Der Initialwert für hilf ist Zahl.
Wenn Zahl == 0 oder Zahl == 1, ist die Bedingung der Schleife
    hilf > 1
also immer false. Deshalb wird die Variable bits auch ihren Initialwert (1) behalten.

Wenn Zahl > 1
Bei jeder Iteratin der Schleife wird hilf halbiert und bits um 1 inkrementiert.
Dies ist ein konstantes Verhalten ohne Ausnahmen. Daher trifft die Bedingung
    binaryDigits(Zahl) == (binaryDigits(Zahl // 2) + 1)
zu.
"""

# SECTION Task 5

# SUBSECT 5a


# SUBSECT 5b


# SECTION 6

# SUBSECT 6a


# SUBSECT 6b



