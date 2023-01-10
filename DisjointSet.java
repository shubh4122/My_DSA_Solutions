package DSA;

import java.util.ArrayList;

public class DisjointSet {
    ArrayList<Integer> rank = new ArrayList<>();
    ArrayList<Integer> parent = new ArrayList<>();

    DisjointSet(int numOfNodes) {
        for (int i = 0; i < numOfNodes + 1; i++) { // +1 to support 1 based indexing too
            rank.add(0); //Default values
            parent.add(i);
        }
    }

    public int findUltParent(int node) {//finding ultimate parent
        if (node == parent.get(node))
            return node;

        int ulp = findUltParent(parent.get(node));
        //it REPLACES val at index node with val ULP
        parent.set(node, ulp);
        return parent.get(node);
    }

    public void unionByRank(int u, int v) {
/*
         ----------------------------------------------
        |                 Union Algo                   |
        |     1. Find ult Parent of u,v (pu, pv)       |
        |     2. Find RANK of pu, pv                   |
        |     3. Attach smaller rank ultimate parent   |
        |        to larger Rank ultimate Par           |
         ----------------------------------------------
 */
        int pu = findUltParent(u);
        int pv = findUltParent(v);

        //if pu and pv of both same, then no need to union them
        if (pu == pv)   return;

        if (rank.get(pu) < rank.get(pv)) {
            parent.set(pu, pv); // Make pv as Parent of pu, That is attach pu to pv.
            //Rank of pv doesnt change coz it was already higher. so it wouldnt cause any change
        }
        else if (rank.get(pu) > rank.get(pv)) {
            parent.set(pv, pu);
        }
        else {//when both rank equal, then join any to any, and increase RANK of final UltPar
            parent.set(pv, pu);
            rank.set(pu, rank.get(pv)+1);//or rank[pu] + 1 will be same
        }
    }
}

class Main {
    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(7);
        ds.unionByRank(1,2);
        ds.unionByRank(2,3);
        ds.unionByRank(4,5);
        ds.unionByRank(6,7);
        ds.unionByRank(5,6);

        //Check if 3 and 7 belong to same set/component
        if (ds.findUltParent(3) == ds.findUltParent(7))
            System.out.println("Same component");
        else System.out.println("Not same component");

        //add another edge
        ds.unionByRank(3,7);

        //Again check for 3 and 7
        if (ds.findUltParent(3) == ds.findUltParent(7))
            System.out.println("Same component");
        else System.out.println("Not same component");

    }
}
