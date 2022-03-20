import util
index_sum = [0, 0]


def increase_index(n):
    global index_sum
    index_sum[0] += n


def increase_sum(n):
    global index_sum
    index_sum[1] += n


def parse_to_bin(string):
    binary = ""
    for i in range(len(string)):
        binary += util.hex_to_bin[string[i]]

    return binary


def get_binary_to_dec(bits, count):
    dec = 0
    for i in range(count):
        if bits[index_sum[0] + i] == "1":
            dec += 2 ** (count - i - 1)

    increase_index(count)
    return dec


"""Calculation"""


def get_literal_value(bits):
    while bits[index_sum[0]] != '0':
        increase_index(5)
        # value = bits[start_index:(start_index := start_index + 5)]
    increase_index(5)


def operator(bits):
    increase_index(1)
    if bits[index_sum[0] - 1] == '0':
        """next 15 bits is total length of sub-packets"""
        length = get_binary_to_dec(bits, 15)
        initial_index = index_sum[0]
        while index_sum[0] - initial_index < length:
            process_packet(bits)

    else:
        """11 bits are number of sub-packets contained"""
        number = get_binary_to_dec(bits, 11)

        for i in range(number):
            process_packet(bits)


def process_packet(bits):
    packet_version = get_binary_to_dec(bits, 3)
    increase_sum(packet_version)
    type_id = get_binary_to_dec(bits, 3)

    if type_id == 4:
        get_literal_value(bits)
    else:
        operator(bits)


def main(file_name):
    f = open(file_name)
    lines = f.readlines()
    f.close()

    BITS = parse_to_bin(lines[0][:-1])
    print(BITS)
    process_packet(BITS)
    print(index_sum[1])


main("input.txt")
