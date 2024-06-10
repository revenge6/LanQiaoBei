package 直播课.day2;

import java.util.Scanner;

/**
 * 积木画————状压dp
 *
 */
public class T10 {
    static int mod=(int)1e9+7;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n;
        n= scan.nextInt();
        int[][] dp;//i列
        dp=new int[n+1][3];//处理好n列时状态为0/1/2的可能数量
        dp[0][0]=1;
        for (int i=1;i<=n;i++){
            dp[i][0]=dp[i-1][0];//上一行满，竖着放一个I
            dp[i][0]=(dp[i][0]+dp[i-1][1])%mod;//上一行上面不满，用一个L
            dp[i][0]=(dp[i][0]+dp[i-1][2])%mod;//上一行下面不满，用一个L
            if(i>=2){
                dp[i][0]=(dp[i][0]+dp[i-2][0])%mod;//特殊情况，前两行是两个I横放
            }
            if(i>=2){
                dp[i][1]=dp[i-2][0];//上一行满，用一个L，上面空
                dp[i][2]=dp[i-2][0];//上一行满，用一个L，下面空
            }
            dp[i][1]=(dp[i][1]+dp[i-1][2])%mod;//上一行下面不满，用一个I横放
            dp[i][2]=(dp[i][2]+dp[i-1][1])%mod;//上一行上面不满，用一个I横放
        }
        System.out.println(dp[n][0]);//
        scan.close();
    }
}
