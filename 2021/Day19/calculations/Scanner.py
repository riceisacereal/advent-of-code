from calculations import vector_calculation as vector


class Scanner:
    def __init__(self):
        self.edges = []
        self.beacons = []
        self.position = [0, 0, 0]

    def add_beacon(self, beacon):
        self.beacons.append(beacon)

    def invert_axis(self, axis):
        for beacon in self.beacons:
            beacon[axis] = -beacon[axis]

    def swap_axis(self, i, j):
        for beacon in self.beacons:
            temp = beacon[i]
            beacon[i] = beacon[j]
            beacon[j] = temp

    def change_position(self, new_pos):
        self.position = new_pos

    def calculate_edges(self):
        beacons = self.beacons
        for i in range(len(beacons) - 1):
            for j in range(i + 1, len(beacons)):
                distance = vector.distance(beacons[i], beacons[j])
                edge = Edge(i, j, distance)
                self.edges.append(edge)

    def __str__(self):
        pos = f"position: {self.position}\n"
        return pos

    def __repr__(self):
        pos = f"position: {self.position}\n"
        return pos


class Edge:
    def __init__(self, s, e, distance):
        self.s = s
        self.e = e
        self.distance = distance

    def __str__(self):
        return f"{self.s}-{self.e}: {self.distance}"

    def __repr__(self):
        return f"Edge {self.s}-{self.e}"
