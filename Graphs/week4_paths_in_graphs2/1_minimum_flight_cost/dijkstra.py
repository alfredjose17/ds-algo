#Uses python3

import sys
import queue


def distance(adj, cost, s, t):
    #write your code here
    n = len(adj)
    dist = []

    for _ in range(n):
        dist.append(float('inf'))

    dist[s] = 0
    heap = queue.PriorityQueue()
    heap.put(s, 0)

    while not heap.empty():
        u = heap.get()
        for v in range(len(adj[u])):
            if dist[adj[u][v]] > dist[u] + cost[u][v]:
                dist[adj[u][v]] = dist[u] + cost[u][v]
                heap.put(adj[u][v], dist[adj[u][v]])

    return dist[t]


if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n, m = data[0:2]
    data = data[2:]
    edges = list(zip(zip(data[0:(3 * m):3], data[1:(3 * m):3]), data[2:(3 * m):3]))
    data = data[3 * m:]
    adj = [[] for _ in range(n)]
    cost = [[] for _ in range(n)]
    for ((a, b), w) in edges:
        adj[a - 1].append(b - 1)
        cost[a - 1].append(w)
    s, t = data[0] - 1, data[1] - 1
    res = distance(adj, cost, s, t)
    val = float('inf')
    if res == val:
        print(-1)
    else:
        print(res)
