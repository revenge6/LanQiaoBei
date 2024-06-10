package 直播课.day4;

import java.util.Scanner;

/**
 * 神奇树————数位DP
 * dp[i][j][k] i: 处理到第几位； j: 看情况这里是前i位的和对末尾取模； k 0/1 0代表没触顶，1代表触顶
 */
public class T18 {
    static long mod=998244353;
    static long[][][] dp;

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        char[] l=scan.next().toCharArray();
        char[] r=scan.next().toCharArray();

        long ansr=0;
        long ansl=0;
        for (int sn=1;sn<=9;sn++){
            ansr=(ansr+DP(r,sn))%mod;
            ansl=(ansl+DP(l,sn))%mod;
        }
        System.out.println(((ansr-ansl+chk(l))%mod+mod)%mod);
        scan.close();
    }
    static int chk(char[] l){
        int sum=0;
        int n=l.length;
        if(l[n-1]-'0'==0)
            return 0;
        for (int i=0;i<n-1;i++){
            sum=(sum+l[i]-'0')%(l[n-1]-'0');
        }
        return sum==0?1:0;
    }
    static long DP(char[] a,int sn){
        int n=a.length;
        dp=new long[n+1][12][2];
        dp[0][0][1]=1;
        int l=0,r=9;
        for (int i=1;i<=n;i++){//枚举位数
            for (int j=0;j<sn;j++){//枚举i-1位的值
                if(i==n){
                    l=sn;
                    r=sn;
                }
                for (int ss=l;ss<=r;ss++){//枚举第i位的值
                    //未触顶到未触顶 因为转移状态的上一位未触顶，所以后面必然也未触顶，则不需要判断
                    dp[i][(j+ss)%sn][0]+=dp[i-1][j][0];
                    dp[i][(j+ss)%sn][0]%=mod;
                    //触顶到未触顶
                    if(ss<a[i-1]-'0'){
                        dp[i][(j+ss)%sn][0]+=dp[i-1][j][1];
                        dp[i][(j+ss)%sn][0]%=mod;
                    }
                    //触顶到触顶
                    if(ss==a[i-1]-'0'){
                        dp[i][(j+ss)%sn][1]+=dp[i-1][j][1];
                        dp[i][(j+ss)%sn][1]%=mod;
                    }
                }
            }
        }

        return (dp[n][0][0]+dp[n][0][1])%mod;//将触顶和未触顶的值的和取模返回
    }
}
