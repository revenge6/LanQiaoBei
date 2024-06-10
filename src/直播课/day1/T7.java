package 直播课.day1;

import java.util.Scanner;

/**
 * 李白打酒————线性DP
 * 三维数组
 * dp为可能情况数！！！
 */
public class T7 {
    static int mod=(int)1e9+7;//取模
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n,m;
        n= scan.nextInt();
        m= scan.nextInt();
        int[][][] dp=new int[n+1][m+1][m+3];
        dp[0][0][2]=1;//本来2斗酒的总数为1
        dp[0][1][1]=1;//遇1次花1斗酒总数为1
        dp[0][2][0]=1;//遇2次花0斗酒总数为1
        /**
         *  注意这里的i从1开始，为什么？因为i为0的情况中只可能有以上三种情况
         *  我们已经对其赋值为1！
         *  如果你非要想i从0开始，那么只需要dp[0][1][1]=0;dp[0][2][0]=0;
         */
        for(int i=1;i<=n;i++){
            for (int j=0;j<=m;j++){
                for (int k=0;k<=m;k++){
                    if(k%2==0){
                        if(j>0)
                        dp[i][j][k]+=dp[i][j-1][k+1];
                        if(i>0)
                        dp[i][j][k]+=dp[i-1][j][k/2];
                    }else {
                        if(j>0)dp[i][j][k]+=dp[i][j-1][k+1];
                    }
                    dp[i][j][k]%=mod;
                }
            }
        }
        System.out.println(dp[n][m-1][1]);
        scan.close();
    }
}
