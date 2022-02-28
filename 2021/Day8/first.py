def main(lines):
    total = 0
    for line in lines:
        output = line.split(" | ")[1][:-1].split(' ')
        for digit in output:
            length = len(digit)
            if length == 2 or length == 4 or length == 3 or length == 7:
                total += 1

    return total


f = open("input.txt")
l = f.readlines()

print("Total times digits 1, 4, 7, 8:", main(l))

f.close()