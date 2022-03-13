def main(file_name):
    f = open(file_name)
    lines = f.readlines()

    # read all numbers
    array = [int(x) for x in lines[0].split(',')]

    sumCrab = sum(array)
    print("Total crab distance:", sumCrab)
    average = round(sumCrab / len(array)) - 1
    print("Average:", average)

    totalFuel = 0
    for i in range(len(array)):
        distance = abs(average - array[i])
        totalFuel += (distance * (distance + 1)) / 2

    f.close()
    return totalFuel


print("Total fuel:", main("input.txt"))
