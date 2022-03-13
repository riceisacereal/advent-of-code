closeToOpen = {')': '(', ']': '[', '}': '{', '>': '<'}
errorScore = {')': 3, ']': 57, '}': 1197, '>': 25137}


def main(fileName):
    f = open(fileName)
    line = f.readline()[:-1]
    points = 0
    while line:
        track = []
        for i in range(len(line)):
            curr = line[i]
            correspond = closeToOpen.get(curr)
            if correspond:  # closing
                if (len(track) != 0) and track[-1] == correspond:
                    track.pop()
                else:
                    # print(line, "Invalid char:", curr, "index", i)
                    points += errorScore.get(curr)
                    break
            else:  # opening
                track.append(curr)

        line = f.readline()[:-1]

    f.close()
    return points


print("Total points:", main("input.txt"))
