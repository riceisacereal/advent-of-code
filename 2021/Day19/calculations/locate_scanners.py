from calculations.Scanner import Scanner
from calculations import vector_calculation as vector


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


def aligning_helper(beacons, beaconsx, i, scannerx):
    aligned = True
    for j in range(len(beacons) - 1):
        d = abs(beacons[j][i] - beacons[j + 1][i])
        dx = abs(beaconsx[j][i] - beaconsx[j + 1][i])
        if d != dx:
            aligned = False
            break

    if aligned:
        """might be opposite sign"""
        d = beacons[0][i] - beacons[1][i]
        dx = beaconsx[0][i] - beaconsx[1][i]
        if d == -dx:
            scannerx.invert_axis(i)

        """onto next axis"""
        i += 1

    return aligned, i


def align_points(beacons, beaconsx, scannerx):
    """sort scannerx by every axis asc+desc, and compare gaps to sorted x axis of scanner"""
    beacons.sort(key=lambda x: (x[0], x[1], x[2]))

    i = 0
    while i <= 2:
        beaconsx.sort(key=lambda x: (x[0], x[1], x[2]))

        """normal order"""
        aligned, i = aligning_helper(beacons, beaconsx, i, scannerx)
        if aligned:
            continue

        """inverse"""
        scannerx.invert_axis(i)
        beaconsx.sort(key=lambda x: (x[0], x[1], x[2]))
        aligned, i = aligning_helper(beacons, beaconsx, i, scannerx)
        if aligned:
            continue

        if not aligned:
            """not aligned to this axis, swap axis"""
            scannerx.swap_axis(i, i + 1)
            if i == 0:
                """x<->y, x<->z => x,y,z -> y,z,x -> z,x,y -> x,y,z"""
                scannerx.swap_axis(i + 1, i + 2)

    """take first point as a, perform a - ax, find displacement from scanner0"""
    displacement = vector.addition(beacons[0], vector.inverse(beaconsx[0]))
    scannerx.change_position(displacement)