package DSA;

import java.util.*;

public class Graphs {
    public static void main(String[] args) {
//        Can create a graph if required. Didnt create for now, as it is tedious and lengthy process
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        int n = 6; //number of nodes
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
//        graph.get(0).add(1);
//        graph.get(1).add(0);
//        graph.get(1).add(2);
//        graph.get(1).add(6);
//        graph.get(2).add(1);
//        graph.get(2).add(4);
//        graph.get(3).add(5);
//        graph.get(4).add(2);
//        graph.get(4).add(6);
//        graph.get(5).add(3);
//        graph.get(6).add(1);
//        graph.get(6).add(4);

//      Graph 4
//        graph.get(0).add(1);
//        graph.get(0).add(3);
//        graph.get(1).add(0);
//        graph.get(1).add(3);
//        graph.get(1).add(4);
//        graph.get(2).add(4);
//        graph.get(2).add(5);
//        graph.get(3).add(0);
//        graph.get(3).add(1);
//        graph.get(3).add(4);
//        graph.get(4).add(1);
//        graph.get(4).add(2);
//        graph.get(4).add(3);
//        graph.get(5).add(2);


//        Graph 5
//        graph.get(0).add(2);
//        graph.get(0).add(3);
//        graph.get(1).add(3);
//        graph.get(2).add(0);
//        graph.get(2).add(3);
//        graph.get(3).add(0);
//        graph.get(3).add(1);

//        als0 change value of n when changing graph!!
//        System.out.println(bfs(n, graph));
//        System.out.println(dfsOfGraph(n, graph));
//        System.out.println(detectCycleBFS(n, graph));

//        System.out.println(isBipartiteDFS(graph, n));

//        Graph 6 - Directed Graph
//        graph.get(0).add(2);
//        graph.get(3).add(2);

//        Graph 7 - Directed Graph
//        graph.get(2).add(3);
//        graph.get(3).add(1);
//        graph.get(4).add(0);
//        graph.get(4).add(1);
//        graph.get(5).add(0);
//        graph.get(5).add(2);

        System.out.println(Arrays.toString(topoDFS(graph, n)));
        System.out.println(Arrays.toString(topoBFS(graph, n)));
    }


    public static boolean isCyclicBFS(ArrayList<ArrayList<Integer>> graph, int n) {
//      Using Kahn's algo
//      SAME CODE as topoBFS. almost

//      Data structures Required
        int[] indegree = new int[n];
        int[] ans = new int[n];
        Queue<Integer> q = new ArrayDeque<>();

//      Calculating INDEGREE
        for (int node = 0; node < n; node++) {
            for (int adjNode : graph.get(node)) {
                indegree[adjNode]++;
            }
        }

//      whosever in-deg is 0, put them in Queue
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0)
                q.add(i);
        }

//      Starting the BFS now.
        int i = 0;
//      This count variable keeps count of how many nodes were popped out of queue
        int count = 0;
        while (!q.isEmpty()) {
//          pop the front of Queue. and store it.
            int temp = q.remove();
            ans[i] = temp;
            i++;
//          increment count with each pop. To count nodes
            count++;

//          Traverse all its Adj nodes
            for (int adjNode : graph.get(temp)) {
                if (indegree[adjNode] != 0) { // THIS is REDUNDANT. Coz, this will always be >= 0. this is simply the count of nodes pointing to this node. THINK!
                    indegree[adjNode]--;
                    if (indegree[adjNode] == 0)
                        q.add(adjNode);
                }
            }
        }

//      If count(or no. of popped nodes = n) then topo sort sequence generated, else not
        if (count == n) {
            return false;
        }
        return true;

    }

//  KAHN'S Algorithm
    public static int[] topoBFS(ArrayList<ArrayList<Integer>> graph, int n) {
//      Data structures Required
        int[] indegree = new int[n];
        int[] ans = new int[n];
        Queue<Integer> q = new ArrayDeque<>();

//      Calculating INDEGREE
        for (int node = 0; node < n; node++) {
            for (int adjNode : graph.get(node)) {
                indegree[adjNode]++;
            }
        }

//      whosever in-deg is 0, put them in Queue
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0)
                q.add(i);
        }

//      Starting the BFS now.
        int i = 0;
        while (!q.isEmpty()) {
//          pop the front of Queue. and store it.
            int temp = q.remove();
            ans[i] = temp;
            i++;

//          Traverse all its Adj nodes
            for (int adjNode : graph.get(temp)) {
                if (indegree[adjNode] != 0) { // THIS is REDUNDANT. Coz, this will always be >= 0. this is simply the count of nodes pointing to this node. THINK!
                    indegree[adjNode]--;
                    if (indegree[adjNode] == 0)
                        q.add(adjNode);
                }
            }
        }

        return ans;
    }

    public static int[] topoDFS(ArrayList<ArrayList<Integer>> graph, int n) {
        boolean[] vis = new boolean[n];

//        This stores The final sequence after TOPO sort.
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[n];

        for (int node = 0; node < n; node++) {
            if (!vis[node]) {
                topologicalSortDFS(graph, vis, node, stack);
            }
        }

//        STACK --> ARR
//      IMP, coz later size will change after each pop
//      OR USE WHILE, stack not empty.
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            ans[i] = stack.pop();
        }
        return ans;
    }

    public static void topologicalSortDFS(ArrayList<ArrayList<Integer>> graph, boolean[] vis, int node, Stack<Integer> stack){
        if (!vis[node]) {
            vis[node] = true;
            for (int adjNode : graph.get(node)) {
                topologicalSortDFS(graph, vis, adjNode, stack);
            }
//          Uptil now, completely same as DFS code
//          Below adding node to stack.
            stack.push(node);
        }
    }

    public static boolean isCyclicDirectedGraph(ArrayList<ArrayList<Integer>> graph, int n) {
        boolean visited[] = new boolean[n];
        boolean dfsVisited[] = new boolean[n];

        for (int node = 0; node < n; node++) {
            if (!visited[node])
                if (checkCycleDFS(graph, node, visited, dfsVisited))
                    return true;
        }
        return false;
    }

    public static boolean checkCycleDFS(ArrayList<ArrayList<Integer>> graph, int node, boolean[] visited, boolean[] dfsVisited) {
//      No Base Condtion here !! IMP HERE
//      Whenever any node is visited. mark both arrays as visited!
        visited[node] = true;
        dfsVisited[node] = true;

//       Now Check each node's Adj nodes, to have both vis = dfsVis = true
        for (int adjNode : graph.get(node)) {
            if (!visited[adjNode]) {
                if (checkCycleDFS(graph, adjNode, visited, dfsVisited))
                    return true;
            }
//          This condition means, vis[adj] = true, and dfsvis[adj] is also TRUE. HENCE CYCLE FOUND
            else if (dfsVisited[adjNode] == true)
                return true;
        }

//      SEE WHAT IT DOES??
        dfsVisited[node] = false;
        return false;
    }


    public static boolean isBipartiteDFS(ArrayList<ArrayList<Integer>> graph, int n){
//      Here thing to NOTE is, we use color arr instead of Visited. Color arr keeps track of both color and visited
//      0 : Not visited,
//     -1 : Color 1,
//      1 : Color 2
        int[] color = new int[n];

        for (int node = 0; node < n; node++) {
            if (color[node] == 0 ){//not visited
                if (colorGraphDFS(graph, color, node, -1) == false)
                    return false;
            }
        }
        return true;
    }

    public static boolean colorGraphDFS(ArrayList<ArrayList<Integer>> graph, int[] color, int node, int parent) {

        if (color[node] == 0) { //not visited
            System.out.println("dfs("+node+")");
            if (parent == -1)
                color[node] = 1;
            else
                color[node] = -color[parent];

            for (int adjNode : graph.get(node)) {
//                PUT THIS IN IF AND NOTE THIS THING!! FOR MORE SEE CYCLE DETECTION IN DFS
//                NOTE. if function returns something. Dont directly call it.
//                AND DONT simply return after calling. CHECK true or false. IN GRAPH
                if(!colorGraphDFS(graph, color, adjNode, node))
                    return false;
            }
        }

        else {//colored
            if (color[parent] == color[node])
                return false;
        }

        return true;
    }

    public static boolean isBipartiteGraphBFS(ArrayList<ArrayList<Integer>> graph, int n) {
//      Here thing to NOTE is, we use color arr instead of Visited. Color arr keeps track of both color and visited
//      0 : Not visited,
//     -1 : Color 1,
//      1 : Color 2
        int[] color = new int[n];
        Queue<Integer> q = new ArrayDeque<>();

        for (int node = 0; node < n; node++) {
            if (color[node] == 0) //not visited
            {
                q.add(node);
                color[node] = 1;

                while (!q.isEmpty()) {
                    int parent = q.remove();
//                    int previousNode = parent;
                    for (int adjNode : graph.get(parent)) {
                        if (color[adjNode] == 0) { //not visited
                            q.add(adjNode);
                            color[adjNode] = -(color[parent]);
                        }
//                      This checks if the current node has it's adjNode(parent) with same color
                        if (color[parent] == color[adjNode])
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean detectCycleDfs(int n, ArrayList<ArrayList<Integer>> graph) {
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!vis[i])
//              V IMP NOTE: we return and terminate function only when cycle found.
//              We should not return when its false. Coz it will terminate the funciton
//              and wont check any further. Hence RETURN ONLY WHEN TRUE!!!
                if(dfsCycleDetection(graph, vis, i, -1))
                    return true;
        }
        return false;
    }

    private static boolean dfsCycleDetection(ArrayList<ArrayList<Integer>> graph, boolean[] vis, int node, int parent) {
//        mark node as visited
        vis[node] = true;

//        Check for cycle
/*          Cases:
                1. node n NOT VISITED
                    - call cycleCheck for this n and if cycle found return true.

                2. node n VISITED earlier
                    i. if n == parent, not cycle, hence return false
                    ii. if n != parent, cycle there. return true
 */
        for (int n : graph.get(node)) {
            if (!vis[n]) {
                if (dfsCycleDetection(graph, vis, n, node))
                    return true;
            }
//          it comes here, when n has already been visited earlier
            else if (n != parent)
                return true;
//          DONT return any thing when n==PARENT. Coz it will terminate the call
//          Just continue checking when this condition strikes.
        }
        return false;
    }

    public static boolean detectCycleBFS(int n, ArrayList<ArrayList<Integer>> graph) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n];

//      This for loop and if has been used, to counter for DISCONNECTED GRAPHS !!
        for (int node = 0; node < n; node++) {
            if (!visited[node]) {

//              BFS code starts from here. Also for CONNECTED GRAPHS, q.add(will always be 0). i.e. q.add(0). Coz, all other nodes will be visited in
//              one for iteration and in all other cases visited[node] will always be 1
                q.add(node); //The starting point of graph. By default taken as 0
                visited[node] = true;

                while (!q.isEmpty()) {
                    int temp = q.remove();

                    int count = 0;
                    for (int val : graph.get(temp)) {
                        if (!visited[val]) {
                            q.add(val);
                            visited[val] = true;
                        } else {
                            count++;
                        }

                        if (count > 1)
                            return true;
                    }
                }
            }
        }
        return false;
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
