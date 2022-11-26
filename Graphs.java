package DSA;

import java.util.*;

public class Graphs {
    public static final int INFINITY = Integer.MAX_VALUE;

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

//        System.out.println(Arrays.toString(topoDFS(graph, n)));
//        System.out.println(Arrays.toString(topoBFS(graph, n)));

        int[][] edges = {{0,1},{0,3},{3,4},{4 ,5},{5, 6},{1,2},{2,6},{6,7},{7,8},{6,8}} ;
        System.out.println(edgesToList(edges, 9));
    }

//  This is for graphs, where we have weights. So it stores the destination node and the weights
static class Pair implements Comparator<Pair>{
        int node, weight;
        Pair(){}

        Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

    @Override
    public int compare(Pair p1, Pair p2) {
        //quick way to implement. For ascending order. See other way too.
        return p1.weight - p2.weight;
    }
}


    public static int[] bellman_ford(ArrayList<ArrayList<Integer>> edges, int n, int src) {
        int[] dist = new int[n];
        Arrays.fill(dist, INFINITY);
        dist[src] = 0;

/*
        |----------------------------------------------|
        |               Relax n-1 times                |
        |----------------------------------------------|
*/

        for (int i = 0; i < n - 1; i++) {
            relax(edges, dist, false);
        }

        //This is nth call to relax. If now it enters the below IF in Relax. where dist changes. It will tell me, it changed.
        boolean isNegCycle = relax(edges, dist, true);
        if (isNegCycle)
            return new int[]{-1};

        return dist;
    }

    private static boolean relax(ArrayList<ArrayList<Integer>> edges, int[] dist, boolean isNthCall) {
        //edge ->  (u, v, w).  u: parent. v : adjNode. w : weight
        for (ArrayList<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            int w = edge.get(2);

            int newDist = dist[u] + w;
            if (dist[u] != INFINITY && newDist < dist[v]) { // for nodes which havent been reached yet. we wont relax them.
                dist[v] = newDist;
                //this is solely done for the nth iteration, finding presence of negative cycle.
                if (isNthCall)
                    return true;// true coz, this is Nth call, and it entered here which means DIST[] is changed.
            }
        }
        return false;//no negative cycle
    }

    public static int[] dijkstra(ArrayList<ArrayList<ArrayList<Integer>>> graph, int n, int src) {
        //Create Priority Queue
        PriorityQueue<Pair> pq = new PriorityQueue<>(new Pair()); // new Pair() is comparator given to pq to use.
        int[] dist = new int[n];
        Arrays.fill(dist, INFINITY);

        //add src to PQ
        pq.add(new Pair(src, 0));
        dist[src] = 0;

/*
        |--------------------------------------------------------|
        |        ******** FINDING SHORTEST PATH ********         |
        |                                                        |
        |      Do below steps: UNTIL pq is empty                 |
        |       1. Pop the node(with least dist). Property of pq |
        |       2. Visit all adj nodes of Popped node            |
        |       3. Calc newDist, if its Lower:                   |
        |               a. Update it in dist[]                   |
        |               b. Push this node Pair in PQ             |
        |--------------------------------------------------------|
*/
        while (!pq.isEmpty()) {
            Pair topNode = pq.remove();
            int leastDistNode = topNode.node;
            int leastDist = topNode.weight;

            for (int i = 0; i < graph.get(leastDistNode).size(); i++) {
                int adjNode = graph.get(leastDistNode).get(i).get(0);
                int adjWeight = graph.get(leastDistNode).get(i).get(1);

                int newDist = dist[leastDistNode] + adjWeight;
                if (newDist < dist[adjNode]) {
                    dist[adjNode] = newDist;
                    //node pushed to PQ with new updated DIST.
                    pq.add(new Pair(adjNode, dist[adjNode]));
                }
            }
        }
        return dist;
    }

    public static ArrayList<ArrayList<Pair>> edgesToList_Directed_Weighted(int[][] edges, int n) {
        ArrayList<ArrayList<Pair>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Pair>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(new Pair(edges[i][1], edges[i][2]));
        }
//        System.out.println(graph);
        return graph;
    }

    public static void shortestDist_DAG_Weighted_using_TOPOSORT(ArrayList<ArrayList<Pair>> graph, int n, int[] dist, int src) {
//        https://www.codingninjas.com/codestudio/library/shortest-path-in-a-directed-acyclic-graph
//        Read above article for any sort of doubt.

//        |----------------------------------------------|
//        |        Step 1 - TOPO SORT, for sequence      |
//        |----------------------------------------------|

        //This solution is slightly different from my solution.
        //It uses TOPOSORT for the order in which nodes are visited
        int[] topoSort = new int[n];
        boolean[] vis = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for (int node = 0; node < n; node++) {
            if (!vis[node])
                topoDFS_weighted(graph, vis, node, stack);
        }


//        |--------------------------------------------------------|
//        |        ******** FINDING SHORTEST PATH ********         |
//        |                                                        |
//        |      Step 2 - Pop from stack, and visit this node.     |
//        |               Update its dist, if lesser.              |
//        |               Visit, its immediate adjNodes            |
//        |--------------------------------------------------------|

        Arrays.fill(dist, INFINITY);
        dist[src] = 0;

        while (!stack.isEmpty()) {
            int top = stack.pop(); // elem popped will be in topo order. i.e. 1st popped elem points to other following it.

            //NOTE : this if condition is for, if src and TOP of Stack aren't same.
            //We'll start doing the inner process, only after the src is popped.
            if (dist[top] != INFINITY) { //V IMP.
                for (Pair adjNode : graph.get(top)) {
                    int newDist = dist[top] + adjNode.weight; //adjNode.weight means, weight req to reach adj from top

                    if (newDist < dist[adjNode.node]){ // replace with lesser distance
                        dist[adjNode.node] = newDist;
                    }
                }
            }
        }

        //This is just to replace INF with -1. QUES Requirement
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == INFINITY)
                dist[i] = -1;
        }

    }

    //below toposort is for directed graphs, where we have Pair and not int.
    public static void topoDFS_weighted(ArrayList<ArrayList<Pair>> graph, boolean[] vis, int node, Stack<Integer> stack){
        if (!vis[node]) {
            vis[node] = true;
            for (Pair adjNode : graph.get(node)) {
                topoDFS_weighted(graph, vis, adjNode.node, stack);
            }
//          Uptil now, completely same as DFS code
//          Below adding node to stack.
            stack.push(node);
        }
    }


//------------------MY SOLUTION - DIFFERENT FROM ALL SOLUTIONS ONLINE!!------------------
//    IT is same as for undirected unit weight graphs, with slight changes. Instead of 1, add wt.
    public static void shortestDist_DAG_Weighted(ArrayList<ArrayList<Pair>> graph, int n, int[] dist, int src) {
//      No vis arr required, As multiple visits can be done to a node, and then we see whose dist is min!!
        Queue<Integer> q = new ArrayDeque<>();
//      Replace each val with (infinity) and then replace it if a dist lower than that occurs.
        Arrays.fill(dist, INFINITY);

        q.add(src);
        dist[src] = 0;

        while(!q.isEmpty()) {
            int parent = q.remove();

            for (Pair adjNode : graph.get(parent)) {
//              Check if the dist to adjNode from src is less than its stored Dist, then replace. Else ignore
//              dist[parent] is dist of parent from src
                int newDist = dist[parent] + adjNode.weight; //replaced 1 with the weight.

                if (newDist < dist[adjNode.node]){
                    dist[adjNode.node] = newDist;
//                  only that adjNode must enter queue, whose new dist </<= its current dist.
//                  REASON in copy. why its inside if.
                    q.add(adjNode.node);
                }
//              else ignore newDist.
            }
        }

        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == INFINITY)
                dist[i] = -1;
        }
    }

//    Use this to convert edges[][] to adjacency list. when not given directly.
//    FOR DIRECTED GRAPHS, JUST ADD EDGES ONE WAY.
    public static ArrayList<ArrayList<Integer>> edgesToList(int[][] edges, int n) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < edges.length; i++) {
//          For self loops. Prevents repetition of nodes. like 3-3, 3-5. So without this it will be. 3-3,3,5. 2 times 3.
            if (edges[i][0] == edges[i][1]) {
                graph.get(edges[i][0]).add(edges[i][1]);
                continue;
            }
//          Coz in undirected graphs, 0-1  --> vertex is both from 0 to 1, and 1 to 0
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }
//        System.out.println(graph);
        return graph;
    }

    public static void shortestDist_Undirected_UnitWt(ArrayList<ArrayList<Integer>> graph, int n, int[] dist, int src) {

    //        M1 gfg

//        Queue<Integer> q = new ArrayDeque<>();
//        boolean[] vis = new boolean[n];
////      If a node isn't reachable from src, its dist from it be -1
//        Arrays.fill(dist, -1);
//
////      Add src to Q, mark it vis, and its dist from itself as 0
//        q.add(src);
//        vis[src] = true;
//        dist[src] = 0;
//
//        while(!q.isEmpty()) {
//            int parent = q.remove();
//            for (int adjNode : graph.get(parent)) {
//                if (!vis[adjNode]) {
////                  Put it in Q, mark it vis, and write its dist
//                    q.add(adjNode);
//                    vis[adjNode] = true;
//                    dist[adjNode] = dist[parent] + 1;
//                }
//            }
//        }



//        M2
//          Below is strivers solution. See

//      No vis arr required, As multiple visits can be done to a node, and then we see whose dist is min!!
        Queue<Integer> q = new ArrayDeque<>();
//      Replace each val with (infinity) and then replace it if a dist lower than that occurs.
        Arrays.fill(dist, INFINITY);

        q.add(src);
        dist[src] = 0;

        while(!q.isEmpty()) {
            int parent = q.remove();

            for (int adjNode : graph.get(parent)) {
//              Check if the dist to adjNode from src is less than its stored Dist, then replace. Else ignore
//              dist[parent] is dist of parent from src
                int newDist = dist[parent] + 1;
                if (newDist < dist[adjNode]){
                    dist[adjNode] = newDist;
//                  only that adjNode must enter queue, whose new dist </<= its current dist.
//                  REASON in copy. why its inside if.
                    q.add(adjNode);
                }
//              else ignore newDist.
            }
        }

        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == INFINITY)
                dist[i] = -1;
        }
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
