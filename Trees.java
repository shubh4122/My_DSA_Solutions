package DSA;

import java.util.*;

class Node {
    int data;
    Node left, right;

    Node(int data) {
        this.data = data;
    }
}

public class Trees {
    public static void main(String[] args) {
        Node root = new Node(10);
        root.left = new Node(20);
        root.right = new Node(30);
        root.left.left = new Node(40);
        root.left.right = new Node(50);
        root.right.right = new Node(60);
        root.left.right.left = new Node(70);
        root.left.right.right = new Node(80);

        //BST
//        Node root = new Node(10);
//        root.left = new Node(5);
//        root.right = new Node(15);
//        root.right.left = new Node(12);
//        root.right.right = new Node(18);

//        System.out.println(insertBSTRecursive(root, 11));
//        System.out.println(insertBSTIterative(root, 8).data);

//--------Traversals/Printing of tree--------
//        inorder(root);
//        System.out.println();
//        inorderIterative(root);
//        preorderIterative(root);
//        System.out.println();
//        preorder(root);
//        System.out.println();
//        postorder(root);

//--------Traversals/Printing of tree returning List--------
//        System.out.println(inorderReturn(root));

//--------Height of Tree--------
//        System.out.println(height(root));

//--------Print nodes at K distance from root of Tree--------
//        nodesAtKDistance(root, 2);

//--------BFS tree Traversal--------
//        levelOrderTraversal(root);
//        System.out.println();
//        bfsEfficient(root);
        bfsEfficientLineByLine(root);
        System.out.println(bfsEfficientLbLReturn(root));

//--------Size of tree(No. of nodes)--------
//        System.out.println(size(root));

//--------Max of Tree--------
//        System.out.println(maxInTree(root));
    }

    //BST

    public static Node insertBSTIterative(Node root, int x) {
        Node ptr = root;
        if (root == null)
            return new Node(x);

        while (ptr != null){
            if (ptr.data < x) {
                if (ptr.right == null) {
                    ptr.right = new Node(x);
                    break;
                }

                ptr = ptr.right;
            }

            else if (ptr.data > x) {
                if (ptr.left == null){
                    ptr.left = new Node(x);
                    break;
                }

                ptr = ptr.left;
            }
        }
        return root;
    }
    //Recursive
    public static Node insertBSTRecursive(Node root, int x) {
        //also change the tree first.
        if (root == null) return new Node(x);
        else if (root.data < x)
            //doing this, root.right is to link the inserted node to the last root, which is less/greater than key
            root.right = insertBSTRecursive(root.right, x);
        else if (root.data > x)
            root.left = insertBSTRecursive(root.left, x);
        else return root; //equality case

        return root;
    }


    public static void preorderIterative(Node root){
        if (root == null) {
            return;
        }

        Node curr = root;
        Stack<Node> stack = new Stack<Node>();
        stack.push(curr);
        System.out.print(stack.peek().data+" ");
        while (!stack.isEmpty()) {
//          While the left of root is not null, keep on pushing it. Root obviously changes below.
            while (curr.left != null) {
                stack.push(curr.left);
                System.out.print(stack.peek().data+" ");
                curr = curr.left;
            }

//          Popping off nodes now and pushing rights
            Node popped = stack.pop();

            if (popped.right != null) {
                stack.push(popped.right);
                System.out.print(stack.peek().data+" ");
                curr = popped.right;
//              Root changes and the new node is the right subtree's parent now.
//              Again we go on pushing all left childs in next iteration.
            }
        }
    }

    public static void inorderIterative(Node root) {
        if (root == null) {
            return;
        }

        Node curr = root;
        Stack<Node> stack = new Stack<Node>();
        stack.push(curr);
        while (!stack.isEmpty()) {
//          While the left of root is not null, keep on pushing it. Root obviously changes below.
            while (curr.left != null) {
                stack.push(curr.left);
                curr = curr.left;
            }

//          Popping off nodes now and pushing rights
            Node popped = stack.pop();
            System.out.print(popped.data+" ");

            if (popped.right != null) {
                stack.push(popped.right);
                curr = popped.right;
//              Root changes and the new node is the right subtree's parent now.
//              Again we go on pushing all left childs in next iteration.
            }
        }
    }

    public static int maxInTree(Node root) {
        if (root == null)
            return Integer.MIN_VALUE;

        int maxLeft = maxInTree(root.left);
        int maxRight = maxInTree(root.right);

        return Math.max(root.data, Math.max(maxRight, maxLeft));
    }


    public static int size(Node root) {
        if (root == null)
            return 0;

        return size(root.left) + size(root.right) + 1;
    }


//  Breadth First Search

    public static ArrayList<ArrayList<Integer>> bfsEfficientLbLReturn(Node root) {
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        q.offer(null);

        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
        int h = height(root);
        for (int i = 0; i < h; i++) {
            a.add(new ArrayList<Integer>());
        }
        int i = 0;

        while (!q.isEmpty()) {
            Node temp = q.poll();

//          Here we need to use poll and offer, coz add and remove will through Exception for null
            if (temp == null) {
                if (!q.isEmpty())
                    q.offer(null);

                i++;
                // System.out.println();
            }
            else {
                // System.out.print(temp.data + " ");
                a.get(i).add(temp.data);

                if (temp.left != null)
                    q.offer(temp.left);
                if (temp.right != null)
                    q.offer(temp.right);

            }

        }

        return a;
    }

    public static void bfsEfficientLineByLine(Node root) {
//        TC : O(n)

//        Queue<Node> q = new ArrayDeque<>();

        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        q.offer(null);

        while (!q.isEmpty()) {
            Node temp = q.poll();

//          Here we need to use poll and offer, coz add and remove will through Exception for null
            if (temp == null) {
                if (!q.isEmpty())
                    q.offer(null);
                System.out.println();
            }

            else {
                System.out.print(temp.data + " ");

                if (temp.left != null)
                    q.offer(temp.left);
                if (temp.right != null)
                    q.offer(temp.right);

            }

        }
    }

    public static void bfsEfficient(Node root) {
//        TC : O(n)

        Queue<Node> q = new ArrayDeque<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node temp = q.remove();
            System.out.print(temp.data+" ");

            if (temp.left != null)
                q.add(temp.left);
            if (temp.right != null)
                q.add(temp.right);
        }
    }
    public static void levelOrderTraversal(Node root) {
        //Very ineficient Solution. See more efficient solution in Video and do.
//        TC : O(hn)
        for (int k = 0; k < height(root); k++) {
            nodesAtKDistance(root, k);
        }
    }
    
    
    public static void nodesAtKDistance(Node root, int k) {
        if (root == null || k < 0)
            return;

        if (k==0){
            System.out.print(root.data+" ");
            return;
        }

        nodesAtKDistance(root.left, k-1);
        nodesAtKDistance(root.right, k-1);
    }

    public static ArrayList<Integer> inorderReturn(Node root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root != null) {
            list.addAll(inorderReturn(root.left));
            list.add(root.data);
            list.addAll(inorderReturn(root.right));
        }
        return list;
    }
    public static void inorder(Node root) {
        if(root == null)
            return;
        if (root.left == null && root.right == null) {
            System.out.print(root.data+" ");
            return;
        }

        inorder(root.left);
        System.out.print(root.data+" ");
        inorder(root.right);
    }

    public static void preorder(Node root) {
        if (root != null) {
            System.out.print(root.data+" ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    public static void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data+" ");
        }
    }

    public static int height(Node root) {
        if (root == null)
            return 0;

        int hLeftSubtree = height(root.left) + 1;
        int hRightSubtree = height(root.right) + 1;

        return Math.max(hLeftSubtree, hRightSubtree);
    }
}
