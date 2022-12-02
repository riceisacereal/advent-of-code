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

    """------- 1 -------"""
    all_beacons = []
    """add beacons from scanner j to all beacons"""
    for scanner in scanners:
        for beacon in scanner.beacons:
            if beacon not in all_beacons:
                all_beacons.append(beacon)

    all_beacons.sort(key=lambda x: x[0])
    print(len(all_beacons))

    """------- 2 -------"""
    largest_distance = 0
    for scanner1 in scanners:
        for scanner2 in scanners:
            distance = vector.manhattan_distance(scanner1.position, scanner2.position)
            if distance > largest_distance:
                largest_distance = distance

    print(largest_distance)


main("input.txt")
