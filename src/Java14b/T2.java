package Java14b;

import java.util.Scanner;

public class T2 {
    static long mod=1000000007;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int T=scan.nextInt();
        while(T-->0){
            int sum=0;
            int N= scan.nextInt();
            int[] a=new int[N+1];
            for (int i=0;i<N;i++){
                a[i]= scan.nextInt();
                sum+=a[i]%2;
            }
            if(sum%2==1){
                System.out.println("0");
            }else {
                System.out.println((int)(Math.pow(2,N-sum)*Math.pow(2,sum==0?0:sum-1)%mod));
            }
        }
        scan.close();
    }
}
