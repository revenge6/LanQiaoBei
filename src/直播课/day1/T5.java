package 直播课.day1;

import java.util.Scanner;

/**
 * 包子凑数
 * 1.裴蜀定理
 */
public class T5 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        //1
        int n;
        n= scan.nextInt();
        int[] a;
        a=new int[n+1];
        a[1]= scan.nextInt();
        int gcd=a[1];
        for (int i=2;i<=n;i++){
            a[i]= scan.nextInt();
            gcd=gcd(gcd,a[i]);
        }
        if(gcd!=1){
            System.out.println("INF");
            return;
        }
        boolean[] dp=new boolean[100001];
        //2
        dp[0]=true;
        for(int i=1;i<=n;i++){
            for(int j=a[i];j<=100000;j++){
                if(dp[j-a[i]]==true)
                    dp[j]=true;
            }
        }
        int count=0;
        for (int i=1;i<=100000;i++){
            if(dp[i]==false)
                count++;
        }
        System.out.println(count);
        scan.close();
    }
    static int gcd(int a,int b){
        return b==0?a:gcd(b,a%b);
    }
}
