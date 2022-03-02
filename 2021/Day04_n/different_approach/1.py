f = open("../input.txt")
bingoDraws = f.readline()[:-1].split(',')
f.readline()

bingoDrawsMapped = [0] * 100
# map draws into indexes
for i in range(len(bingoDraws)):
    num = int(bingoDraws[i].strip())
    bingoDrawsMapped[num] = i


def calculate_board_bingo(board):
    minRowNum = 101
    minColNum = []

    # for each row
    for j in range(5):
        currList = []
        for repeat in range(5):
            currList.append(bingoDrawsMapped[int(board.pop().strip())])

        minRowNum = min(minRowNum, max(currList))
        if len(minColNum) == 0:
            minColNum = currList
        else:
            for k in range(5):
                if minColNum[k] < currList[k]:
                    minColNum[k] = currList[k]

    bingoIn = min(minRowNum, min(minColNum))
    return bingoIn


# calculate the fastest bingo for each board
fastestBoardAndDraw = [101, 101]
winningBoard = []
boardCount = 0
while True:
    line = f.readline()
    if not line:
        break

    boardList = ''
    while line != '\n' and len(line) != 0:
        boardList += line
        line = f.readline()

    bingo = calculate_board_bingo(boardList.split())
    if bingo < fastestBoardAndDraw[1]:
        winningBoard = boardList.split()
        fastestBoardAndDraw[0] = boardCount
        fastestBoardAndDraw[1] = bingo

    boardCount += 1

for draw in range(fastestBoardAndDraw[1] + 1):
    if bingoDraws[draw] in winningBoard:
        winningBoard.remove(bingoDraws[draw])

winningBoardInt = list(map(int, winningBoard))
sumBoard = sum(winningBoardInt)
winningDraw = int(bingoDraws[fastestBoardAndDraw[1]])

f.close()

print(fastestBoardAndDraw)
print(sumBoard)
print(winningDraw)
print(winningDraw * sumBoard)
