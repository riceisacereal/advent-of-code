def flash(octopi, row, col):
    if row < 0 or row >= len(octopi) or col < 0 or col >= len(octopi[0]):
        return 0

    num = octopi[row][col]
    if num == 0:  # already flashed this day
        return 0
    else:  # increment and maybe flash
        octopi[row][col] += 1
        if octopi[row][col] > 9:
            octopi[row][col] = 0
            octo_flashes = 0
            for i in range(-1, 2, 1):
                for j in range(-1, 2, 1):
                    octo_flashes += flash(octopi, row + i, col + j)

            return 1 + octo_flashes
        else:
            return 0


def day(octopi):
    to_flash = []
    for row in range(len(octopi)):
        for col in range(len(octopi[row])):
            octopi[row][col] += 1
            if octopi[row][col] > 9:
                to_flash.append([row, col])

    daily_flashes = 0
    while len(to_flash) > 0:
        index = to_flash.pop()
        daily_flashes += flash(octopi, index[0], index[1])

    return daily_flashes


def main(fileName):
    f = open(fileName)

    octopi = []
    line = f.readline()
    while line:
        nums = []
        for i in range(len(line) - 1):
            nums.append(int(line[i]))
        octopi.append(nums)

        line = f.readline()

    '''
    part 1 code

    flashes = 0
    for i in range(100):
        flashes += day(octopi)

    return flashes
    '''

    # part 2 code
    i = 1
    while day(octopi) != len(octopi) * len(octopi[0]):
        i += 1

    f.close()
    return i


print(main("input.txt"))
