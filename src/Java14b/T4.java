package Java14b;

import java.util.Scanner;

/**
 * 蜗牛————动态规划
 */
public class T4 {

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        int n= scan.nextInt();
        int x[]=new int[n+1];
        for (int i=1;i<=n;i++){
            x[i]= scan.nextInt();
        }
        int[] a=new int[n+1];
        int[] b=new int[n+1];
        for (int i=1;i<n;i++){
            a[i]= scan.nextInt();
            b[i+1]= scan.nextInt();
        }
        double dp[][]=new double[n+1][2];
        //dp[i][j]到达第i个竹竿，在入传送门的最短时间/在竹竿厎部的最短时间
        dp[1][0]=x[1];
        dp[1][1]=x[1]+a[1]/0.7;
        for (int i=2;i<=n;i++){
            dp[i][0]=Math.min(dp[i-1][0]+x[i]-x[i-1],dp[i-1][1]+b[i]/1.3);
            if(b[i]>=a[i])
                dp[i][1]=Math.min(dp[i][0]+a[i]/0.7,dp[i-1][1]+(b[i]-a[i])/1.3);
            else
                dp[i][1]=Math.min(dp[i][0]+a[i]/0.7,dp[i-1][1]+(a[i]-b[i])/0.7);
        }
        System.out.printf("%.2f",dp[n][0]);
        scan.close();
    }
}
