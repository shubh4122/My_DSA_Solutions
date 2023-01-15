package DSA;

import java.util.Arrays;

public class DP {
    static int[][] dpMem = new int[1001][1001];//n=1000, w=1000
    public static void main(String[] args) {
        //initializing dp arr with -1, coz max profit cant be -1
        for (int i = 0; i < dpMem.length; i++)
            Arrays.fill(dpMem[i], -1);
    }

    public static boolean isSubsetSum(int n, int arr[], int sum) {
        boolean[][] dp = new boolean[n+1][sum+1];
        //Initialization - row0 -> F, col0 -> T
        Arrays.fill(dp[0], false);//row0 filled
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = true; // col0 filled
        }

        //Choice Diag code
        //NOTE: The loop MUST START FROM 1, 0th row, col have already been taken care of
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (arr[i-1] <= j)
                    dp[i][j] = dp[i-1][j - arr[i-1]] || dp[i-1][j];//MAX replaced by || (or)
                else
                    dp[i][j] = dp[i-1][j];
            }
        }
        return dp[n][sum];
    }

    public static int ksTab(int[] val, int[] wt, int w, int n) {
        int[][] dp = new int[n+1][w+1];
        //BC -> Initialization. Make row(n) = 0 as 0, and col(w)=0 as 0
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < n+1; i++)
            dp[i][0] = 0;

        //Choice Diag
        for (int i = 1; i < n+1; i++) //0,0 are already initialized
            for (int j = 1; j < w+1; j++)
                if (wt[i-1] <= j)
                    dp[i][j] = Math.max(val[i-1] + dp[i-1][j - wt[i-1]], dp[i-1][j]);

                else //if wt[n-1] > w
                    dp[i][j] = dp[i-1][j];

        return dp[n][w];
    }

    public static int ksMem(int[] val, int[] wt, int w, int n) {
        //BC
        if (n == 0 || w == 0)
            return 0;

        //Just Recursive solution Takes too much time. so use MEMOIZATION
        if (dpMem[n][w] != -1)
            return dpMem[n][w];

        //Choice Diag
        if (wt[n-1] <= w)
            return dpMem[n][w] = Math.max(val[n-1] + ksMem(val, wt, w - wt[n-1], n-1),
                    ksMem(val, wt, w, n-1));

        else //if wt[n-1] > w
            return dpMem[n][w] = ksMem(val, wt, w, n-1);
    }
}
