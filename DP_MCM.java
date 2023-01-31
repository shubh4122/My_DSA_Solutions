package DSA;

import java.util.Arrays;

public class DP_MCM {
    public static void main(String[] args) {
        int[] arr = {40, 20, 30, 10, 30};
        String s = "abbcac";
        int n = arr.length;
        int sn = s.length();
        int[][] dp = new int[sn+1][sn+1];
        for (int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i], -1);

//        System.out.println(mcm(arr, 1, arr.length - 1, dp));

        System.out.println(minPalindromePartition(s, 0, sn - 1, dp));
    }

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
