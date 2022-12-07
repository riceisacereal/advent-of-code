import math


def manhattan_distance(v, u):
    D = len(v)
    result = 0
    for i in range(D):
        result += abs(v[i] - u[i])

    return result


def addition(v, u):
    D = len(v)
    result = [0] * D
    for i in range(D):
        result[i] = v[i] + u[i]

    return result


def inverse(v):
    return [-x for x in v]


def distance(v, u):
    result = 0
    for i in range(len(v)):
        result += (v[i] - u[i]) ** 2
    result = math.sqrt(result)

    return result
