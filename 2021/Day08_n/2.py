ASCII_OF_LOWER_A = 97
fiveGroup = {3: 2, 5: 3, 4: 5}
sixGroup = {5: 0, 4: 6, 6: 9}


def get_matches(known, digit, length):
    matches = 0
    # find matches with 1 and 4
    for i in range(length):
        index = ord(digit[i]) - ASCII_OF_LOWER_A
        matches += known[0][index]
        matches += known[1][index]

    return matches


def get_digit(known, digit):
    length = len(digit)
    if length == 2:
        return 1
    elif length == 3:
        return 7
    elif length == 4:
        return 4
    elif length == 7:
        return 8
    elif length == 5:  # 5 group: 2, 3, 5
        matches = get_matches(known, digit, 5)
        return fiveGroup[matches]
    elif length == 6:  # 6 group: 0, 6, 9
        matches = get_matches(known, digit, 6)
        return sixGroup[matches]


def get_mapped(num):
    # maps a seven segment display into
    # [a, b, c, d, e ,f ,g]
    mapped = [0] * 7
    for i in range(len(num)):
        mapped[ord(num[i]) - ASCII_OF_LOWER_A] = 1

    return mapped


def main(file_name):
    f = open(file_name)
    lines = f.readlines()

    total = 0
    for line in lines:
        lineSplit = line.split(" | ")
        numbers = lineSplit[0].split(' ')
        # find 1 and 4
        knownNumbers = [[]] * 2
        for num in numbers:
            if len(num) == 2:
                knownNumbers[0] = get_mapped(num)
            elif len(num) == 4:
                knownNumbers[1] = get_mapped(num)

        output = lineSplit[1].strip().split(' ')
        for i in range(4):
            # get corresponding digit
            digit = get_digit(knownNumbers, output[i])
            # multiply and add corresponding position to total
            position = 10 ** (3 - i)
            total += digit * position

    f.close()
    return total


print("Total of output:", main("input.txt"))
