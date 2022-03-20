import util
BITS = ""
index_ptr = [0]
operations = {0: "Sum", 1: "Product", 2: "Min", 3: "Max", 5: "Greater than", 6: "Less than", 7: "Equal to"}


def increase_index(n):
    global index_ptr
    index_ptr[0] += n


def parse_to_bin(string):
    binary = ""
    for i in range(len(string)):
        binary += util.hex_to_bin[string[i]]

    return binary


"""Calculation"""


def get_binary_to_dec(count):
    dec = 0
    for i in range(count):
        if BITS[index_ptr[0] + i] == "1":
            dec += 2 ** (count - i - 1)

    increase_index(count)
    return dec


def get_literal_value():
    num = 0
    while True:
        first_bit = BITS[index_ptr[0]]
        increase_index(1)
        num = (num * 16) + get_binary_to_dec(4)
        if first_bit == '0':
            break

    return num


def operator():
    increase_index(1)
    list_of_nums = []

    if BITS[index_ptr[0] - 1] == '0':
        """next 15 bits is total length of sub-packets"""
        length = get_binary_to_dec(15)
        initial_index = index_ptr[0]
        while index_ptr[0] - initial_index < length:
            list_of_nums.append(process_packet())

    else:
        """11 bits are number of sub-packets contained"""
        number = get_binary_to_dec(11)
        for i in range(number):
            list_of_nums.append(process_packet())

    return list_of_nums


def operator_calculation(list_of_nums, type_id):
    print(operations[type_id], list_of_nums)
    if type_id == 0:
        return sum(list_of_nums)
    elif type_id == 1:
        product = 1
        for i in list_of_nums:
            product *= i
        return product
    elif type_id == 2:
        return min(list_of_nums)
    elif type_id == 3:
        return max(list_of_nums)
    elif type_id == 5:
        return 1 if list_of_nums[0] > list_of_nums[1] else 0
    elif type_id == 6:
        return 1 if list_of_nums[0] < list_of_nums[1] else 0
    elif type_id == 7:
        return 1 if list_of_nums[0] == list_of_nums[1] else 0


def process_packet():
    """skip packet version"""
    increase_index(3)
    type_id = get_binary_to_dec(3)

    if type_id == 4:
        return get_literal_value()
    else:
        return operator_calculation(operator(), type_id)


def main(file_name):
    f = open(file_name)
    lines = f.readlines()
    f.close()

    global BITS
    BITS = parse_to_bin(lines[0].strip())
    print(BITS)
    print(len(BITS))

    final = process_packet()
    print("Final value:", final)


main("input.txt")
