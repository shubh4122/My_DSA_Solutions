package DSA;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DP_MCM {
    public static void main(String[] args) {
        int[] arr = {40, 20, 30, 10, 30};
        int[][] dp = new int[arr.length+1][arr.length+1];
        for (int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i], -1);

        System.out.println(mcm(arr, 1, arr.length - 1, dp));
    }

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
