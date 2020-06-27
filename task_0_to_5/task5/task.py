
# SECTION Task 1

"""
{P} = { a > 0 ^ b > 0 ^ c < 0 }

a = a + b - c # a > 0 bewiesen
# R = { a > 0 ^ b > 0 ^ c < 0 }
d = b
# R = { a > 0 ^ b > 0 ^ c < 0 }
b = a - b - c # b > 0 bewiesen, Ausdruck wird von a dominiert
# R = { a > 0 ^ b > 0 ^ c < 0 & ^ == a - d - c }
c = -c # hier wird c positiv; c > 0 bewiesen

# d > 0
{Q} = { a > 0 & b > 0 & c > 0 & b == a - d + c }
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
{Q} = { x >= 0 ^ (x * y + z) == c }

Branch 1 {P ^ B} S {Q}
{Q ^ B} = { (x >= 0 ^ x * y + z == c) ^ (x % 2 == 0) }
x = x // 2
{(x // 2 >= 0) ^ (x // 2 * y + z) == c } y = y + y
{(x // 2 >= 0) ^ (x // 2 * (y+y) + z == c)}
Da y+y equivalent zu 2*y ist und der Wert in (x//2)*(y+y) mit einer halibierten Zahl multipliziert wird, kann die Rechnung gekürzt werden zu (x*y+z).
{(x // 2 >= 0) ^ (x * y + z) == c}
Wenn (x//2 >= 0), dann muss auch (x>=0) gelten:
{(x >= 0) ^ (x * y + z) == c}
Dies ist gleich {P}, womit die Formel für diesen Branch bewiesen wurde.


Branch 2 {P ^ ~B} S {Q} (Wobei ~B bedeutet, dass die Bedingung nicht erfüllt ist)
{Q ^ ~B} = {x >= 0 ^ (x * y + z) == c ^ (x % 2 != 0)}
(x % 2 != 0) ist gleich (x % 2 == 1), da x größer als 0 ist. Damit muss auch (x >= 1) gelten:
{x >= 1 ^ (x * y + z) == c ^ (x % 2 == 1)}
{x >= 1 ^ (x * y + z) == c} x = x - 1
{(x-1) >= 1 ^ ((x-1) * y + z) == c} z = z + y
{(x-1) >= 1 ^ ((x-1) * y + (z+y)) == c}
Die Formel lässt sich umstellen zu
{(x-1) >= 1 ^ ((x-1) * y + y + z) == c}
Aus der Addition von y nach (x-1)*y folgt
{(x-1) >= 1 ^ (x * y + z) == c}
Wenn wir (x-1)>=1 wieder zu x>=0 umstellen, erhalten wir
{x >= 1 ^ (x * y + z) == c}
was gleich {P} ist. Damit ist die Formel bewiesen.


Die Bedingungen für Branch 1 und 2 gelten. Damit ist die ganze gegebene Programmformel gültig.

"""

# SECTION Task 3

"""
{P} = { b > 0 ^ a > 0 }
counter = 1
power = b
{INV} = { (b > 0) ^ (power == b**counter) ^ (a >= counter >= 0) }
while counter < a:
    power = power * b
    counter = counter + 1
{Q} = { power == b ** a }

{P} = { b > 0 ^ a > 0 }
{INV} = { (b > 0) ^ (power == b**counter) ^ (a >= counter >= 0) }
{Q} = { power == b ** a }

Zu zeigen:
{P} S {INV} und {INV ^ ~B} S {Q}

{INV ^ ~B} = {(b > 0) ^ (power == b ** counter) ^ (a >= counter >= 0) ^ a <= counter}
Die Bedingungen a>=counter und a<=counter lassen sich zusammenfassen zu a==counter:
{(b > 0) ^ (power == b ** counter) ^ (a >= 0) ^ a == counter}
counter = counter + 1
{(b > 0) ^ (power == b**a + 1) ^ (a >= 0) ^ (a + 1 == counter +1)} # counter+1 ersetzt durch a+1
power = power * b
{(b > 0) ^ (power * b == b ** a+1) ^ (a >= 0) ^ (a+1 == counter+1)}
Wenn man in (power * b == b ** a+1) beide Seiten durch b teilt, erhält man (power == b ** a).
{(b > 0) ^ (power == b ** a) ^ (a >= 0) ^ (a == counter)}
Die ersten Bedingungen sind identisch zu denen aus denen von {Q}. Damit bleibt nur zu zeigen dass (a==counter).
Der Grund dafür wurde bereits oben genannt: Er geht aus der Kombination von INV und ~B hervor:
(a >= counter >= 0) ^ (a <= counter) => a == counter

Zu zeigen ist noch {P}S{INV}
{ (b > 0) ^ (power == b**counter) ^ (a >= counter >= 0) } power = b
{ (b > 0) ^ (b == b ** counter) ^ (a >= counter >= 0) } counter = 1
{ (b > 0) ^ (b == b ** 1) ^ (a >= 1 >= 0) }
Daher gilt
{(b > 0) ^ (b == b) ^ (a >= 1)}
(b == b) ist offensichtlich.
Aus (a >= 1) geht hervor dass (a > 0).
Also
{ b > 0 ^ a > 0 }
Das ist gleich {P}.

Dadurch ist erwiesen, dass die Programmformel gilt.

# Nachtrag:
In Kurz:
Der loop wird a-1 Mal ausgeführt, aber mindestens 0 mal ausgeführt.
power = b * (b * a-1) # => power = b ** a
counter = 1 + (a-1) # => counter = a
{Q} ist damit erfüllt.
Die Bedingung (b > 0) aus {INV} geht aus {P} hervor. Damit ist auch {INV} bewiesen.
Der Loop wird a-1 Male ausgeführt, da (a > 0) (laut {P}) ist und counter von 1 bis a zählt und counter mit jeder Iteration nur um 1 inkrementiert wird. Wenn a==1 ist, wird der loop nicht ausgeführt.
power behält also den Initialwert von b (womit power==b**a) bewiesen ist, da (a==1).

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

# Hilfsfunktion!
# Das Ergebnis ist nur richtig wenn n >= 0
def binaryDigits(n):
    return len("{0:b}".format(n))


def binaryDigits1(Zahl):
    hilf = Zahl
    bits = 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)
    while hilf > 1:
        hilf = hilf // 2
        bits = bits + 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)
    return bits

"""
def binaryDigits2(Zahl):
    hilf = Zahl
    bits = 1
    assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)
    while hilf > 1:
        hilf = hilf // 2
        bits = bits + 1
    assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)
    return bits


def binaryDigits3(Zahl):
    hilf = Zahl
    bits = 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)
    while hilf > 1:
        hilf = hilf // 2
        bits = bits + 1
    assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)
    return bits
"""

# SUBSECT 4a
"""
1. assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1)

2. assert hilf >= 0 and (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits)

3. assert hilf >= 0 and (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits)

Die richtige Antowrt ist 1.

hilf wird bei jedem Durchlauf der Schleife halbiert, während bits inkrementiert wird.
Nachdem hilf einmal halbiert wurde, muss binaryDigits(Zahl) schon einen höheren Wert ergeben als binaryDigits(hilf).
Laut 3. müsste also nach der Schleife gelten, dass
   (hilf >= 0 ^ binaryDigits(hilf) < binaryDigits(Zahl) ^ bits > 1 ^ (binaryDigits(hilf) + bits == binaryDigits(Zahl) + bits))
   Was offensichtlich nie stimmen kann.

Laut 2. müsste gelten
   (hilf >= 0 ^ binaryDigits(hilf) < binaryDigits(Zahl) ^ bits > 1 ^ (binaryDigits(hilf) + 1 == binaryDigits(Zahl) + bits))

Laut 1.
   (hilf >= 0 ^ binaryDigits(hilf) < binaryDigits(Zahl) ^ bits > 1 ^ (binaryDigits(hilf) + bits == binaryDigits(Zahl) + 1))
   Dies ist richtig.


Die obige Beschreibung trifft nur zu, wenn Zahl größer als 1 ist.
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

def checkOuter(seq, j, r):
    return j >= 1 and j <= len(seq) and len(seq) == len(r) + 1

def checkInner(seq, k, j, key):
    return k < j and k >= -1 and key is not None

def insertsort (seq): 
    j = 1
    r = range(1, len(seq))
    # {INV} = {j >= 1 ^ j <= len(seq) ^ len(seq) == (len(r)+1)}
    assert checkOuter(seq, j, r)
    while j in r:
        key = seq[j]
        k = j-1
        # {INV} = {k < j and k >= -1 and key is not None}
        assert checkInner(seq, k, j, key)
        while k>=0 and seq[k]>key:
            seq[k+1] = seq[k]
            k = k-1
            seq[k+1] = key
        # {INV} = {k < j and k >= -1 and key is not None}
        assert checkInner(seq, k, j, key)
        j += 1
    # {INV} = {j >= 1 ^ j <= len(seq) ^ len(seq) == (len(r)+1)}
    assert checkOuter(seq, j, r)

import random

arr = list(range(0, 1000))
random.shuffle(arr)
print(arr)
insertsort(arr)
print(arr)

# SUBSECT 5b


# SECTION 6

# SUBSECT 6a

"""
Die wichtigste Invariante ist, dass sich der pivot während der Bearbeitung
der Schleife konstant verhält.

{INV} = {pivot == A[low]}

Dieser Check ist möglich, da low und high ebenfalls konstante Werte sind.
"""

def partition(A, low, high):
    pivot = A[low]
    i = low
    #{INV} = {pivot == A[low]}
    for j in range(low+1, high+1):
        if (A[j] < pivot):
            i = i + 1
            A[i], A[j] = A[j], A[i]
    #{INV} = {pivot == A[low]}
    A[i], A[low] = A[low], A[i]
    return i

# SUBSECT 6b

"""
Die Implementation der Funktion verlässt sich für den Vergleich der Elemente 
mit dem Pivot darauf, dass sich der Pivot nicht verändert.

---
Oder es ist eine Fangfrage, weil in der Aufgabe eine for-Schleife statt einer
while-Schleife genutzt wird.
"""

