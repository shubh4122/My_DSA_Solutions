package DSA;

public class BitManipulation {
    public static void main(String[] args) {
//        System.out.println(iThBitOfNumber(245, 7));
//        evenOddUsingAnd(211);
    }
    

    public static int iThBitOfNumber(int n, int i) {
        //Bit Manipulation
        //Using BIT MASKING!!!!
        //just like we did in odd even, we used num & 1 to find lsb and then concluded odd or even
        //same here, just that we need to & num with 1 followed by i-1 0's. eg. i = 3. use 100
        int mask = 1<<(i-1); // we left shift 1, i-1 times, hence pushing these 0's after 1
        n = n & mask;//extracts the ith bit. rest bits made 0
        return n >> (i - 1);//right shifted to eliminate all right 0's and get ith bit alone!

        //Normal method
//        int ans = 0;
//        for (int j = 0; j < i; j++) {//i times -> here we consider i to be from 0.(indexing)
//            n /= 2;
//        }
//        return n%2;//returning i'th bit
    }

    public static int nonDuplicate(int[] arr) {
        //all elements present 2 times, except 1 elem. find it
        int xor = 0;
        for (int n : arr) {
            xor ^= n;
        }
        return xor;
    }

    public static void evenOddUsingAnd(int n) {
        if ((n & 1) == 1) System.out.println("Odd");
        else              System.out.println("Even");
    }
}
