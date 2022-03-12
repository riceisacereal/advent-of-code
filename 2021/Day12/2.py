paths = 0
ORD_LOWER_A = 97


def incr():
    global paths
    paths += 1


def can_visit(path, e, twice_chance):
    """
    return:
        0 - can not visit
        1 - can visit
        2 - can visit, second small cave visit
    """
    if ord(e[0]) >= ORD_LOWER_A and e in path:  # is small cave and in path
        if twice_chance:
            # has a cave that's been visited twice
            # a new cave is visited twice
            # or cave that is visited twice is visited a third time
            return 0
        else:
            # use second chance
            return 2

    return 1


def count_paths(dic, path, e, twice_chance):
    if e == "end":  # finish a path
        incr()
        return

    path.append(e)
    edges = dic.get(e)
    for edge in edges:
        ind = can_visit(path, edge, twice_chance)
        if ind:
            count_paths(dic, path, edge, twice_chance or (ind == 2))

    path.pop()


"""
Parse dictionary
"""


def add_to_dic(dic, key, e):
    # sneaky, putting start as a destination which matters in the second part
    # do not link end to anything/link anything to start
    if key != "end" and e != "start":
        edges = dic.get(key)
        if edges:  # has existing edges
            if e in edges:
                return
            else:
                edges.append(e)
        else:  # add new edge list
            new_list = [e]
            dic[key] = new_list

    add_to_dic(dic, e, key)


def get_dic(lines) -> dict:
    cave = {}
    for line in lines:
        en, ex = line[:-1].split('-')
        add_to_dic(cave, en, ex)

    return cave


def main(file_name):
    f = open(file_name)
    lines = f.readlines()

    # parse dictionary
    cave = get_dic(lines)
    print(cave)

    # count paths
    count_paths(cave, [], "start", False)
    print("Total paths:", paths)


main("input.txt")
