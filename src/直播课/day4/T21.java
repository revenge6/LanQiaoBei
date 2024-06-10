package 直播课.day4;

import java.util.Scanner;

/**
 * Windy数
 */
public class T21 {
    static int[][][] dp;//dp[i][j][k] i,处理到第i行 j i-1位数 m k 0/1 是否触顶
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        char[] l=scan.next().toCharArray();
        char[] r=scan.next().toCharArray();
        int[] s1=new int[l.length+1];
        int[] s2=new int[r.length+1];
        for (int i=0;i<l.length;i++){
            s1[i+1]=l[i]-'0';
        }
        for (int i=0;i<r.length;i++){
            s2[i+1]=r[i]-'0';
        }
        long ansr=0;
        long ansl=0;
        ansr=ansr+DP(s2);
        ansl=ansl+DP(s1);
        System.out.println(ansr-ansl+chk(s1));
        scan.close();
    }
    static int chk(int[] l){
        int a;
        a=l[0];
        int n=l.length;
        if(n==2)
            return 1;
        for (int i=1;i<n;i++){
            if(Math.abs(l[i]-a)<2){
                return 0;
            }
            a=l[i];
        }
        return 1;
    }
    static long DP(int[] a) {
        int n = a.length - 1;
        dp = new int[a.length][10][2];

        for (int i = 1; i < a[1]; i++) {
            dp[1][i][0] = 1;
        }
        dp[1][a[1]][1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int ss = 1; ss <= 9; ss++)
                dp[i][ss][0]++;
            for (int j = 0; j <= 9; j++) {

                for (int ss = 0; ss <= 9; ss++) {
                    if (Math.abs(j - ss) < 2)
                        continue;
                    dp[i][ss][0] += dp[i - 1][j][0];
                    if (ss < a[i])
                        dp[i][ss][0] += dp[i - 1][j][1];
                    if (ss == a[i])
                        dp[i][ss][1] += dp[i - 1][j][1];
                }

            }
        }
        int sum = 0;
        for (int i = 0; i <= 9; i++) {
            sum += dp[n][i][0];
            sum += dp[n][i][1];
        }
        return sum;
    }
}
