import heap
import graph


def update_estimate(row, col, curr_d, cavern, explored, p_finding, next_heap):
    if row < 0 or col < 0 or row >= len(cavern) or col >= len(cavern[0]) or explored[row][col]:
        """Negative index or path already explored"""
        return

    this_step = curr_d + cavern[row][col]
    if p_finding[row][col] < 0 or this_step < p_finding[row][col]:
        p_finding[row][col] = this_step
        heap.add_to_heap(next_heap, [row, col, this_step])

    return


def dijkstra(row, col, cavern, explored, p_finding, next_heap):
    while row != len(cavern) - 1 or col != len(cavern[0]) - 1:
        explored[row][col] = 1

        """Update estimates around"""
        curr_d = p_finding[row][col]
        for i in range(-1, 2, 2):
            update_estimate(row, col + i, curr_d, cavern, explored, p_finding, next_heap)
            update_estimate(row + i, col, curr_d, cavern, explored, p_finding, next_heap)

        """Find the next path to take"""
        row, col = heap.pop_head(next_heap)

    return p_finding[row][col]


def main(file_name):
    cavern = graph.parse_graph(file_name)
    explored = graph.get_blank_map(cavern, 0)

    p_finding = graph.get_blank_map(cavern, -1)
    p_finding[0][0] = 0

    risk = dijkstra(0, 0, cavern, explored, p_finding, [])

    print("Risk level to bottom right:", risk)


main("input.txt")
