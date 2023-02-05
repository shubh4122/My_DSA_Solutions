package DSA;

public class DP_Trees {
    public static void main(String[] args) {
        Node root = new Node(10);
        root.left = new Node(20);
        root.right = new Node(30);
        root.left.left = new Node(40);
        root.left.right = new Node(50);
        root.right.right = new Node(60);
        root.left.right.left = new Node(70);
        root.left.right.right = new Node(80);

        //Find diameter of tree
        int[] diameter = new int[1];
        height(root, diameter);
        System.out.println(diameter[0]);
    }



    //O(n)TC - Diameter of tree
    public static int height(Node node, int[] diameter) {
        if(node == null)
            return 0;

        int l = height(node.left, diameter);
        int r = height(node.right, diameter);

        diameter[0] = Math.max(diameter[0], 1+l+r);

        return 1+ Math.max(l,r);
    }

    //O(n^2) TC
    public static int diameterOfTree(Node node) {
        if (node == null)
            return 0;

        int l = Trees.height(node.left);
        int r = Trees.height(node.right);

        int diameter = 1+l+r;
        int d1 = diameterOfTree(node.left);
        int d2 = diameterOfTree(node.right);

        return Math.max(diameter, Math.max(d1, d2));
    }
}
