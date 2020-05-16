#!/usr/bin/env python3

# Imports
import math
import sys

# This function calculates the surface size of a triangle.
# The parameters a, b and c must always be numbers.
# Returns a number.
def triangleSurface(a, b, c):
  s = (a+b+c) / 2
  temp = (s * (s-a) * (s-b) * (s-c))

  if temp <= 0:
    raise ValueError("Impossible triangle sides.")

  res = math.sqrt(temp)
  return res

# Bind console args to a local variable.
argv = sys.args

# Linux uses the script name as the first argument, windows doesn't.
# To make work easier, this code removes the script name.
if (argv[0] == __file__):
  argv.pop(0)

# Check if we have enough arguments (Exactly 3 are needed)
if len(argv) < 3:
  print("Program needs exactly 3 arguments.", file=sys.stderr)
  sys.exit(1)

# Convert the console arguments to integers
# and bind them to a, b and c.
# If one of the parameters can not be converted for some reason,
# exit with error message.
try:
  a, b, c = map(float, argv)
except ValueError as ve:
  print("One of the parameters could not be converted to an int.\n",
        file=sys.stderr)
  sys.exit(1)

# Call triangleSurface.
# If something goes wrong, exit with error message.
try:
    result = triangleSurface(a, b, c)
except ValueError as ve:
    print(str(ve), file=sys.stderr)
    sys.exit(1)

# Finally give the result to the user.
print(result)

