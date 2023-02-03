package DSA;

import java.util.Arrays;

public class DP_MCM {
    public static void main(String[] args) {
        int[] arr = {40, 20, 30, 10, 30};
        String s = "T^F|F";//"T|F^F&T|F^F^F^T|T&T^T|F^T^F&F^T|T^F";
        int n = arr.length;
        int sn = s.length();
        int[][] dp = new int[sn+1][sn+1];
        for (int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i], -1);

        int[][][] dp3d = new int[2][sn+1][sn+1];//2 because true and false
        //i took [2] 1st as a row because it became easier to fill the arr with -1.
        //i could have taken it as [][][2]
        for (int i = 0; i < sn; i++) {
            Arrays.fill(dp3d[0][i], -1);//fillig false part
            Arrays.fill(dp3d[1][i], -1);//filling true part
        }

//        System.out.println(mcm(arr, 1, arr.length - 1, dp));
//        System.out.println(minPalindromePartition(s, 0, sn - 1, dp));
        System.out.println(countWaysBooleanParenthesization(s, 0, sn - 1, true, dp3d));
    }

    public static int countWaysBooleanParenthesization(String s, int i, int j, boolean isTrue, int[][][] dp3d) {
        //def - func returns num of ways in which a string from i to j, will be (isTrue, i.e. if its true, then true. and if false then false)
        //BC
        if (i > j)
            return 0;// no ways

        if (i == j) {
            if (isTrue)
                return (s.charAt(i) == 'T') ? 1 : 0; //i.e. if the only char is T, then there is 1 way to evaluate it to be true. else 0
            else //isTrue = false
                return (s.charAt(i) == 'F') ? 1 : 0;
        }

        //MEMOIZATION
        if (dp3d[isTrue ? 1:0][i][j] != -1)
            return dp3d[isTrue ? 1:0][i][j];

        //CODE
        int ways = 0;
        for (int k = i+1; k <= j - 1; k+=2) {
            //left expression
            int waysLT = countWaysBooleanParenthesization(s, i, k - 1, true, dp3d);
            int waysLF = countWaysBooleanParenthesization(s, i, k - 1, false, dp3d);
            //right expression
            int waysRT = countWaysBooleanParenthesization(s, k + 1, j, true, dp3d);
            int waysRF = countWaysBooleanParenthesization(s, k + 1, j, false, dp3d);

            //calc ans(ways here) from tempAns(waysXX here)
            if (s.charAt(k) == '&') {
                if (isTrue)//1 way in AND, T&T = T only
                    ways += (waysLT * waysRT);
                else //3 ways. T&F, F&T, F&F = F
                    ways += ((waysLT * waysRF)   +   (waysLF * waysRT)   +   (waysLF * waysRF));
            }

            else if (s.charAt(k) == '|'){
                if (isTrue)//3 ways in OR. T|T, T|F, F|T == T all.
                    ways += ((waysLT * waysRT)   +   (waysLT * waysRF)   +   (waysLF * waysRT));
                else //1 way. F|F = F
                    ways += (waysLF * waysRF);
            }

            else {
                if (isTrue)//2 ways in XOR. T^F, F^T = T
                    ways += ((waysLT * waysRF) + (waysLF * waysRT));
                else //2 ways. T^T, F^F = F
                    ways += ((waysLT * waysRT)   +   (waysLF * waysRF));
            }
        }

        return dp3d[isTrue ? 1:0][i][j] = ways % 1003;// ques asked ans%1003
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int minPalindromePartition(String s, int i, int j, int[][] dp) {
        //BC
        //if already palindrome, no partition required
        if (isPalindrome(s, i, j))
            return 0; // 0 partitions

        //this as a whole is redundant bc??!!!
//        if (i >= j) //= is optional. coz that base case will be dealt by above isPal condition
//            return 0;

        //Memoization
        if (dp[i][j] != -1)
            return dp[i][j];

        //Break into k(partitioning)
        int min = Integer.MAX_VALUE;
        int iTok = -1, kToj = -1;
        for (int k = i; k <= j-1; k++) {
            //MORE OPTIMISATION!!
            if (dp[i][k] == -1)
                dp[i][k] = minPalindromePartition(s, i, k, dp);

            if (dp[k+1][j] == -1)
                dp[k+1][j] = minPalindromePartition(s, k+1, j, dp);

            //+1 is the partition.
            int tempAns = dp[i][k] + 1 + dp[k+1][j];
            min = Math.min(min, tempAns);
        }
        return dp[i][j] = min;
    }

    public static boolean isPalindrome(String s, int i, int j) {
        //NOTE: .equals doesn't work on StringBuilders. Because it doesn't override equals method and hence compares REFERENCE unlike string
        return (s.substring(i, j+1).equals(new StringBuilder(s.substring(i, j+1)).reverse().toString()));
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int mcm(int[] arr, int i, int j, int[][] dp) {
        //BC
        if (i >= j) //1st INVALID I/P. = used coz, when i=j only one cell remains, and its not valid
            return 0;

        //Memoization
        if (dp[i][j] != -1)
            return dp[i][j];

        //breaking on k
        int min = Integer.MAX_VALUE;

        for (int k = i; k <= j-1; k++) { //same as k<j
            int tempAns = mcm(arr, i, k, dp) + (arr[i-1]*arr[k]*arr[j]) + mcm(arr, k+1, j, dp);

            min = Math.min(min, tempAns);
        }

        return dp[i][j] = min;
    }
}
