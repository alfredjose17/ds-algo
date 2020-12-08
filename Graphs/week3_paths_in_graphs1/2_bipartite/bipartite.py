#Uses python3

import sys
from queue import Queue

def bipartite(adj, dist, i):
    #write your code here
    dist[i] = 1
    q = Queue()
    q.put(i)

    while not q.empty():
        u = q.get()
        for v in adj[u]:
            if dist[v] == -1:
                q.put(v)
                dist[v] = 1 - dist[u]
            elif dist[u] == dist[v]:
                return False

    return True

def explore(adj):
    n = len(adj)
    dist = [-1 for _ in range(n)]

    for i in range(n):
        if dist[i] == -1:
            bfs = bipartite(adj, dist, i)
            if not bfs:
                return 0
    
    return 1

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n, m = data[0:2]
    data = data[2:]
    edges = list(zip(data[0:(2 * m):2], data[1:(2 * m):2]))
    adj = [[] for _ in range(n)]
    for (a, b) in edges:
        adj[a - 1].append(b - 1)
        adj[b - 1].append(a - 1)
    print(explore(adj))
