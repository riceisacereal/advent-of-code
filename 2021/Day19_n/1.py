from calculations.scanner_calculation import read_scanners
from calculations.scanner_calculation import calculate_edges
from calculations.scanner_calculation import locate_scanners


def main(filename):
    f = open(filename)
    lines = f.readlines()

    scanners = read_scanners(lines)
    calculate_edges(scanners)
    locate_scanners(scanners)

    """add beacons"""
    all_beacons = []
    for scanner in scanners:
        for beacon in scanner.beacons:
            if beacon not in all_beacons:
                all_beacons.append(beacon)

    # all_beacons.sort(key=lambda x: x[0])
    print("Total number of beacons:", len(all_beacons))

    f.close()


main("input.txt")
