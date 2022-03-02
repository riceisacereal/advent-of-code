def main(lines):
    # constructing 2d list
    track = []
    total = 0
    size = 1000
    for i in range(size):
        j = [0] * size
        track.append(j)

    for line in lines:
        total += process_line(track, line)

    return total


def find_coordinate(track, x, y):
    # take coordinates and finds corresponding entry in track
    count = track[x][y]
    if count == 1:
        track[x][y] += 1
        return 1
    else:
        track[x][y] += 1
        return 0


def process_line(track, line):
    # gets the coordinates of start and end of line,
    # determines whether it is vertical, horizontal or diagonal,
    # creates for loop
    c = line.split(' -> ')
    start = c[0].split(',')
    end = c[1].split(',')
    x1 = int(start[0])
    y1 = int(start[1])
    x2 = int(end[0])
    y2 = int(end[1].strip())

    count = 0
    if x1 == x2:
        # loop over y
        y = min(y1, y2)
        maxY = max(y1, y2)
        for y in range(y, maxY + 1):
            count += find_coordinate(track, x1, y)

    elif y1 == y2:
        # loop over x
        minX = min(x1, x2)
        maxX = max(x1, x2)
        for x in range(minX, maxX + 1):
            count += find_coordinate(track, x, y1)

    '''
    I did not for the love of god see that I only had to calculate horizontal and vertical lines
    I spent an hour trying to find the bug, but I just found bugs in my vertical line calculation,
        which is good that I at least got to fix that part
    I drew out the 10x10 example on paper and compared it with my output, like:
    
    X0123456789
    0/       /X
    1 /  /  / X
    2//X / /  X
    3 / /X/   /
    4  / *    /
    5   X/X   /
    6  / * /   
    7/X/XX  /  
    8/ / /   / 
    9    /     
    
    where I had one stroke for 1, X for 2 and an asterisk for 3, it matched perfectly
    Then I read the puzzle again and saw that I only had to consider v+h lines
    I commented out the whole else statement for diagonal lines
    It worked first try
    I am so mad and I hate myself so much 
    '''

    # else:
    #     # diagonal, loop over both
    #     incr = 1
    #     if x1 > x2:
    #         # x increase x2 -> x1
    #         # y increase y2 -> y1
    #         minX = x2
    #         maxX = x1
    #         y = y2
    #         if y1 < y2:
    #             # y decrease y2 -> y1
    #             incr = -1
    #
    #     else:
    #         # x increase x1 -> x2
    #         # y increase y1 -> y2
    #         minX = x1
    #         maxX = x2
    #         y = y1
    #         if y2 < y1:
    #             # y decrease y1 -> y2
    #             incr = -1
    #
    #     for x in range(minX, maxX + 1):
    #         count += find_coordinate(track, x, y)
    #         y += incr

    return count


f = open("input.txt")
allLines = f.readlines()

print(main(allLines))

f.close()
