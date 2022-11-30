from Scanner import Scanner
import vector_calculation as vector


def read_scanners(lines):
    """read scanners"""
    scanners = []
    scanner_index = -1
    for line in lines:
        if line == '\n':
            continue
        elif line[:3] == "---":
            scanner = Scanner()
            scanners.append(scanner)
            scanner_index += 1
            continue

        """add beacons to scanner"""
        beacon = [int(x) for x in line[:-1].split(',')]
        scanners[scanner_index].add_beacon(beacon)

    return scanners


def calculate_edges(scanners):
    """calculate all edges"""
    for scanner in scanners:
        scanner.calculate_edges()
        # print(scanner.edges)
        """sort edges for ease of comparison later"""
        scanner.edges.sort(key=lambda x: x.distance)


def get_cluster(scanner, scannerx, edge_list, edge_listx):
    """tally indexes appear rate"""
    tally = [0] * len(scanner.beacons)
    tallyx = [0] * len(scannerx.beacons)

    for e in edge_list:
        tally[e.s] += 1
        tally[e.e] += 1

    for e in edge_listx:
        tallyx[e.s] += 1
        tallyx[e.e] += 1

    """if appear rate < 11, then point is not enough to be in cluster"""
    """if appear rate >= 11, point is likely to be in cluster"""
    index = []
    indexx = []

    for i in range(len(tally)):
        if tally[i] >= 11:
            index.append(i)

    for i in range(len(tallyx)):
        if tallyx[i] >= 11:
            indexx.append(i)

    """return tuple of indices of the points in the cluster for the two scanners"""
    if len(index) < 12 or len(indexx) < 12:
        return None, None
    else:
        return index, indexx


def find_intersection(scanner, scannerx):
    """loop through length of edges
    note down indexes of points of the edges that have the same distance [a, b, ax, bx]"""
    e = scanner.edges
    ex = scannerx.edges
    edge_list = []
    edge_listx = []

    i = ix = 0
    while i < len(e) and ix < len(ex):
        d = e[i].distance
        dx = ex[ix].distance
        if d == dx:
            edge_list.append(e[i])
            edge_listx.append(ex[ix])

            if ix + 1 < len(ex) and dx != ex[ix + 1].distance:
                ix += 1
            elif i + 1 < len(e) and d != e[i].distance:
                i += 1
            else:
                """multiple equal values"""
                temp_ix = ix + 1
                while temp_ix < len(ex) and e[i] == ex[temp_ix]:
                    edge_list.append(e[i])
                    edge_listx.append(ex[ix])
                    temp_ix += 1
                i += 1

        elif d < dx:
            i += 1
        elif d > dx:
            ix += 1

    return get_cluster(scanner, scannerx, edge_list, edge_listx)


def aligning_helper(ordered, beacons, beaconsx, i, scannerx):
    aligned = True
    for j in range(len(beacons) - 1):
        d = abs(beacons[j][i] - beacons[j + 1][i])
        dx = abs(beaconsx[j][i] - beaconsx[j + 1][i])
        if d != dx:
            aligned = False
            break

    if aligned:
        """aligned = ordered, no need to reverse the order"""
        ordered = True
        """might be opposite sign"""
        d = beacons[0][i] - beacons[1][i]
        dx = beaconsx[0][i] - beaconsx[1][i]
        if d == -dx:
            # """invert beacons in cluster"""
            # for beacon in beaconsx:
            #     beacon[i] = -beacon[i]
            # """invert all beacons in scanner"""
            scannerx.invert_axis(i)

        i += 1

    elif ordered:
        """ordered but not aligned, wrong axis probably"""
        if i + 1 > 2:
            raise (Exception, "axis not aligned hmm")

        # for beacon in beaconsx:
        #     temp = beacon[i]
        #     beacon[i] = beacon[i + 1]
        #     beacon[i + 1] = temp
        scannerx.swap_axis(i, i + 1)

    return aligned, ordered, i


def align_points(beacons, beaconsx, scanner, scannerx):
    """sort scannerx by every axis asc+desc, and compare gaps to sorted x axis of scanner"""
    beacons.sort(key=lambda x: x[0])

    i = 0
    aligned = False
    ordered = False
    while i <= 2:
        if not ordered:
            """beacons are not in the same order as scanner 0"""
            beaconsx.sort(key=lambda x: x[i])

        aligned, ordered, i = aligning_helper(ordered, beacons, beaconsx, i, scannerx)
        if aligned or ordered:
            continue

        beaconsx.reverse()
        aligned, ordered, i = aligning_helper(ordered, beacons, beaconsx, i, scannerx)
        if not aligned:
            scannerx.swap_axis(i, i + 1)

    """take first point as a, perform a - ax, find displacement"""
    displacement = vector.addition(beacons[0], vector.inverse(beaconsx[0]))
    """get position of newly aligned scanner"""
    scannerx.change_position(vector.addition(scanner.position, displacement))


def add_beacons(all_beacons, scanner):
    """calculate all points, add to hashset"""


def main(filename):
    f = open(filename)
    lines = f.readlines()

    scanners = read_scanners(lines)
    calculate_edges(scanners)

    all_beacons = set()
    """add all beacons from scanner 0, everything is relative to scanner 0"""
    for beacon in scanners[0].beacons:
        all_beacons.add(str(beacon))

    i = 0
    added_scanners = [0]
    """scanner i is always recorded and aligned"""
    while len(added_scanners) < len(scanners):
        for i in added_scanners:
            for j in range(len(scanners)):
                if i == j or (j in added_scanners and i in added_scanners):
                    continue

                """find cluster of intersection"""
                beacons, beaconsx = find_intersection(scanners[i], scanners[j])
                """check validity"""
                if beacons is None:
                    continue
                elif len(beacons) != len(beaconsx):
                    raise(Exception, "Uh Oh")

                beacons = list(map(lambda x: scanners[i].beacons[x], beacons))
                beaconsx = list(map(lambda x: scanners[j].beacons[x], beaconsx))

                """align points"""
                align_points(beacons, beaconsx, scanners[i], scanners[j])
                """add beacons from scanner j to all beacons"""

                added_scanners.append(j)
                i = j
                break

    print(scanners)


main("test.txt")
