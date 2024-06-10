package 练习.动态规划;

import java.util.Scanner;

public class LCS {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        int n,m;
        n= scan.nextInt();
        m= scan.nextInt();
        int[] a,b;
        a=new int[n+1];
        b=new int[m+1];
        for (int i=1;i<=n;i++)
            a[i]= scan.nextInt();
        for (int i=1;i<=m;i++)
            b[i]= scan.nextInt();
        int[][] dp=new int[n+1][m+1];
        for (int i=1;i<=n;i++){
            for (int j=1;j<=m;j++){
                if(a[i]==b[j])
                    dp[i][j]=dp[i-1][j-1]+1;
                else
                    dp[i][j]=Math.max(Math.max(dp[i-1][j],dp[i][j-1]),dp[i-1][j-1]);
            }
        }
        /**
         * 1 2 3 4 5
         * 2 1 3 4 5
         */
        System.out.println(dp[n][m]);
        scan.close();
    }
}
