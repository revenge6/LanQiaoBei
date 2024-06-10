package 直播课.day2;

import java.util.Scanner;

public class T12 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int ST=1<<21;
        int g[][]=new int[22][22];
        long dp[][]=new long[ST][22];

        for (int i=1;i<=21;i++){
            for (int j=1;j<=21;j++){
                if(gcd(i,j)==1)
                    g[i-1][j-1]=1;
            }
        }
        dp[1][0]=1;//i为状态 j为楼号，从0开始 dp为i状态下，最后楼号为j的路径数
        for (int i=1;i<ST;i++){
            for (int j=0;j<21;j++){
                if((i>>j & 1)==0)
                    continue;
                for (int k=0;k<21;k++){
                    if(g[j][k]==0||(i>>k & 1)!=0)
                        continue;
                    dp[i+(1<<k)][k]+=dp[i][j];
                }
            }
        }
        long res=0;
        for (int i=0;i<21;i++){
            if(g[i][0]==1)
                res+=dp[ST-1][i];
        }
        System.out.println(res);//881012367360
        scan.close();
    }
    static int gcd(int a,int b){
        return b==0?a:gcd(b,a%b);
    }
}
