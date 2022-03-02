def switch_indexes(array, a, b):
    temp = array[a]
    array[a] = array[b]
    array[b] = temp


def update_right(array, right, pivot, left):
    while array[right] >= pivot and right > left:
        right -= 1
    return right


def quick_sort(array, start, end):
    if (end - start) <= 0:
        return

    pivot = array[end]
    left = start
    right = update_right(array, end - 1, pivot, left)
    while left < right:
        if array[left] >= pivot:
            switch_indexes(array, left, right)
            # move right pointer
            right = update_right(array, right, pivot, left)
        left += 1

    switch_indexes(array, right + 1, end)
    quick_sort(array, start, right)
    quick_sort(array, right + 1, end)


def quick_select(array, start, end):
    pivot = array[end]
    left = start
    right = update_right(array, end - 1, pivot, left)
    while left < right:
        if array[left] >= pivot:
            switch_indexes(array, left, right)
            # move right pointer
            right = update_right(array, right, pivot, left)
        left += 1

    pivotIndex = right + 1
    switch_indexes(array, pivotIndex, end)

    # decide on whether to take right or left part
    half = int(len(array) / 2) - 1
    if pivotIndex == half:
        return array[half]
    if pivotIndex > half:
        return quick_select(array, start, pivotIndex - 1)
    else:
        return quick_select(array, pivotIndex + 1, end)


def main(inputLines):
    # read all numbers
    array = inputLines[0].split(',')
    for i in range(len(array)):
        string = array[i]
        array[i] = int(string)

    # quick select median 500th
    # start and end are inclusive
    midPoint = quick_select(array, 0, len(array) - 1)
    # quick_sort(array, 0, len(array) - 1)
    # midPoint = array[500]
    print("Mid point:", midPoint)

    total = 0
    for i in range(len(array)):
        total += abs(midPoint - array[i])

    return total


f = open("input.txt")
lines = f.readlines()

print("Total fuel:", main(lines))

f.close()
