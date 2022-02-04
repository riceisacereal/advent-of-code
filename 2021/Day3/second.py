def find_common(commonList, index, findCommon):
    if len(commonList) < 2 or index > 11:
        return commonList

    trackOne = []
    trackZero = []
    for line in commonList:
        currentBit = int(line[index])
        if currentBit == 1:
            trackOne.append(line)
        else:
            trackZero.append(line)

    index += 1
    if (len(trackZero) > (len(commonList) / 2)) == findCommon:
        return find_common(trackZero, index, findCommon)
    else:
        return find_common(trackOne, index, findCommon)


f = open("input.txt")
lines = f.readlines()
oxygen = find_common(lines, 0, True)
co2 = find_common(lines, 0, False)
f.close()

print(oxygen)
print(co2)

print(int(oxygen[0], 2) * int(co2[0], 2))
