package 直播课.day1;

import java.io.IOException;
import java.util.Scanner;

public class T3 {
    public static void main(String[] args) throws IOException {
        Scanner scan=new Scanner(System.in);
        int n,k;
        n= scan.nextInt();
        k= scan.nextInt();
        int[] a,p;
        a=new int[n+1];
        p=new int[k+1];
        for (int i=1;i<=n;i++){
            a[i]= scan.nextInt();
        }
        for (int i=1;i<=k;i++){
            p[i]= scan.nextInt();
        }
        long[][] dp;
        dp=new long[n+1][k+1];
        int now=0;
        int pre=1;
        for (int i=0;i<=k;i++){
            dp[0][i]=Long.MIN_VALUE;
            dp[1][i]=Long.MIN_VALUE;
        }
        dp[0][0]=0;
        for (int i=1;i<=n;i++){
            now^=1;
            pre^=1;
            //不需要对now这行全部更新！
//            for (int j=0;j<=k;j++){
//                dp[now][j]=-8000000000000000000L;
//            }
            dp[now][0]=-8000000000000000000L;
            for(int j=1;j<=k;j++){
                dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-1])+1L*a[i]*p[j];
            }
        }
        System.out.println(dp[n][k]);
        scan.close();
    }
}
