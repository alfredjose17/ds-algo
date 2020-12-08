#Uses python3

import sys


def negative_cycle(adj, cost, n, visited):
    #write your code here
    dist = [float('inf')] * n
    dist[0] = 0

    for i in range(n):
        for u in range(n):
            visited[u] = True
            for v in range(len(adj[u])):
                visited[adj[u][v]] = True
                if dist[adj[u][v]] > dist[u] + cost[u][v]:
                    dist[adj[u][v]] = dist[u] + cost[u][v]
                    if i == n - 1:
                        return 1
                        
    return 0


def explore(adj, cost, n):
    visited = [False] * n
    for i in range(n):
        if not visited[i]:
            if negative_cycle(adj, cost, n, visited):
                return 1
    
    return 0



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
    # Adding extra vertex that is connected to all other vertices with edge weight 1
    temp = [i for i in range(n)]
    adj.append(temp)
    tempc = [0] * n
    cost.append(tempc)
    print(explore(adj, cost, len(adj)))
