import calculations.vector_calculation as vector
from calculations.scanner_calculation import read_scanners
from calculations.scanner_calculation import calculate_edges
from calculations.scanner_calculation import locate_scanners


def main(filename):
    f = open(filename)
    lines = f.readlines()

    scanners = read_scanners(lines)
    calculate_edges(scanners)
    locate_scanners(scanners)

    largest_distance = 0
    for scanner1 in scanners:
        for scanner2 in scanners:
            distance = vector.manhattan_distance(scanner1.position, scanner2.position)
            if distance > largest_distance:
                largest_distance = distance

    print("Largest Manhattan distance:", largest_distance)

    f.close()


main("input.txt")
