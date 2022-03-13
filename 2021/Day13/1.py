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


def fold(coors, axis, fold_line):
    for c in coors:
        if c[axis] > fold_line:
            c[axis] = int(2 * fold_line) - c[axis]


def count(edges, coors):
    rows, cols = edges[1] + 1, edges[0] + 1
    paper = [[]] * rows
    total = 0
    for i in range(rows):
        paper[i] = [0] * cols

    for c in coors:
        if paper[c[1]][c[0]] == 0:
            total += 1
            paper[c[1]][c[0]] += 1

    return total


def main(file_name):
    f = open(file_name)
    line = f.readline()
    coors = []
    edges = [-1, -1]

    while line:
        if line[0] == '\n':
            """if reached end break"""
            break
        else:
            coors.append(process_and_track_edges(edges, line))
        line = f.readline()

    # get folding line
    line = f.readline()
    axis = 0 if line[11] == 'x' else 1
    fold_line = int(line[13:])
    if fold_line < edges[axis]:
        edges[axis] = fold_line

    fold(coors, axis, fold_line)
    print("Total dots:", count(edges, coors))
    f.close()


main("input.txt")
