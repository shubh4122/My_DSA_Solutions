package DSA;

import java.util.ArrayList;
import java.util.Collections;

public class Recursion {
    public static void main(String[] args) {
//        print_1_to_n(10);
//        print_n_to_1(10);
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(4);
        arr.add(7);
        arr.add(3);
        arr.add(32);
        arr.add(53);
        arr.add(31);
        arr.add(363);
        arr.add(13);
        arr.add(23);
        arr.add(36);
        arr.add(2);
        arr.add(1);
        arr.add(8);
        arr.add(3);
        System.out.println(sortArrRecursively(arr));
    }

//----------Following problems are from Aditya verma, unless specified----------

    public static ArrayList<Integer> sortArrRecursively(ArrayList<Integer> arr) {
        //Hypothesis - sort(arr) --> sorted arr
        //Inductive - sort(arr) --> sort(arr.remove(last elem)) [i.e. leaving the last elem of arr]
        //                                  and insert the last excluded elem in the sorted arr
        //Base Condition - if size = 1, return the only elem

        if (arr.size() == 1)
            return arr;

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

        return sortedArr;
    }

    private static void insertInSortedArr(int lastElem, ArrayList<Integer> sortedArr) {
//        Hypothesis - insert(lastelem, arr) --> insert elem in sorted arr, so it remains sorted]
//        Induction - insert(...) --> remove this arr last elem, if its < lastElem. insert lastElem. Reinsert previously removed elem.
//        BC - if arr.size = 0 || arr.get(size - 1) <= lastElem --> push elem to back of arr

        if (sortedArr.size() == 0 || sortedArr.get(sortedArr.size() - 1) <= lastElem) {
            sortedArr.add(sortedArr.size(), lastElem);
            return;
        }
        int last = sortedArr.remove(sortedArr.size() - 1);
        insertInSortedArr(lastElem, sortedArr);
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
