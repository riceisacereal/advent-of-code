f = open("input.txt")
lines = f.readlines()
countOne = [0] * 12

for line in lines:
    for i in range(12):
        countOne[i] += int(line[i])

gamma = epsilon = 0

for i in range(11, -1, -1):
    currentNumber = 2 ** (11 - i)
    if countOne[i] > 500:
        gamma += currentNumber
    else:
        epsilon += currentNumber

f.close()

print(gamma)
print(epsilon)
print(gamma * epsilon)
