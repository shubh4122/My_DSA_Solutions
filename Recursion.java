package DSA;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Recursion {
    public static void main(String[] args) {
//        print_1_to_n(10);
//        print_n_to_1(10);

//        ArrayList<Integer> arr = new ArrayList<>();
//        arr.add(4);
//        arr.add(7);
//        arr.add(3);
//        arr.add(32);
//        arr.add(53);
//        arr.add(31);
//        arr.add(363);
//        arr.add(13);
//        arr.add(23);
//        arr.add(36);
//        arr.add(2);
//        arr.add(1);
//        arr.add(8);
//        arr.add(3);
//        System.out.println(sortArrRecursively(arr));
        Stack<Integer> s = new Stack<>();
        s.push(4);
        s.push(45);
        s.push(1);
        s.push(3);
        s.push(8);
        s.push(53);
//        s.push(24);

        System.out.println(s);
//        sortStack(s);
        int k = s.size()/2 + 1;
        deleteMidOfStack(s, k);
        System.out.println(s);
    }

//----------Following problems are from Aditya verma, unless specified----------


    public static void deleteMidOfStack(Stack<Integer> s, int k) {
//        H : delete(s) --> deletes mid elem of stack
//        I : del(s) --> pop top
//                       del(stack') : del mid of previous stack in modified stack'[stack' - this is new stack with its previous top popped]
//                       push top back to the above stack whose mid elem is deleted
//        BC : if size = k(=size/2 +1) --> s.pop

//        int k = s.size()/2 + 1; //this is the mid elem position in stack irrespective of size of stack being even or odd
        if (s.size() == k) {
            s.pop();
            return;
        }

        int top = s.pop();
        deleteMidOfStack(s, k);
        s.push(top);
    }

    //Similar to array
    public static void sortStack(Stack<Integer> s) {
//        H : sort(s) --> sorted stack
//        I : sort(s) --> sort(stack except the top elem),
//                          Insert top to the sorted Stack
//        BC : if(s.size = 1) return;

        if (s.size() == 1) return;

        int top = s.pop();
        sortStack(s);
        insertInSortedStack(top, s);
    }

    private static void insertInSortedStack(int top, Stack<Integer> s) {
//        H : insert(top, s) --> inserts top in 's' in a way that it keeps 's' sorted
//        I : insert(top, s) --> Remove top' of this s.
//                               insert the passed elem(top), Reinsert the top' to stack
//        BC : if(s.size = 0 || s.peek < top) --> insert top to stack

        if (s.size() == 0 || top > s.peek()) {
            s.push(top);
            return;
        }

        int topSortedStack = s.pop();
        insertInSortedStack(top, s);
        s.push(topSortedStack);
    }

    public static ArrayList<Integer> sortArrRecursively(ArrayList<Integer> arr) {
        //Hypothesis - sort(arr) --> sorted arr
        //Inductive - sort(arr) --> sort(arr.remove(last elem)) [i.e. leaving the last elem of arr, we pass rest of the arr to sort function]
        //                                  and insert the last excluded elem in the sorted arr
        //Base Condition - if size = 1, return the only elem


        //BC
        if (arr.size() == 1)
            return arr;


//        Except the last elem, we pass rest of the arr
//        and get the sorted arr in sortedArr
        int lastElem = arr.remove(arr.size() - 1);
        ArrayList<Integer> sortedArr = sortArrRecursively(arr);

//        -------------Inserting Iteratively-------------
//        for (int i = 0; i < sortedArr.size(); i++) {
//            if(lastElem < sortedArr.get(i)) {
//                sortedArr.add(i, lastElem);
//                break;
//            }
//        }
//        if (lastElem > sortedArr.get(sortedArr.size()-1))
//            sortedArr.add(sortedArr.size(), lastElem);


        //-------------Inserting Recursively-------------
        insertInSortedArr(lastElem, sortedArr);
//      inserts the last elem that was held back to the sorted arr we receive
        return sortedArr;
    }

    private static void insertInSortedArr(int elem, ArrayList<Integer> sortedArr) {
//        Hypothesis - insert(elem, arr) --> insert elem in sorted arr, so it remains sorted
//        Induction - insert(...) --> remove this arr last elem, if its < elem. insert elem. Reinsert previously removed elem.
//        BC - if arr.size = 0 || arr.get(size - 1) <= elem --> push elem to back of arr

        if (sortedArr.size() == 0 || sortedArr.get(sortedArr.size() - 1) <= elem) {
            sortedArr.add(sortedArr.size(), elem);
            return;
        }

//        Removing the last elem of array, because its greater than the LastElem we get to insert
        int last = sortedArr.remove(sortedArr.size() - 1);
//        here we insert the elem(lastElem)
        insertInSortedArr(elem, sortedArr);
//        now we reinsert the elem we took out which was greater than lastElem
        sortedArr.add(sortedArr.size(), last);
    }

    public static void print_1_to_n(int n) {
        //Hypothesis - print(n) -->  1,2,3.....n
        //Inductive - print(n) --> print(n-1), sout(n)
        //Base Condition - if n=1 sout(1)
        if (n == 1){
            System.out.println(n);
            return;
        }

        print_1_to_n(n-1);
        System.out.println(n);
    }

    public static void print_n_to_1(int n) {
        //Hypothesis - print(n) -->  n, n-1,.....3,2,1
        //Inductive - print(n) --> sout(n), print(n-1)
        //Base Condition - if n=1 sout(1)
        if (n == 1){
            System.out.println(n);
            return;
        }

        System.out.println(n);
        print_n_to_1(n-1);
    }
}
