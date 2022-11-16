package DSA;

import java.util.Arrays;
import java.util.Scanner;

public class POTD_GFG {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String S = "gmhgjldoocyfsmjqpsenllxuftcrfnnnzxukizaycbeuppmejqtwbrqimscthphjmdtxcwvgxbcosqsbhnxkgntsixmpovydauacsxlpbndtgxvnmuztkulurxjiukluglzykkplzuhftcufwwyiqmcklosgydagqbgclxontvumzrtxnugdgiprwhzwmcecdkfrhvecsaorrkqeeykkhzdfhdcthgyktdbcaffsgvlzfbelaoxjndowisspbqzuvbyvifnqbaqibwwdktmzwdygvqvwivsdytagypzbprksniwxbkyapwgloejwzebzxbfxqgzhxlaktyjwkkyaifnxlyvkcxjbaqbtzaawlajgasclcblniyktyhfdgqehihahhceudpadhfqmibzqbllasqdzgjiorkwamcxrrxxaenompncsanstfvulhccamaaaexsvxpxbcmqrzukckevpcrdjvfjhhlhljzgiqfltrbmtwyvidsxfjaqehbnomwzxyhhonthewuzuuucxmaexcwbmzqaonclljvbyojfmfehzzjznjflocoabf";
        String W = "dfvfdvd";

        System.out.println(numberOfSubsequences(S, W));


//        int n = sc.nextInt();
//        int[] capacity = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            capacity[i] = sc.nextInt();
//        }
//
//        System.out.println(totalWays(n, capacity));
    }

//      Input is in desktop

//    Your Code's output is:
//            5
//    It's Correct output is:
//            1
    static int numberOfSubsequences(String S, String W){
        // code here
        int k = 0, count = 0;
        for (int i = 0; i < S.length(); i++) {
            k = 0;
            for (int j = i; j < S.length(); j++) {
                if (S.charAt(j) == W.charAt(k)){
                    System.out.println(j + "     " + S.charAt(j) + "     " + W.charAt(k));
                    k++;
                    S = S.substring(0, j) + S.substring(j+1);
                    j--;
                    System.out.println(S);
                    System.out.println("---------------------------------------");
                }

                if (k == W.length()){
                    count++;
                    k = 0;
                    System.out.println(count);
                    System.out.println("++++++++++++++++++");
                }
            }
        }
        return count;
    }

    public static int totalWays(int n, int[] capacity) {
        // code here
        Arrays.sort(capacity);
//      Just keep it long, it will work. Making it int will give an error!!!!!
        long ans = 1l;

        for(int i = 0; i < n; i++) {
//          Modulo done coz resulting answer may be too big!!!
            ans = (ans * (capacity[i] - i))%1000000007;
        }

        return (int) ans;//%1000000007
    }
}
