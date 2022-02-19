import time


def pointers(newFish, days):
    age6Pointer = 6
    for i in range(days):
        age6Pointer = (age6Pointer + 1) % 9
        newFish[age6Pointer] += newFish[(age6Pointer + 2) % 9]

    return sum(newFish)


def queue(newFish, days):
    for i in range(days):
        babyFishButActuallyOldFish = newFish.pop(0)
        newFish[6] += babyFishButActuallyOldFish
        newFish.append(babyFishButActuallyOldFish)

    return sum(newFish)


def main(lines):
    oldFish = lines[0].split(',')
    newFish = [0] * 9
    days = 256

    # read fish
    for i in range(len(oldFish)):
        newFish[int(oldFish[i])] += 1

    return pointers(newFish, days)


startTime = time.time()
f = open("input.txt")
totalFish = main(f.readlines())
print(totalFish)
print("Time taken: %s" % ((time.time() - startTime) * 1000000))

f.close()
