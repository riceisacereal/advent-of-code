def add_no_dupe(folded, index, c):
    bucket = folded[index]
    if not bucket:
        # create bucket
        folded[index] = [c]
        return
    else:
        # check for duplicates in bucket
        for e in bucket:
            if e[0] == c[0] and e[1] == c[1]:
                return
        bucket.append(c)


def get_size(buckets):
    size = 0
    for bucket in buckets:
        size += len(bucket)
    return size


def fold(buckets, line):
    n = int(round(get_size(buckets) / 10))
    folded = [[]] * n

    axis = 0 if line[11] == 'x' else 1
    fold_line = int(line[13:])

    for bucket in buckets:
        for c in bucket:
            if c[axis] > fold_line:
                c[axis] = int(2 * fold_line) - c[axis]
            index = c[axis] % n
            add_no_dupe(folded, index, c)

    return folded


def main(file_name):
    f = open(file_name)
    line = f.readline()
    coors = []

    while line:
        if line[0] == '\n':  # if reached end break
            break
        else:
            # split and convert to coordinates all in one line
            coors.append([int(x) for x in line[:-1].split(',')])
        line = f.readline()

    buckets = [coors]
    # get folding line
    line = f.readline()
    buckets = fold(buckets, line)
    print("Total dots:", get_size(buckets))


main("input.txt")
