from calculations.locate_scanners import read_scanners
from calculations.locate_scanners import calculate_edges
from calculations.locate_scanners import find_intersection
from calculations.locate_scanners import align_points
import calculations.vector_calculation as vector


def main(filename):
    f = open(filename)
    lines = f.readlines()

    scanners = read_scanners(lines)
    calculate_edges(scanners)

    all_beacons = []
    """add all beacons from scanner 0, everything is relative to scanner 0"""
    for beacon in scanners[0].beacons:
        all_beacons.append(beacon)

    i = 0
    added_scanners = [0]
    """scanner i is always recorded and aligned"""
    while len(added_scanners) < len(scanners):
        for i in added_scanners:
            for j in range(len(scanners)):
                if i == j or (j in added_scanners and i in added_scanners):
                    continue

                """find cluster of intersection"""
                cluster, clusterx = find_intersection(scanners[i], scanners[j])
                """check validity"""
                if cluster is None:
                    continue
                elif len(cluster) != len(clusterx):
                    raise(Exception, "Uh Oh")

                cluster = list(map(lambda x: scanners[i].beacons[x], cluster))
                clusterx = list(map(lambda x: scanners[j].beacons[x], clusterx))

                """align points and change position of scanner"""
                align_points(cluster, clusterx, scanners[j])
                """transform all beacon positions"""
                for k in range(len(scanners[j].beacons)):
                    scanners[j].beacons[k] = vector.addition(scanners[j].position, scanners[j].beacons[k])

                """add beacons from scanner j to all beacons"""
                for beacon in scanners[j].beacons:
                    if beacon not in all_beacons:
                        all_beacons.append(beacon)

                added_scanners.append(j)
                i = j
                break

    all_beacons.sort(key=lambda x: x[0])
    print(len(all_beacons))


main("input.txt")
