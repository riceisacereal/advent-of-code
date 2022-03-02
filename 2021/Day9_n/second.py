originalTerrain = []


def get_low_points(fileName):
    f = open(fileName)

    currLine = f.readline()[:-1]
    lineSize = len(currLine)
    prevLine = [9] * lineSize
    lowPointMap = []
    while currLine:
        currLowIndexes = [0] * lineSize
        line = []
        # check if flows forward
        for i in range(lineSize):
            line.append(int(currLine[i]))
            if int(currLine[i]) < int(prevLine[i]):
                currLowIndexes[i] = 1
        originalTerrain.append(line)
        # flow to right
        for i in range(lineSize - 1):
            if int(currLine[i]) and (int(currLine[i]) > int(currLine[i + 1])):
                currLowIndexes[i] = 0
        # flow to left
        for i in range(lineSize - 1, 0, -1):
            if int(currLine[i]) and (int(currLine[i]) > int(currLine[i - 1])):
                currLowIndexes[i] = 0

        prevLine = currLine
        currLine = f.readline()[:-1]

        if currLine:
            prevLowIndexes = currLowIndexes
            # determine if previous low points were actually low points
            for i in range(lineSize):
                if prevLowIndexes[i] and (int(prevLine[i]) > int(currLine[i])):
                    prevLowIndexes[i] = 0
            lowPointMap.append(prevLowIndexes)
        else:
            lowPointMap.append(currLowIndexes)

    f.close()
    return lowPointMap


def swap(basins, a, b):
    temp = basins[a]
    basins[a] = basins[b]
    basins[b] = temp


def down_heap(basins):
    if basins[0] > basins[1]:
        swap(basins, 0, 1)
    if basins[0] > basins[2]:
        swap(basins, 0, 2)


def three_biggest_basins(basins, newBasin):
    if len(basins) < 2:
        basins.append(newBasin)
    elif len(basins) == 2:
        basins.append(newBasin)
        down_heap(basins)
    else:
        if basins[0] < newBasin:
            basins[0] = newBasin
            down_heap(basins)


def expand(row, col):
    if (row < 0) or (row >= len(originalTerrain)) or (col < 0) or (col >= len(originalTerrain[0])):
        return 0

    thisNum = originalTerrain[row][col]
    if thisNum == 9:
        return 0
    else:
        originalTerrain[row][col] = 9
        up = expand(row - 1, col)
        down = expand(row + 1, col)
        left = expand(row, col - 1)
        right = expand(row, col + 1)
        return 1 + up + down + left + right


def main(fileName):
    lowPointsMap = get_low_points(fileName)
    basins = []
    for row in range(len(lowPointsMap)):
        for col in range(len(lowPointsMap[1])):
            if lowPointsMap[row][col]:
                three_biggest_basins(basins, expand(row, col))

    print(basins)
    print(basins[0] * basins[1] * basins[2])


main("input.txt")
