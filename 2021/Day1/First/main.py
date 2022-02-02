f = open("../input.txt")
lines = f.readlines()
prev = current = int(lines[0])
increase = 0

for line in lines:
    prev = current
    current = int(line)
    if current > prev:
        increase = increase + 1

f.close()

print(increase)
