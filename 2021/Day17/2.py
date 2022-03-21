def intersects(key_x, value_x, value_y, stops_in_range):
    for step_y in value_y:
        if step_y >= key_x and key_x in stops_in_range:
            """probe has stopped in x distance at this point"""
            return True
        elif step_y in value_x:
            """has matching step"""
            return True

    return False


def get_distinct_velocities(x_velocities, y_velocities, stops_in_range):
    total = 0
    for (key_y, value_y) in y_velocities.items():
        for (key_x, value_x) in x_velocities.items():
            if intersects(key_x, value_x, value_y, stops_in_range):
                # print(key_x, ",", key_y, sep='')
                total += 1

    return total


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
                    y_velocities[start_v].add(steps)
                else:
                    y_velocities[start_v] = {steps}
                """add to positive"""
                pos_v = (-start_v) - 1
                if y_velocities.get(pos_v):
                    y_velocities[pos_v].add(2 * pos_v + 1 + steps)
                else:
                    y_velocities[pos_v] = {2 * pos_v + 1 + steps}

            v -= 1


def add_x_velocities(x_range, x_velocities, stops_in_range):
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
                if v == 1:
                    """probe stops in range"""
                    stops_in_range.add(start_v)

                if x_velocities.get(start_v):
                    """add current step number"""
                    x_velocities[start_v].add(start_v - v + 1)
                else:
                    """create entry with current step number"""
                    x_velocities[start_v] = {start_v - v + 1}


def add_direct_hits(x_range, y_range, x_velocities, y_velocities):
    for velocity in range(x_range[0], x_range[1] + 1):
        x_velocities[velocity] = {1}
    for velocity in range(y_range[0], y_range[1] + 1):
        if velocity < 0:
            pos_v = (-velocity) - 1
            y_velocities[pos_v] = {2 * pos_v + 2}
        y_velocities[velocity] = {1}


def main(file_name):
    f = open(file_name)
    line = f.readline()
    f.close()
    x_range, y_range = (list(map(int, square[2:].split(".."))) for square in line[13:].strip().split(", "))

    x_velocities, y_velocities = {}, {}
    """stops in x after step ? = initial velocity"""
    stops_in_range = set()
    """Add direct hits"""
    add_direct_hits(x_range, y_range, x_velocities, y_velocities)
    """Checking which x, y velocities fall into the range"""
    add_x_velocities(x_range, x_velocities, stops_in_range)
    add_y_velocities(y_range, y_velocities)

    # print(x_velocities)
    # print(y_velocities)

    print("Total distinct velocities:", get_distinct_velocities(x_velocities, y_velocities, stops_in_range))


main("input.txt")
