def main(inputLines):
    # read all numbers
    array = inputLines[0].split(',')
    for i in range(len(array)):
        string = array[i]
        array[i] = int(string)

    sumCrab = sum(array)
    print("Total crab distance:", sumCrab)
    average = round(sumCrab / len(array)) - 1
    print("Average:", average)

    totalFuel = 0
    for i in range(len(array)):
        distance = abs(average - array[i])
        totalFuel += (distance * (distance + 1)) / 2

    return totalFuel


f = open("input.txt")
lines = f.readlines()

print("Total fuel:", main(lines))

f.close()