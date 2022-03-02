bingoBoardNums = [[]] * 100

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
        if len(bingoBoardNums[n]) == 0:
            bingoBoardNums[n] = [entry]
        else:
            bingoBoardNums[n].append(entry)
    rowNum += 1

# doing the bingo
boardTrack = []
for i in range(boardNum + 1):
    track = [0] * 10
    boardTrack.append(track)

losingBoard = 0
losingNumber = 0
found = False
chosen = []
winningBoards = []
for i in range(len(toBeChosen)):
    currentChoice = int(toBeChosen[i].strip())
    relatedBoards = bingoBoardNums[currentChoice]
    chosen.append(currentChoice)

    for entry in relatedBoards:
        board = entry[0]
        row = entry[1]
        col = entry[2]
        boardTrack[board][row] += 1
        boardTrack[board][col + 5] += 1
        a = boardTrack[board][row]
        b = boardTrack[board][col + 5]

        if (a == 5 or b == 5) and (board not in winningBoards):
            winningBoards.append(board)
            if len(winningBoards) == 100:
                losingBoard = board
                losingNumber = currentChoice
                found = True
                break

    if found:
        break

# calculating result
total = 0
for i in range(len(bingoBoardNums)):
    if i in chosen:
        continue

    for entry in bingoBoardNums[i]:
        board = entry[0]
        if board > losingBoard:
            break
        elif board == losingBoard:
            total += i
            break

f.close()

print(bingoBoardNums)
print(losingBoard)
print(losingNumber)
print(total)
print(total * losingNumber)
