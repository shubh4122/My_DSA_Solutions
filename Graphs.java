package DSA;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Graphs {
    public static void main(String[] args) {
//        Can create a graph if required. Didnt create for now, as it is tedious and lengthy process
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        int n = 7; //number of nodes
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
        }
//        Graph1
//        graph.get(0).add(1);
//        graph.get(0).add(2);
//        graph.get(1).add(0);
//        graph.get(2).add(0);
//        graph.get(2).add(3);
//        graph.get(2).add(4);
//        graph.get(3).add(2);
//        graph.get(4).add(2);

//      Graph 2
//        graph.get(0).add(1);
//        graph.get(0).add(2);
//        graph.get(0).add(5);
//        graph.get(1).add(0);
//        graph.get(1).add(3);
//        graph.get(2).add(0);
//        graph.get(2).add(4);
//        graph.get(3).add(1);
//        graph.get(3).add(5);
//        graph.get(4).add(2);
//        graph.get(4).add(5);
//        graph.get(5).add(0);
//        graph.get(5).add(3);
//        graph.get(5).add(4);

//      Graph3 - disconnected
        graph.get(0).add(1);
        graph.get(1).add(0);
        graph.get(1).add(2);
        graph.get(1).add(6);
        graph.get(2).add(1);
        graph.get(2).add(4);
        graph.get(3).add(5);
        graph.get(4).add(2);
        graph.get(4).add(6);
        graph.get(5).add(3);
        graph.get(6).add(1);
        graph.get(6).add(4);

//        als0 change value of n when changing graph!!
        System.out.println(bfs(n, graph));
        System.out.println(dfsOfGraph(n, graph));
    }

    public static ArrayList<Integer> dfsOfGraph(int n, ArrayList<ArrayList<Integer>> graph) {
        boolean[] vis = new boolean[n];
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!vis[i])
                dfs(graph, vis, i, res);
        }
        return res;
    }

    private static void dfs(ArrayList<ArrayList<Integer>> graph, boolean[] vis, int temp, ArrayList<Integer> res) {
        if (vis[temp])
            return;

        res.add(temp);
        vis[temp] = true;

        for (int val : graph.get(temp)) {
            dfs(graph, vis, val, res);
        }
    }

    public static ArrayList<Integer> bfs(int n, ArrayList<ArrayList<Integer>> graph) {
        ArrayList<Integer> ans = new ArrayList<>();
        Queue<Integer> q = new ArrayDeque<>();
        int[] visited = new int[n];

//      This for loop and if has been used, to counter for DISCONNECTED GRAPHS !!
        for (int node = 0; node < n; node++) {
            if (visited[node] == 0) {

//              BFS code starts from here. Also for CONNECTED GRAPHS, q.add(will always be 0). i.e. q.add(0). Coz, all other nodes will be visited in
//              one for iteration and in all other cases visited[node] will always be 1
                q.add(node); //The starting point of graph. By default taken as 0
                visited[node] = 1;

                while (!q.isEmpty()) {
                    int temp = q.remove();
                    ans.add(temp);

                    for (int i = 0; i < graph.get(temp).size(); i++) {
                        int val = graph.get(temp).get(i);
                        if (visited[val] == 0) {
                            q.add(val);
                            visited[val] = 1;
                        }
                    }
                }
            }
        }

        return ans;
    }
}
