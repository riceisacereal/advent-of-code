f = open("input.txt")
queue = [int(f.readline()), int(f.readline()), int(f.readline())]
prevSum = currentSum = sum(queue)
count = 0

while True:
    line = f.readline()
    if not line:
        break
    prevSum = currentSum
    queue.pop(0)
    queue.append(int(line))
    currentSum = sum(queue)

    if currentSum > prevSum:
        count += 1

f.close()

print(count)
