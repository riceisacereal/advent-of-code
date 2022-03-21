def main(file_name):
    f = open(file_name)
    line = f.readline()
    f.close()
    x_range, y_range = (list(map(int, square[2:].split(".."))) for square in line[13:-1].split(", "))

    n = (y_range[0] * -1) - 1
    highest = (n * (n + 1)) // 2

    print(highest)


main("input.txt")
