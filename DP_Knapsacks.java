package DSA;

import java.util.Arrays;

/*
NOTE : Many Codes below will have direct Top Down(Tabulation) Approach.
       That is because they were similar to some other prev prob so just copied the method.
       BUT if some new problem is encountered. always try writing recursive code, and then MEMOIZING it!!!
 */
public class DP_Knapsacks {
    static int[][] dpMem = new int[10001][10001];//n=10000, w=10000
    public static void main(String[] args) {
        //initializing dp arr with -1, coz max profit cant be -1
        for (int i = 0; i < dpMem.length; i++)
            Arrays.fill(dpMem[i], -1);

        int[] arr = {25, 10, 5};//{2,5,3,6};//{1,1,2,3};//{1, 4} ;//{2, 3, 5, 6, 8, 10};
        int sum = 30;
        int diff = 1;
//        System.out.println(countSubsetsMem(arr, arr.length, sum, dpMem));
//        System.out.println(subsetPartitionMinSumDifference(arr, arr.length));
//        System.out.println(waysToPartitionSubsetsForGivenDifference(arr.length, diff, arr));
//        System.out.println(coinChange1(arr, arr.length, sum));
//        System.out.println(coinChange2(arr, arr.length, sum));
//        System.out.println(coinChange2Tabulation(arr, arr.length, sum));
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int coinChange2TabulationSPACE_OPTIMISED(int[] coins, int n, int sum) {
        //Using 2 1D arr instead of dp[][]. SAVES A LOT OF SPACE!
        int[] prevRow = new int[sum+1];// At initialization. This is row0
        int[] currRow = new int[sum+1];// At initialization. This is row1

        //Initialization - BC
        Arrays.fill(prevRow, Integer.MAX_VALUE - 1);//filling 0th row
        //Instead of filling 1st col, here we'll fill only 1st cell.
        prevRow[0] = 0;
    //        Make changes according to new arrays!!
    //        for (int j = 1; j < sum + 1; j++) {
    //            if (j % coins[0] == 0)    dp[1][j] = j / coins[0];
    //            else                      dp[1][j] = Integer.MAX_VALUE - 1;
    //        }

        //MAIN CODE

        for (int i = 1; i < n + 1; i++) {// i = 2 when 3rd BC is used
            for (int j = 1; j < sum + 1; j++) {
                //All ...[i][*] -> curr[*]
                //all ...[i-1][*] -> prev[*]
                if (coins[i-1] <= j)
                    currRow[j] = Math.min(1+currRow[j-coins[i-1]], prevRow[j]);
//                    dp[i][j] = Math.min(1+dp[i][j - coins[i-1]], dp[i-1][j]);

                else
                    currRow[j] = prevRow[j];
//                    dp[i][j] = dp[i-1][j];
            }
            //updating prev after current row done.
            prevRow = currRow;
        }

        return currRow[sum];
//        return dp[n][sum];
    }

    public static int coinChange2Tabulation(int[] coins, int n, int sum) {
        int[][] dp = new int[n+1][sum+1];
        //Initialization - BC
        Arrays.fill(dp[0], Integer.MAX_VALUE - 1);
        for (int i = 0; i < n + 1; i++)
            dp[i][0] = 0;
//        CAN ALSO traverse arr and Fill 3rd BC val too, but it works without that too. so leaving it
//        for (int j = 1; j < sum + 1; j++) {
//            if (j % coins[0] == 0)    dp[1][j] = j / coins[0];
//            else                      dp[1][j] = Integer.MAX_VALUE - 1;
//        }

        //MAIN CODE
        for (int i = 1; i < n + 1; i++) {// i = 2 when 3rd BC is used
            for (int j = 1; j < sum + 1; j++) {
                if (coins[i-1] <= j)
                    dp[i][j] = Math.min(1+dp[i][j - coins[i-1]], dp[i-1][j]);

                else
                    dp[i][j] = dp[i-1][j];
            }
        }

        return dp[n][sum];
    }

    //Min num of coins for sum upto given sum
    public static int coinChange2(int[] coins, int n, int sum) {
        //HYPOTHESIS of this method : --> V IMP
        //  It returns the Min Number of coins req to give sum.

        //BC
        if (n == 0 && sum != 0) return Integer.MAX_VALUE - 1; //because when no possible way, then num of coins UNDEFINED
        if (sum == 0)   return 0;
        // seee if any other bc?
        //NOTE n-1 = 0 here always. so we can also write coins[0]
        //if (n == 1) return sum % coins[n-1] == 0 ? sum/coins[n-1] : Integer.MAX_VALUE -1;

        //Memoization
        if (dpMem[n][sum] != -1)
            return dpMem[n][sum];

        //Choice diag
        if (coins[n-1] <= sum)
            return dpMem[n][sum] = Math.min(1+coinChange2(coins, n, sum - coins[n-1]), coinChange2(coins, n-1, sum));
        else
            return dpMem[n][sum] = coinChange2(coins, n-1, sum);

//        https://practice.geeksforgeeks.org/problems/number-of-coins1824/1
//        https://leetcode.com/problems/coin-change/
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Max no. of ways QUESTION
    public static int coinChange1(int[] coins, int n, int sum) {
        //USE LONG instead of int for COUNT. That is return type -> long, dpMem type -> long --- IN GFG. In LC its correct
        //same as countSubsets--> with given sum. Just that it'll be unbounded here
        //BC
        if (sum == 0) return 1; // Empty set. i.e. No coins can sum upto 0
        if (n == 0)   return 0; // this will execute when sum != 0

        //Memoization
        if (dpMem[n][sum] != -1)
            return dpMem[n][sum];

        //Main Code - Choice Diagram
        if (coins[n-1] <= sum)
            //return Take(don't decrease coin array size coz UNBOUNDED, i.e. pass call(n) and not n-1) + Not take
            return dpMem[n][sum] = coinChange1(coins, n, sum - coins[n-1]) + coinChange1(coins, n-1, sum);

        else
            return dpMem[n][sum] = coinChange1(coins, n-1, sum);



        //https://practice.geeksforgeeks.org/problems/coin-change2448/1?utm_source=gfg&utm_medium=article&utm_campaign=bottom_sticky_on_article
        // https://leetcode.com/problems/coin-change-ii/description/

    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int rodCutting(int price[], int n) {
        /*
            Total inputs are : price[], n, and length[](not given in parameters, coz we can derive it ourselves
            Fill length[] from 1 to n

            Similarity:
            price[] --> val[]
            length[] --> wt[]
            n --> W (coz n is the Total length of ROD, and we need to add up all the piece len to n(total len))

            HENCE, this is Knapsack.
            But, we can cut rod into same length PIECES.
            That is we can use same length[i](and hence price[i]) many number of times.
            HENCE, this is an example of UNBOUNDED KS.!!
         */
        int[] length = new int[n];
        for (int i = 0; i < n; i++)
            length[i] = i+1;
        //Now we have 3 I/Ps. len, price, n

        int maxPrice = unboundedKnapsack(price, length, n, n);
        return maxPrice;
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int unboundedKnapsack(int[] val, int[] wt, int n, int w) {
        int[][] dp = new int[n+1][w+1];
        //Initialisation
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < n + 1; i++)
            dp[i][0] = 0;

        //Choice Diag
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < w + 1; j++) {
                //Max of (Taken(hence Unprocessed, so dp[i][...]), Not taken(hence Processed, so dp[i-1][j]))
                if (wt[i-1] <= j)
                    dp[i][j] = Math.max(val[i-1] + dp[i][j - wt[i-1]], dp[i-1][j]);

                else
                    dp[i][j] = dp[i-1][j];
            }
        }

        return dp[n][w];
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int targetSumLeetcode(int[] arr, int targetSum) {
        //Because target was from -1000 to 1000. And if we can get +n target, we can definitely get -n by just flipping S1 and S2 in substraction
        targetSum = Math.abs(targetSum);
        return waysToPartitionSubsetsForGivenDifference(arr.length, targetSum, arr);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int waysToPartitionSubsetsForGivenDifference(int n, int diff, int[] arr) {
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += arr[i];

        if((sum+diff)%2 != 0) return 0;

        int sumSubset1 = (sum+diff)/2;//Owing to 2 equations
        //now count how many subsets are there with this sum
        return countSubsetTabulationMY(arr, n, sumSubset1);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int subsetPartitionMinSumDifference(int[] arr, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += arr[i];

        int halfSum = sum/2;
        //We can also use maxSumNearestToHalfSum. that works exactly like knapsack
        int sumPartition1 = ksMem(arr, arr, halfSum, n);
        int sumPartition2 = sum - sumPartition1;

        return Math.abs(sumPartition2 - sumPartition1);
    }

    //This function returns SUM nearest to given sum
    private static int maxSumNearestToHalfSum(int[] arr, int n, int sum) {
        //BC
        if (sum == 0 || n == 0)   return 0;

        /*
            M1 : Again write code similar to KnapSack.
            or
            M2 : Call knapsackMemoization with given values
         */

//        M1:
//        Memoization
        if (dpMem[n][sum] != -1)
            return dpMem[n][sum];

//        CODE(Choice Diag)
//        Just like knapsack, Return Max Sum(profit there) nearest to Given SUM(W there)
        if (arr[n-1] <= sum)
            return dpMem[n][sum] = Math.max(arr[n-1] + maxSumNearestToHalfSum(arr, n-1, sum - arr[n-1]),
                                            maxSumNearestToHalfSum(arr, n-1, sum));

        else return dpMem[n][sum] = maxSumNearestToHalfSum(arr, n-1, sum);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //This is MY tabulation code, which is written by CONVERTING MY memoization code
    public static int countSubsetTabulationMY(int[] arr, int n, int sum) {
        int[][] dp = new int[n+1][sum+1];
        //BC
        Arrays.fill(dp[0], 0);
        dp[0][0] = 1;

        for (int i = 1; i < n + 1; i++)
            for (int j = 0; j < sum + 1; j++)
                if (arr[i-1] <= j)
                    //count of subsets equal to given sum, when including (n-1)th item in arr
                    //+
                    // count of subsets whose sum = given sum, when not including (n-t)th elem
                    dp[i][j] = mod1000000007(dp[i-1][j - arr[i-1]] + dp[i-1][j]);

                else    dp[i][j] = dp[i-1][j];


        return dp[n][sum];
    }

    //this function returns count of subsets from arr of size n, whose sum = given sum
    public static int countSubsetsMem(int[] arr, int n, int sum, int[][] dpMem) {
        //BC
        if (n == 0 && sum != 0)
            return 0; //No subset possible. hence count = 0

        if (n == 0 && sum == 0)
            return 1;//empty set possible

        //Memoization
        if (dpMem[n][sum] != -1)
            return dpMem[n][sum];

        //Main code (Choice Diag)
        if (arr[n-1] <= sum)
            return dpMem[n][sum] = mod1000000007(countSubsetsMem(arr, n-1, sum - arr[n-1], dpMem) //count of subsets equal to given sum, when including (n-1)th item in arr
                                +
                    countSubsetsMem(arr, n - 1, sum, dpMem)); // count of subsets whose sum = given sum, when not including (n-t)th elem

        else    return dpMem[n][sum] = mod1000000007(countSubsetsMem(arr, n - 1, sum, dpMem));
    }

    public static int countSubsetsTabulation(int[] arr, int n, int sum) { //count subsets with given sum
        int[][] dp = new int[n+1][sum+1];
        int count0 = 0;

        //Initialization - row0 -> F = 0, col0 -> T = 1
        Arrays.fill(dp[0], 0);//row0 filled
        dp[0][0] = 1;
        for (int i = 1; i < n + 1; i++) {
            if (arr[i-1] == 0)  count0++;
            dp[i][0] = (int)Math.pow(2, count0); // col0 filled
        }

        //Choice Diag code
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (arr[i-1] <= j)
                    dp[i][j] = mod1000000007(dp[i - 1][j - arr[i - 1]] + dp[i - 1][j]);//||(or) replaced by +
                else
                    dp[i][j] = dp[i-1][j];
            }
        }
        return mod1000000007(dp[n][sum]);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int equalPartition(int n, int arr[]) {
        //Return 1. For true.
        //Step 1: Find Total sum of arr
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }

        //Step 2: If sum - Odd, then no partition can be done, If Even - partition MAY BE possible
        if (sum%2 != 0) //odd sum
            return 0;   //Partition NOT possible

        /*
            We can Directly call subset sum function with sum/2 ALSO!!!
            OR
            write its code again!
         */

        //Step 3: Now Start DP.
        return isSubsetSum(n, arr, sum/2) ? 1 : 0;
//        boolean[][] dp = new boolean[n+1][(sum/2)+1];
//        //Initialization
//        Arrays.fill(dp[0], false);//row0 filled
//        for (int i = 0; i < n + 1; i++)
//            dp[i][0] = true; // col0 filled
//
//        //Choice Diag code ---------- BE VERY CAREFULLLLL!!!!  i and j start from 1 !!!!!!!!!!!!!!
//        for (int i = 1; i < n + 1; i++) {
//            for (int j = 1; j < (sum / 2) + 1; j++) {
//                if (arr[i-1] <= j)
//                    dp[i][j] = dp[i-1][j - arr[i-1]] || dp[i-1][j];
//
//                else
//                    dp[i][j] = dp[i-1][j];
//            }
//        }
//        return dp[n][sum/2] ? 1 : 0;
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static boolean isSubsetSum(int n, int arr[], int sum) {
        boolean[][] dp = new boolean[n+1][sum+1];
        //Initialization - row0 -> F, col0 -> T
        Arrays.fill(dp[0], false);//row0 filled
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = true; // col0 filled
        }

        //Choice Diag code ---------- BE VERY CAREFULLLLL!!!!  i and j start from 1 !!!!!!!!!!!!!!
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
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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

    //This function returns the Max Profit that can be done while filling W wt.
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
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    public static int mod1000000007(long n) {
        return (int)(n%(1e9+7));
    }
}
