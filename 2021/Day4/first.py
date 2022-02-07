bingoCardNums = [[]] * 100

f = open("input.txt")
lines = f.readlines()

toBeChosen = lines[0].split(',')
boardNum = 0
rowNum = 0

# reading all boards
for i in range(2, len(lines), 1):
    line = lines[i]
    if line == '\n':
        boardNum += 1
        rowNum = 0
        continue

    split = line.split()
    for columnNum in range(len(split)):
        n = int(split[columnNum].strip())
        entry = [boardNum, rowNum, columnNum]
        if len(bingoCardNums[n]) == 0:
            bingoCardNums[n] = [entry]
        else:
            bingoCardNums[n].append(entry)
    rowNum += 1

# doing the bingo
boardTrack = []
for i in range(boardNum + 1):
    track = [0] * 10
    boardTrack.append(track)

winningBoard = 0
winningNumber = 0
found = False
chosen = []
for i in range(len(toBeChosen)):
    currentChoice = int(toBeChosen[i].strip())
    relatedCards = bingoCardNums[currentChoice]
    chosen.append(currentChoice)

    for entry in relatedCards:
        board = entry[0]
        row = entry[1]
        col = entry[2]
        boardTrack[board][row] += 1
        boardTrack[board][col + 5] += 1
        a = boardTrack[board][row]
        b = boardTrack[board][col + 5]

        if a == 5 or b == 5:
            winningBoard = board
            winningNumber = currentChoice
            found = True
            break

    if found:
        break

# calculating result
total = 0
for i in range(len(bingoCardNums)):
    if chosen.__contains__(i):
        continue

    for entry in bingoCardNums[i]:
        board = entry[0]
        if board > winningBoard:
            break
        elif board == winningBoard:
            total += i
            break

print(bingoCardNums)
print(winningBoard)
print(winningNumber)
print(total)
print(total * winningNumber)
