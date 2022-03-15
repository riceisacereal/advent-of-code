def swap(heap, a, b):
    temp = heap[a]
    heap[a] = heap[b]
    heap[b] = temp


def up_heap(heap, curr_index):
    if curr_index == 0:
        return

    parent_index = int((curr_index - 1) / 2)
    if heap[curr_index][2] < heap[parent_index][2]:
        swap(heap, curr_index, parent_index)
        return up_heap(heap, parent_index)


def down_helper(heap, child_index, curr_index, length):
    if child_index < length and heap[child_index][2] < heap[curr_index][2]:
        swap(heap, child_index, curr_index)
        down_heap(heap, child_index)


def down_heap(heap, curr_index):
    left_index = (curr_index * 2) + 1
    right_index = (curr_index * 2) + 2
    length = len(heap)

    down_helper(heap, left_index, curr_index, length)
    down_helper(heap, right_index, curr_index, length)


def pop_head(heap):
    e = heap[0]
    swap(heap, 0, len(heap) - 1)
    heap.pop()
    down_heap(heap, 0)
    return e[:2]


def add_to_heap(heap, e):
    heap.append(e)
    up_heap(heap, len(heap) - 1)
