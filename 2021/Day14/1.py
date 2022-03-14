def add_to_dic(dic, key, value):
    if key not in dic:
        dic[key] = value
    else:
        dic[key] += value


def insert_symbol(pairs, key, sym, value):
    left = key[0] + sym
    right = sym + key[1]
    add_to_dic(pairs, left, value)
    add_to_dic(pairs, right, value)


def polymer_step(rules, pairs, symbol_counter):
    new_pairs = {}
    for key, value in pairs.items():
        inserted_sym = rules.get(key)
        if not inserted_sym:
            continue

        insert_symbol(new_pairs, key, inserted_sym, value)
        add_to_dic(symbol_counter, inserted_sym, value)

    return new_pairs


def process_template(template, symbol_counter):
    """
    Split the polymer in pairs and count symbols
    """
    dic = {}
    for i in range(len(template) - 1):
        """Count current symbol"""
        add_to_dic(symbol_counter, template[i], 1)
        """Construct and add pair"""
        pair = template[i] + template[i + 1]
        add_to_dic(dic, pair, 1)

    """Count last symbol"""
    add_to_dic(symbol_counter, template[len(template) - 1], 1)

    return dic


def find_difference(symbol_counter):
    return max(symbol_counter.values()) - min(symbol_counter.values())


def main(file_name, STEPS):
    f = open(file_name)
    lines = f.readlines()

    template = lines[0][:-1]
    rules = {}
    symbol_counter = {}

    for i in range(2, len(lines), 1):
        """Record rules"""
        pair = lines[i][:-1].split(" -> ")
        rules[pair[0]] = pair[1]

    """Encode polymer into pairs"""
    pairs = process_template(template, symbol_counter)

    for i in range(STEPS):
        pairs = polymer_step(rules, pairs, symbol_counter)

    print("Difference:", find_difference(symbol_counter))
    f.close()


main("input.txt", 40)
