#Uses python3

import sys
from queue import Queue

def bfs(adj, s, m):
    dist = [0]*len(adj)
    prev = [None]*len(adj)
    for i in range(len(adj)):
        dist[i] = m+1
    dist[s] = 0
    q = Queue()
    q.put(s)
    while not q.empty():
        u = q.get()
        for v in adj[u]:
            if dist[v] == m+1:
                q.put(v)
                dist[v] = dist[u] + 1
                prev[v] = u
    return prev


def distance(adj, s, t, m):
    #write your code here
    prev = bfs(adj, s, m)
    res = 0
    while t != s:
        if prev[t] != None:
            res += 1
            t = prev[t]
        else:
            return -1
    return res

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
    s, t = data[2 * m] - 1, data[2 * m + 1] - 1
    print(distance(adj, s, t, m))
