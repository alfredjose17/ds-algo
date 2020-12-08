#Uses python3

import sys

sys.setrecursionlimit(200000)

def explore(adj, visited, x):
    visited[x] = True
    for i in adj[x]:
        if not visited[i]:
            explore(adj, visited, i)

def dfs(adj, visited, order, x):
    visited[x] = True
    for i in adj[x]:
        if not visited[i]:
            dfs(adj, visited, order, i)
    order.append(x)


def number_of_strongly_connected_components(adj, adjt):
    result = 0
    visited = [False]*len(adj)
    order = []
    #write your code here
    for i in range(len(adj)):
        if not visited[i]:
            dfs(adjt, visited, order, i)
    visited = [False]*len(adj)
    order.reverse()
    for i in order:
        if not visited[i]:
            explore(adj, visited, i)
            result += 1
    return result

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n, m = data[0:2]
    data = data[2:]
    edges = list(zip(data[0:(2 * m):2], data[1:(2 * m):2]))
    adj = [[] for _ in range(n)]
    adjt = [[] for _ in range(n)]
    for (a, b) in edges:
        adj[a-1].append(b-1)
    for (a, b) in edges:
        adjt[b-1].append(a-1)
    print(number_of_strongly_connected_components(adj, adjt))
