from snailfish_number_calculator import reduce
from snailfish_number_calculator import calculate_magnitude


def main(file_name):
    f = open(file_name)
    lines = f.readlines()
    line = list(lines.pop(0)[:-1])
    reduce(line)
    print("".join(line))

    for new_line in lines:
        build_line = ['[']
        build_line.extend(line)
        build_line.append(',')
        build_line.extend(list(new_line[:-1]))
        build_line.append(']')
        line = build_line
        reduce(line)
        # print("".join(line))

    print("".join(line))
    print(calculate_magnitude(line))

    f.close()


main("test.txt")
