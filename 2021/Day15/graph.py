def get_blank_map(cavern, fill_value):
    width = len(cavern[0])
    height = len(cavern)

    unexplored_map = [[]] * height
    for i in range(height):
        unexplored_map[i] = [fill_value] * width

    return unexplored_map


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
