def get_blank_map(cavern, fill_value):
    width = len(cavern[0])
    height = len(cavern)

    blank_map = [[]] * height
    for i in range(height):
        blank_map[i] = [fill_value] * width

    return blank_map


def parse_graph(file_name):
    f = open(file_name)

    cavern = []
    line = f.readline()[:-1]
    while line:
        row = [0] * len(line)
        for i in range(len(line)):
            row[i] = int(line[i])
        cavern.append(row)

        line = f.readline()[:-1]

    f.close()
    return cavern


def extend_graph(cavern):
    width = len(cavern[0])
    height = len(cavern)

    """Extend to the right"""
    for big_col in range(1, 5, 1):
        start_col = big_col * width
        for row in range(height):
            for col in range(start_col, start_col + width):
                num = cavern[row][col - width] + 1
                if num > 9:
                    num = 1
                cavern[row].append(num)

    """Extend tiles down, and append another in the end"""
    for d in range(4):
        for row in range(height * d, (height * d) + height):
            """Add first column"""
            cavern.append([cavern[row][width]])
            new_row_index = len(cavern) - 1

            """Append the rest to new column"""
            for col in range(width + 1, 5 * width):
                cavern[new_row_index].append(cavern[row][col])

            """Extend another tile at the end"""
            last = len(cavern[new_row_index])
            for col in range(last - width, last):
                num = cavern[new_row_index][col] + 1
                if num > 9:
                    num = 1
                cavern[new_row_index].append(num)
