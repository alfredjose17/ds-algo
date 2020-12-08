#Uses python3
import sys
import math

def distance(a, b):
    d = ((a[0] - b[0]) ** 2) + ((a[1] - b[1]) ** 2)
    return math.sqrt(d)


def find(u, sets):
    if sets[u] != u:
        sets[u] = find(sets[u], sets)
    return sets[u]


def union(u ,v, sets):
    f1 = find(u, sets)
    f2 = find(v, sets)
    if f1 != f2:
        sets[f1] = sets[f2]
        return True
    return False


def minimum_distance(points, n):
    result = 0
    #write your code here
    edges = sets = []
    for i in range(n):
        for j in range(i + 1, n):
            d = distance(points[i], points[j])
            edges.append((d, i, j))

    edges.sort()
    #print(edges)
    sets = [i for i in range(n)]

    for d, u, v in edges:
        if union(u, v, sets):
            #print(sets)
            result += d

    return result


if __name__ == '__main__':
    n = int(input())
    points = []
    for i in range(n):
        points.append(list(map(int, input().split())))
    #print(points)
    print("{0:.9f}".format(minimum_distance(points, n)))
