def main(fileName):
    f = open(fileName)

    currLine = f.readline()[:-1]
    lineSize = len(currLine)
    prevLine = [9] * lineSize
    totalRiskLevel = 0
    while currLine:
        currLowIndexes = [0] * lineSize

        # check if flows forward
        for i in range(lineSize):
            if int(currLine[i]) < int(prevLine[i]):
                currLowIndexes[i] = 1
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
                if prevLowIndexes[i] and (int(prevLine[i]) < int(currLine[i])):
                    totalRiskLevel += 1 + int(prevLine[i])
        else:
            for i in range(lineSize):
                if currLowIndexes[i]:
                    totalRiskLevel += 1 + int(prevLine[i])

    f.close()
    return totalRiskLevel


print("Low points:", main("input.txt"))
