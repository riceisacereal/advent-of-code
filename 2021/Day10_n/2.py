def swap(heap, a, b):
    temp = heap[a]
    heap[a] = heap[b]
    heap[b] = temp


def pop_root(heap, minheap):
    if len(heap) == 0:
        return
    elif len(heap) == 1:
        root = heap.pop()
        return root

    last = len(heap) - 1
    swap(heap, 0, last)
    root = heap.pop()
    down_heap(heap, 0, minheap)
    return root


def up_heap(heap, currIndex, minheap):
    if currIndex < 1:
        return

    parentIndex = int((currIndex - 1) / 2)
    if (heap[parentIndex] > heap[currIndex]) == minheap:
        swap(heap, currIndex, parentIndex)
        up_heap(heap, parentIndex, minheap)
    else:
        return


def down_heap(heap, currIndex, minheap):
    leftIndex = currIndex * 2 + 1
    rightIndex = currIndex * 2 + 2
    length = len(heap)
    # check indexes are in range
    if leftIndex < length and (heap[currIndex] > heap[leftIndex]) == minheap:
        swap(heap, currIndex, leftIndex)
        down_heap(heap, leftIndex, minheap)
    if rightIndex < length and (heap[currIndex] > heap[rightIndex]) == minheap:
        swap(heap, currIndex, rightIndex)
        down_heap(heap, rightIndex, minheap)
    return


def add_new_score(bigger, smaller, newScore):
    # bigger[0] always contains the median
    # check where new score has to be added
    bigHeap = True  # big heap is min heap
    smallHeap = False  # small heap is max heap

    if len(bigger) == 0:
        toBigger = True
    else:
        toBigger = bigger[0] < newScore

    if toBigger:
        if len(bigger) > len(smaller):
            # pop from bigger to smaller, then add to bigger, both upheap
            smaller.append(pop_root(bigger, bigHeap))
            up_heap(smaller, len(smaller) - 1, smallHeap)
        bigger.append(newScore)
        up_heap(bigger, len(bigger) - 1, bigHeap)
    else:
        if len(bigger) == len(smaller):
            # pop from smaller to bigger if there is anything to pop, then add to smaller, both up heap
            bigger.append(pop_root(smaller, smallHeap))
            up_heap(bigger, len(bigger) - 1, bigHeap)
        smaller.append(newScore)
        up_heap(smaller, len(smaller) - 1, smallHeap)


closeToOpen = {')': '(', ']': '[', '}': '{', '>': '<'}
completionScore = {'(': 1, '[': 2, '{': 3, '<': 4}


def calculate_score(line):
    total = 0
    while len(line):
        total = total * 5 + completionScore.get(line.pop())

    return total


def main(fileName):
    f = open(fileName)
    line = f.readline()[:-1]
    bigger = []
    smaller = []
    while line:
        track = []
        valid = True
        for i in range(len(line)):
            curr = line[i]
            correspond = closeToOpen.get(curr)
            if correspond:  # closing
                if (len(track) != 0) and track[-1] == correspond:
                    track.pop()
                else:
                    valid = False
                    break
            else:  # opening
                track.append(curr)

        if valid:
            score = calculate_score(track)
            add_new_score(bigger, smaller, score)

        line = f.readline()[:-1]

    return bigger[0]


median = main("input.txt")
print("Median:", median)
