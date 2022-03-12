paths = 0
ORD_LOWER_A = 97


def incr():
    global paths
    paths += 1


def visited(path, e):
    if ord(e[0]) >= ORD_LOWER_A:  # is small cave
        if e in path:  # already visited this small cave
            return True
    return False


def count_paths(dic, path, e):
    if e == "end":  # finish a path
        incr()
        return

    path.append(e)
    edges = dic.get(e)
    for edge in edges:
        if visited(path, edge):
            continue

        count_paths(dic, path, edge)
    path.pop()

    '''
    new_path = old_path.copy()
    new_path.append(e)
    edges = dic.get(e)
    for edge in edges:
        if ord(edge[0]) >= ORD_LOWER_A:  # is small cave
            if edge in new_path:  # already visited this small cave
                continue

        count_paths(dic, new_path, edge)
    '''


"""
Parse dictionary
"""


def add_to_dic(dic, key, e):
    edges = dic.get(key)
    if edges:  # has existing edges
        if e in edges:
            return
        else:
            edges.append(e)
    else:  # add new edge list
        new_list = [e]
        dic[key] = new_list

    if key != "start" and e != "end":
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
    count_paths(cave, [], "start")
    print("Total paths:", paths)


main("input.txt")
