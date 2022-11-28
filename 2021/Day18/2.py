from snailfish_number_calculator import reduce
from snailfish_number_calculator import calculate_magnitude


def main(file_name):
    f = open(file_name)
    lines = f.readlines()

    max_mag = 0
    for i in range(len(lines)):
        for j in range(len(lines)):
            if i == j:
                continue

            left = list(lines[i][:-1])
            right = list(lines[j][:-1])
            build_line = ['[']
            build_line.extend(left)
            build_line.append(',')
            build_line.extend(right)
            build_line.append(']')

            reduce(build_line)
            mag = calculate_magnitude(build_line)
            if mag > max_mag:
                # print("".join(left), "".join(right))
                max_mag = mag

    print(max_mag)

    f.close()


main("test.txt")
