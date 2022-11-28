def explode(line, first_digit):
    left = int(line[first_digit])
    right = int(line[first_digit + 2])

    """replace with 0 and move numbers"""
    line[first_digit - 1] = '0'
    for i in range(first_digit, len(line) - 4):
        line[i] = line[i + 4]
    """shorten list"""
    for i in range(4):
        line.pop()
    """find left"""
    for i in range(first_digit - 2, 0, -1):
        if line[i].isdigit():
            line[i] = str(int(line[i]) + left)
            break
    """find right"""
    for i in range(first_digit + 1, len(line)):
        if line[i].isdigit():
            line[i] = str(int(line[i]) + right)
            break

    return line


def split(line, digit):
    num = int(line[digit])
    left = num // 2
    right = num - left

    """expand"""
    for i in range(4):
        line.append('0')
    """move elements"""
    for i in range(len(line) - 1, digit + 3, -1):
        line[i] = line[i - 4]
    """insert pair"""
    pair = ['[', str(left), ',', str(right), ']']
    for i in range(5):
        line[digit] = pair[i]
        digit += 1


def reduce(line):
    reduced = False
    while not reduced:
        brackets = 0
        reduced = True
        for i in range(len(line)):
            symbol = line[i]
            if brackets > 4 and symbol.isdigit():
                reduced = False
                """explode"""
                explode(line, i)
                # print("e", "".join(line))
                break
            elif symbol == '[':
                brackets += 1
            elif symbol == ']':
                brackets -= 1

        if reduced:
            for i in range(len(line)):
                symbol = line[i]
                if symbol.isdigit() and int(symbol) >= 10:
                    reduced = False
                    """split"""
                    split(line, i)
                    # print("s", "".join(line))
                    break


def calculate_magnitude(line):
    if len(line) == 1:
        return int(line[0])

    brackets = 0
    middle = 0
    """identify middle"""
    for i in range(1, len(line) - 1):
        symbol = line[i]
        if symbol == '[':
            brackets += 1
        elif symbol == ']':
            brackets -= 1

        if symbol == ',' and brackets == 0:
            """found middle"""
            middle = i
            break

    left = line[1:middle]
    right = line[middle + 1:-1]
    return 3 * calculate_magnitude(left) + 2 * calculate_magnitude(right)
