def main(file_name):
    f = open(file_name)
    lines = f.readlines()

    total = 0
    for line in lines:
        output = line.split(" | ")[1][:-1].split(' ')
        for digit in output:
            length = len(digit)
            if length == 2 or length == 4 or length == 3 or length == 7:
                total += 1

    f.close()
    return total


print("Total times digits 1, 4, 7, 8:", main("input.txt"))
