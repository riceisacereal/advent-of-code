import math


def intersects(lx, ly, key_x, key_y):
    pass


def get_distinct_velocities(x_velocities, y_velocities):
    total = 0
    for (key_y, value_y) in y_velocities:
        for (key_x, value_x) in x_velocities:
            if intersects():
                total += 1


def add_y_velocities(y_range, y_velocities):
    for start_v in range(-1, (y_range[0] // 2) - 1, -1):
        v = start_v
        curr_y_distance = 0
        while True:
            curr_y_distance += v

            if curr_y_distance < y_range[0]:
                """probe over lowest y"""
                break
            elif curr_y_distance <= y_range[1]:
                """probe in range"""
                steps = start_v - v + 1
                """add to negative"""
                if y_velocities.get(start_v):
                    y_velocities[start_v].append(steps)
                else:
                    y_velocities[start_v] = [steps]
                """add to positive"""
                pos_v = (-start_v) - 1
                if y_velocities.get(pos_v):
                    y_velocities[pos_v].append(2 * pos_v + steps)
                else:
                    y_velocities[pos_v] = [2 * pos_v + steps]

            v -= 1


def add_x_velocities(x_range, x_velocities):
    for start_v in range(1, (x_range[1] // 2) + 2):
        curr_x_distance = 0
        """count down"""
        for v in range(start_v, 0, -1):
            curr_x_distance += v

            if curr_x_distance > x_range[1]:
                """probe has gone over range"""
                break
            elif curr_x_distance >= x_range[0]:
                """probe is in range"""
                if x_velocities.get(start_v):
                    """add current step number"""
                    x_velocities[start_v].append(start_v - v + 1)
                else:
                    """create entry with current step number"""
                    x_velocities[start_v] = [start_v - v + 1]


def add_direct_hits(x_range, y_range, x_velocities, y_velocities):
    for velocity in range(x_range[0], x_range[1] + 1):
        x_velocities[velocity] = [1]
    for velocity in range(y_range[0], y_range[1] + 1):
        if velocity < 0:
            pos_v = (-velocity) - 1
            y_velocities[pos_v] = [2 * pos_v + 2]
        y_velocities[velocity] = [1]


def main(file_name):
    f = open(file_name)
    line = f.readline()
    f.close()
    x_range, y_range = (list(map(int, square[2:].split(".."))) for square in line[13:].strip().split(", "))

    x_velocities, y_velocities = {}, {}
    """stops in x after step ? = initial velocity"""
    """Add direct hits"""
    add_direct_hits(x_range, y_range, x_velocities, y_velocities)
    add_x_velocities(x_range, x_velocities)
    add_y_velocities(y_range, y_velocities)



    print(x_velocities)
    print(y_velocities)


main("example.txt")
