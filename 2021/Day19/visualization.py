from calculations.scanner_calculation import read_scanners
from calculations.scanner_calculation import calculate_edges
from calculations.scanner_calculation import locate_scanners


SHRINK_FACTOR = 100


def draw_map(scanners, all_beacons):
    ocean = open("terrain.txt", "w", encoding='utf-8')

    max_x = max_y = -1000
    min_x = min_y = 1000

    for beacon in all_beacons:
        max_x = max(beacon[0], max_x)
        max_y = max(beacon[1], max_y)
        min_x = min(beacon[0], min_x)
        min_y = min(beacon[1], min_y)

    height = (max_y - min_y) // SHRINK_FACTOR + 1
    width = (max_x - min_x) // SHRINK_FACTOR + 1
    matrix = [[]] * height
    for i in range(height):
        matrix[i] = ["丶"] * width

    for beacon in all_beacons:
        x = (beacon[0] - min_x) // SHRINK_FACTOR
        y = (beacon[1] - min_y) // SHRINK_FACTOR
        matrix[y][x] = "十"

    for scanner in scanners:
        x = (scanner.position[0] - min_x) // SHRINK_FACTOR
        y = (scanner.position[1] - min_y) // SHRINK_FACTOR
        matrix[y][x] = "囫"

    for line in matrix:
        ocean.write("".join(line) + '\n')

    ocean.close()


def main(filename):
    f = open(filename)
    lines = f.readlines()

    scanners = read_scanners(lines)
    calculate_edges(scanners)
    locate_scanners(scanners)

    all_beacons = []
    """add beacons"""
    for scanner in scanners:
        for beacon in scanner.beacons:
            if beacon not in all_beacons:
                all_beacons.append(beacon)

    draw_map(scanners, all_beacons)

    f.close()


main("input.txt")
