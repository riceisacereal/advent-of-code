def add_to_dic(dic, key, value):
    if key not in dic:
        dic[key] = value
    else:
        dic[key] += value


def insert_e(pairs, e_counter, key, e, value):
    """
    Insert element between pair

    :param pairs: Dictionary containing existing pairs
    :param e_counter: Dictionary recording the element counts
    :param key: Original polymer pair
    :param e: Element to be added
    :param value: The amount of times "key" appears in "pairs"
    """

    """Add the produced new pairs to the dictionary"""
    left = key[0] + e
    right = e + key[1]
    add_to_dic(pairs, left, value)
    add_to_dic(pairs, right, value)
    """Add the amount of new elements that are going to be produced"""
    add_to_dic(e_counter, e, value)


def polymer_step(rules, pairs, e_counter):
    new_pairs = {}
    for key, value in pairs.items():
        inserted_e = rules.get(key)
        if not inserted_e:
            continue

        insert_e(new_pairs, e_counter, key, inserted_e, value)

    return new_pairs


def process_template(template, e_counter):
    """
    Split the polymer in pairs and count symbols
    """
    dic = {}
    for i in range(len(template) - 1):
        """Count current symbol"""
        add_to_dic(e_counter, template[i], 1)
        """Construct and add pair"""
        pair = template[i] + template[i + 1]
        add_to_dic(dic, pair, 1)

    """Count last symbol"""
    add_to_dic(e_counter, template[len(template) - 1], 1)

    return dic


def find_difference(e_counter):
    return max(e_counter.values()) - min(e_counter.values())


def main(file_name, STEPS):
    f = open(file_name)

    template = f.readline()[:-1]
    rules = {}
    e_counter = {}

    """Skip a line"""
    f.readline()
    line = f.readline()
    while line:
        """Record rules"""
        pair = line[:-1].split(" -> ")
        rules[pair[0]] = pair[1]
        line = f.readline()

    """Encode polymer into pairs"""
    pairs = process_template(template, e_counter)

    """Do the steps"""
    for i in range(STEPS):
        pairs = polymer_step(rules, pairs, e_counter)

    print("Difference:", find_difference(e_counter))
    f.close()


main("input.txt", 40)
