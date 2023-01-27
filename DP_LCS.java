package DSA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DP_LCS {
    public static void main(String[] args) {
        String x = "abac";
        String y = "cab";
        int lx = x.length();
        int ly = y.length();

        int[][] dpMem = new int[lx+1][ly+1];
        String[][] dpMems = new String[lx+1][ly+1];
        List<Integer>[][] dp = new List[lx+1][ly+1];  //for the arr ques

        for (int i = 0; i < dpMem.length; i++)
            Arrays.fill(dpMem[i], -1);

        for (int i = 0; i < dpMems.length; i++)
            Arrays.fill(dpMems[i], "-1");

//        System.out.println(lcs(lx, ly, x, y, dpMem));
        System.out.println(lcsPrint(lx, ly, x, y, dpMem));
        System.out.println(lcsPrintMy(lx, ly, x, y, dpMems));
//        System.out.println(scs(lx, ly, x, y, dpMem));
    }


    public static int lrp(String x, int lx, int[][] dp) {
        //Step 1: Create 2nd Str
        String y = x;
        int ly = y.length();

        //Step 2: Find **LCS** of a, b. Only MINOR change is, skip when i == j
        //Initialization
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < lx + 1; i++)
            dp[i][0] = 0;

        //Choice Diagram
        for (int i = 1; i < lx + 1; i++) {
            for (int j = 1; j < ly + 1; j++) {
                //ONLY DIFFERENCE IN LRP & LCS--------------⬇️
                if (x.charAt(i - 1) == y.charAt(j - 1) && i != j)
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else //last char doesn't matches
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }

        return dp[lx][ly];
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int minDelToPalindrome(String s, int[][] dp) {
        return s.length() - longestPalindromicSubseq(s, dp); // When there's only 1 str, dp[s+1][s+1]
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int longestPalindromicSubseq(String a, int[][] dp) {
        //Step 1: Find 2nd HIDDEN string
        String revA = new StringBuilder(a).reverse().toString();

        //Step 2: Call LCS for the above 2. That will be the final ANS!!!!!
        return lcs(a.length(), revA.length(), a, revA, dp);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //operations here mean, Insertions and Deletions
    public static int minOps(String a, String b, int[][] dpMem) {
        //Step 1: Declare Lengths
        int la = a.length();
        int lb = b.length();

        //Step 2: Find LCS len
        int lLCS = lcs(la, lb, a, b, dpMem);

        //Step 3: Find no. of Insertions and Deletions
        int ins = lb - lLCS;
        int del = la - lLCS;

        return ins+del;
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Shortest Common SUPERsequence
    public static String scs(int lx, int ly, String x, String y, int[][] dpMem) {
        //Step 1:   Fill the Table(int dp Table, storing len of subseq) first
        lcsTabulation(lx, ly, x, y, dpMem);

        //Step 2:   Start from last of table, and find the Common Subseq
        StringBuilder ans = new StringBuilder();

        int i = lx, j = ly;
        while(i > 0 && j > 0) {
            if (x.charAt(i - 1) == y.charAt(j - 1)) {
                ans.append(x.charAt(i - 1));    //a. First add it to Ans str
                //b. mov ahead - DIAGONAL MOVE
                i = i - 1;//i--
                j = j -1 ;//j--
            }
            else {
                if (dpMem[i-1][j] > dpMem[i][j-1]) {
                    ans.append(x.charAt(i - 1));
                    i = i - 1;
                }
                else {
                    ans.append(y.charAt(j - 1));
                    j = j - 1;
                }
            }
        }

        //Step 3: Add remaining letters to scs --> These are UNCOMMON(or NON-LCS) letters, to be included in scs
        while (i > 0) {//And j = 0
            ans.append(x.charAt(i - 1));
            i--;
        }
        while (j>0) {//And i = 0
            ans.append(y.charAt(j - 1));
            j--;
        }
        return ans.reverse().toString();
        //https://leetcode.com/problems/shortest-common-supersequence/description/
    }

    public static int scsLength(int lx, int ly, String x, String y, int[][] dpMem) {
        return x.length() + y.length() - lcs(lx, ly, x, y, dpMem);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static List<Integer> printAllLCS(){
        //https://practice.geeksforgeeks.org/problems/print-all-lcs-sequences3413/1
        //DO THIS QUESTION - GFG HARD
        return new ArrayList<>();
    }
    public static String lcsPrint(int lx, int ly, String x, String y, int[][] dpMem) {
        //Step 1:   Fill the Table(int dp Table, storing len of subseq) first
        lcsTabulation(lx, ly, x, y, dpMem); //NOTE: don't use LCS MEMO, because it'll have -1 as base case. so it'll cause problems

        //Step 2:   Start from last of table, and find the Common Subseq
        StringBuilder ans = new StringBuilder();

        int i = lx, j = ly;
        while(i > 0 && j > 0) {
            if (x.charAt(i-1) == y.charAt(j-1)) {
                ans.append(x.charAt(i-1));    //a. First add it to Ans str
                //b. mov ahead - DIAGONAL MOVE
                i = i - 1;//i--
                j = j -1 ;//j--
            }
            else {
                if (dpMem[i-1][j] > dpMem[i][j-1])  i = i - 1;
                else                                j = j - 1;
            }
        }
        return ans.reverse().toString();
    }

    public static List<Integer> lcsPrintArr(int lx, int ly, List<Integer> x, List<Integer> y, List<Integer>[][] dpMems) {
//        Ques - https://www.hackerrank.com/challenges/dynamic-programming-classics-the-longest-common-subsequence/problem?isFullScreen=false

        //BC:
        if (lx == 0 || ly == 0)
            return new ArrayList<>(); //0 len when any one str gets finished. coz then there can be no common subseq

        //Memoization
        if (!dpMems[lx][ly].isEmpty())
            return dpMems[lx][ly];

        //CHOICE DIAGRAM
        if (x.get(lx - 1).equals(y.get(ly - 1))) {
            dpMems[lx][ly].addAll(lcsPrintArr(lx - 1, ly - 1, x, y, dpMems));
            dpMems[lx][ly].add(x.get(lx - 1));
            return dpMems[lx][ly];
        }
        else {//last char doesn't matches
            List<Integer> a = lcsPrintArr(lx, ly - 1, x, y, dpMems);
            List<Integer> b = lcsPrintArr(lx - 1, ly, x, y, dpMems);
            if (a.size() > b.size())
                dpMems[lx][ly].addAll(a);

            else
                dpMems[lx][ly].addAll(b);

            return dpMems[lx][ly];
        }
    }

    //Returns ANY one of the LCS
    public static String lcsPrintMy(int lx, int ly, String x, String y, String[][] dpMems) {
        //NOTE: I DON'T KNOW IF THIS IS CORRECT. THIS IS MY SOLUTION AND IT WORKED FOR MANY TEST CASES. BUT CANT
        //CONFIDENTLY SAY IT WILL ALWAYS WORK. COZ THERE IS NO QUES FOR IT
        //https://www.hackerrank.com/challenges/dynamic-programming-classics-the-longest-common-subsequence/problem?isFullScreen=false
        //IT WORKS!!!!!! SO ITS A CORRECT SOLUTION!!!!!!!

        //BC:
        if (lx == 0 || ly == 0)
            return ""; //0 len when any one str gets finished. coz then there can be no common subseq

        //Memoization
        if (!dpMems[lx][ly].equals("-1"))
            return dpMems[lx][ly];

        //CHOICE DIAGRAM
        if (x.charAt(lx - 1) == y.charAt(ly - 1))
            return dpMems[lx][ly] = lcsPrintMy(lx - 1, ly - 1, x, y, dpMems) + x.charAt(lx-1);
        else {//last char doesn't matches
            String a = lcsPrintMy(lx, ly - 1, x, y, dpMems);
            String b = lcsPrintMy(lx - 1, ly, x, y, dpMems);
            return dpMems[lx][ly] = a.length() > b.length() ? a : b;
        }

    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int longestCommonSUBSTRING_Tabulation(int lx, int ly, String x, String y) {
        //table
        int[][] dp = new int[lx+1][ly+1];

        //Initialization
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < lx + 1; i++)
            dp[i][0] = 0;

        //Choice Diagram
        for (int i = 1; i < lx + 1; i++) {
            for (int j = 1; j < ly + 1; j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1))
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else //last char doesn't matches
                    dp[i][j] = 0;
            }
        }

        //Now, here we need to find what is the MAX val in Matrix
        //Here we dont simply return dp[lx][ly]
        int maxLen = -1;
        for (int i = 0; i < lx + 1; i++)
            for (int j = 0; j < ly + 1; j++)
                if (maxLen < dp[i][j])
                    maxLen = dp[i][j];

        return maxLen;
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int lcsTabulation(int lx, int ly, String x, String y, int[][] dp) {

        //Initialization
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < lx + 1; i++)
            dp[i][0] = 0;

        //Choice Diagram
        for (int i = 1; i < lx + 1; i++) {
            for (int j = 1; j < ly + 1; j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1))
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else //last char doesn't matches
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }

        return dp[lx][ly];
    }

    public static int lcs(int lx, int ly, String x, String y, int[][] dpMem) {
//        Hypothesis --> lcs returns the len of common subsequence between
//                       2 given strings. And we ensure it is the longest.
        //BC:
        if (lx == 0 || ly == 0)
            return 0; //0 len when any one str gets finished. coz then there can be no common subseq

        //Memoization
        if (dpMem[lx][ly] != -1)
            return dpMem[lx][ly];

        //CHOICE DIAGRAM
        if (x.charAt(lx - 1) == y.charAt(ly - 1))
            return dpMem[lx][ly] = 1 + lcs(lx - 1, ly - 1, x, y, dpMem);
        else //last char doesn't matches
            return dpMem[lx][ly] = Math.max(lcs(lx,ly - 1, x, y, dpMem), lcs(lx - 1,ly, x, y, dpMem));

//        https://leetcode.com/problems/longest-common-subsequence/
//        https://practice.geeksforgeeks.org/problems/longest-common-subsequence-1587115620/1
    }
}
