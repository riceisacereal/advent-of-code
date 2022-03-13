def fold(coors, axis, fold_line):
    for c in coors:
        if c[axis] > fold_line:
            c[axis] = int(2 * fold_line) - c[axis]


def visualize(edges, coors):
    paper = [[]] * edges[1]
    for i in range(edges[1]):
        paper[i] = ['丶'] * edges[0]

    for c in coors:
        paper[c[1]][c[0]] = '䨻'

    for row in paper:
        for col in row:
            print(col, end='')
        print("")


def process_and_track_edges(edges, line):
    x, y = (int(x) for x in line[:-1].split(','))
    if edges[0] == -1:
        edges[0], edges[1] = x, y
    else:
        if x > edges[0]:
            edges[0] = x
        if y > edges[1]:
            edges[1] = y
    return [x, y]


def main(file_name):
    f = open(file_name)
    line = f.readline()
    coors = []
    """
    visualizing each fold requires you to set the edges at the max coordinates here
    """
    edges = [-1, -1]

    while line:
        if line[0] == '\n':
            """if reached end break"""
            break
        else:
            coors.append(process_and_track_edges(edges, line))
        line = f.readline()

    """get folding line"""
    line = f.readline()
    while line:
        """do the folds"""
        axis = 0 if line[11] == 'x' else 1
        fold_line = int(line[13:])
        if fold_line < edges[axis]:
            edges[axis] = fold_line

        fold(coors, axis, fold_line)
        # visualize(edges, coors)
        line = f.readline()

    visualize(edges, coors)


main("input.txt")
