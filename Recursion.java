package DSA;

import java.util.*;

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
//        Stack<Integer> s = new Stack<>();
//        s.push(4);
//        s.push(45);
//        s.push(1);
//        s.push(3);
//        s.push(8);
//        s.push(53);
//        s.push(24);

//        System.out.println(s);
//        sortStack(s);
//        int k = s.size()/2 + 1;
//        deleteMidOfStack(s, k);
//        reverseStack(s);
//        System.out.println(s);

//        System.out.println(kthGrammar(4, 6));

//        powerSet("abc", "");
        ArrayList<String> a = new ArrayList<>();
        powerSetLexicographically("abc", "", a);
        Collections.sort(a);
        System.out.println(a);

//        permutationSpaces("abc", "");
//        System.out.println("--------------------------------------");
//        System.out.println(returnPermutationSpaces("abc", ""));

//        permutationWithCaseChange("abc", "");

//        System.out.println(letterCasePermutation("a1B2", ""));

//        System.out.println(generateBalancedParenthesis(3,3,""));
//        System.out.println(NBitBinary(4, 4, 4, ""));
//        System.out.println(josephusProb(40, 7));


//        int n = 40, k = 7;
//        ArrayList<Integer> arr = new ArrayList<>(n);
//        for (int i = 0; i < n; i++) {
//            arr.add(i+1);
//        }
//
//        System.out.println(josephusProbRecursive(arr, k, 0));
    }


//----------Following problems are from Aditya verma, unless specified----------

    public static int josephusProbRecursive(ArrayList<Integer> arr, int k, int curr) {
//        H : jPR(arr, k, curr) --> returns safe posn in the circle
//        I : jPR(arr, k, curr) --> arr.remove(killat)
//                                  jPR(arr, k, killat)
//        BC : if arr.size == 1, return arr[0]

        if (arr.size() == 1)
            return arr.get(0);

        int killAt = (curr + k - 1) % arr.size();
        arr.remove(killAt);
        return josephusProbRecursive(arr, k, killAt);
    }

    public static int josephusProb(int n, int k) {
        ArrayList<Integer> arr = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            arr.add(i+1);
        }

//        this is current position
        int curr = 0;
        while (arr.size() > 1) {
            int killAt = (curr + k - 1) % arr.size();

            arr.remove(killAt);
            curr = killAt;
        }

        return arr.get(0);
    }

    public static List<String> NBitBinary(int n, int ones, int zeros, String op) {
        List<String> list = new ArrayList<>();
        if (ones+zeros == n) {
            list.add(op);
            return list;
        }

        if (ones == zeros) {
            list.addAll(NBitBinary(n, ones - 1, zeros, op + "1"));
        }
        else {
            list.addAll(NBitBinary(n, ones-1, zeros, op+"1"));
            list.addAll(NBitBinary(n, ones, zeros-1, op+"0"));
        }
        return list;
    }


//  This is an example of Extended Ip/Op method. Where inp and out data types are not similar
    public static List<String> generateBalancedParenthesis(int o, int c, String op) {
//        o = opening bracket count, c = closing bracket count
        List<String> list = new ArrayList<>();

        if (o == 0 && c == 0) {
            list.add(op);
            return list;
        }
//      For cases where we only have single choices in the tree
//      case 1 -> o = c
        if (o == c) {
            list.addAll(generateBalancedParenthesis(o-1, c, op+"("));
        }

//      case 2 -> o = 0
        else if (o == 0) {
            list.addAll(generateBalancedParenthesis(o, c-1, op+")"));
        }

//      General case, with 2 choices. First taking '(' and then ')' choice.
        else {
            list.addAll(generateBalancedParenthesis(o-1, c, op+"("));
            list.addAll(generateBalancedParenthesis(o, c-1, op+")"));
        }


        return list;
    }

    public static ArrayList<String> letterCasePermutation(String ip, String op) {
//        Input here can have lowercase, uppercase letters and digits
        ArrayList<String> a = new ArrayList<>();
        if (ip.isEmpty()) {
            a.add(op);
            return a;
        }

        char c = ip.charAt(0);
        if (!Character.isDigit(c)) {
            a.addAll(letterCasePermutation(ip.substring(1), op+ changeCase(c)));
            a.addAll(letterCasePermutation(ip.substring(1), op + c));
        }
        else {
            a.addAll(letterCasePermutation(ip.substring(1), op+c));
        }
        return a;
    }

    private static Character changeCase(char character) {
        if (Character.isLowerCase(character))   return Character.toUpperCase(character);
        else    return Character.toLowerCase(character);
    }

    public static void permutationWithCaseChange(String ip, String op) {
//        We assume input is completely in lowercase

        if (ip.isEmpty()){
            System.out.println(op);
            return;
        }

        permutationWithCaseChange(ip.substring(1), op+Character.toString(ip.charAt(0)).toUpperCase());
        permutationWithCaseChange(ip.substring(1), op+Character.toString(ip.charAt(0)));

    }

    public static ArrayList<String> returnPermutationSpaces(String ip, String op) {
//        This function is same as permutationSpaces, except that it returns an arraylist.
        ArrayList<String> a = new ArrayList<>();

        if (ip.isEmpty()) {
            a.add(op);
            return a;
        }

        if (op.isEmpty())
            a.addAll(returnPermutationSpaces(ip.substring(1), Character.toString(ip.charAt(0))));

        else {
//            Char with space
            a.addAll(returnPermutationSpaces(ip.substring(1), op+" "+ip.charAt(0)));
//            Char without space
            a.addAll(returnPermutationSpaces(ip.substring(1), op+ip.charAt(0)));
        }

        return a;
    }

    public static void permutationSpaces(String ip, String op) {
        if (ip.isEmpty()) {
            System.out.println(op);
            return;
        }

//        this is done to handle the first call, where there is no other choice than putting no space before 1st char
//        Eg: for ip = abc, this step would do : op = a, ip = bc. Then we have regular recursive tree code.
        if (op.isEmpty())
            permutationSpaces(ip.substring(1), Character.toString(ip.charAt(0)));

        else {
//            Char with space
            permutationSpaces(ip.substring(1), op+" "+ip.charAt(0));
//            Char without space
            permutationSpaces(ip.substring(1), op+ip.charAt(0));
        }
    }

    public static void powerSetLexicographically(String ip, String op, ArrayList<String> a) {
//        Subset == Powerset
//        ip - input / op - output --> I/O Mehtod
        if (ip.equals("")){
//            System.out.println(op);
            a.add(op);
            return;
        }

//      CHOICE 1 -->  Not taking the first letter of input
        powerSetLexicographically(ip.substring(1), op, a);

//      CHOICE 2 --> Taking the first letter of input
        powerSetLexicographically(ip.substring(1), op+ip.charAt(0), a);

    }

    //IMP- When subset done on an array/arrayList, its IMPLEMENTATION is important and CHALLENGING!
    //If we make any changes to arr it changes forever, and change is carried through all rec calls
    //Unlike string. Hence slight change in code/strategy
    public static void subsetArr(int[] nums, List<Integer> tempOp, List<List<Integer>> ans, int index) {
        /*
            - nums - num arr
            - tempOp - arr that holds each subset
            - ans - arr that holds all the subset - final ans
            - index - instead of changing input arr(nums) we take/not take elem by passing next index(new start of arr)
         */

        //BC
        if (index == nums.length) {//empty nums arr
//            ans.add(tempOp); -- This doesnt work.! gives empty lists!!!!!
            //This is doing DEEP COPY!
            ans.add(new ArrayList<>(tempOp));
            return;
        }

        //Main code

        //Not take
        subsetArr(nums, tempOp, ans, index + 1);//index+1 means we truncated nums by 1. ( coz we made decision on that elem)

        //Take
        tempOp.add(nums[index]); //we take the current elem of nums as part of our subset
        subsetArr(nums, tempOp, ans, index + 1);

        //IMP - must unchange the op arr. We added elem to OP to include it.  BUT then we need to empty it too, for other rec calls.!
        tempOp.remove(tempOp.size() - 1);
    }


    public static void powerSetLexicographically(String ip, String op) {
//        Subset == Powerset
//        ip - input / op - output --> I/O Mehtod
        if (ip.equals("")){
            System.out.println(op);
            return;
        }

//      CHOICE 1 -->  Not taking the first letter of input
        powerSetLexicographically(ip.substring(1), op);

//      CHOICE 2 --> Taking the first letter of input
        powerSetLexicographically(ip.substring(1), op+ip.charAt(0));

    }

    public static int kthGrammar(int n, int k) {
//        IBH -- see copy.
        if (n==1)   return 0;

        int len = (int) Math.pow(2, n-1);
        int mid = len/2;

        if(k <= mid)
            return kthGrammar(n-1, k);

        else
            return kthGrammar(n-1, k-mid) == 0 ? 1 : 0;
    }

    public static void reverseStack(Stack<Integer> s) {
//        H : rev(s) --> reverses the stack
//        I : rev(s) --> remove top
//                       reverse rest of stack (rev(s'))
//                       reinsert top in front of stack
//        BC : if size = 1   return

        if (s.size() == 1)  return;

        int topTemp = s.pop();
        reverseStack(s);
        insertElemInFrontStack(topTemp, s);
    }

    private static void insertElemInFrontStack(int temp, Stack<Integer> s) {
//        H : insert(t, s) --> inserts t in stack front
//        I : insert(t, s) --> insert(t, s[new stack formed on removing its top elem])
//                             reinsert the top elem
//        BC : if stack.isEmpty --> push t in stack [i.e. inserted temp to the front of stack, and now all other elem will be reinserted]

        if (s.isEmpty()) {
            s.push(temp);
            return;
        }

        int top = s.pop();
        insertElemInFrontStack(temp, s);
        s.push(top);
    }

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
