package 直播课.day2;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 星球————状压dp
 *
 */
public class T9 {
    static double[][] dis;
    static double[][] dp;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n;
        n= scan.nextInt();
        dp=new double[1<<n][n+1];
        dis=new double[n+1][n+1];
        int[] x,y,z,w;
        x=new int[n+1];
        y=new int[n+1];
        z=new int[n+1];
        w=new int[n+1];
        int ST=1<<n;
        for (int i=0;i<n;i++){
            x[i]= scan.nextInt();
            y[i]= scan.nextInt();
            z[i]= scan.nextInt();
            w[i]= scan.nextInt();
        }
        for (int i=0;i<n;i++)
            for (int j=0;j<=i;j++)
                dis[i][j]=dis[j][i]=dis(x[i],y[i],z[i],x[j],y[j],z[j]);

        for(double[] temp :dp)
        Arrays.fill(temp,0x3f3f3f3f);
        for (int i=0;i<n;i++)
            dp[(1<<i)][i]=0;
        for (int i=0;i<ST;i++){//列举状态
            for (int j=0;j<n;j++){
                if((i>>j&1)==1){//j为待攻击星球，筛选i中有j的状态
                    for (int k=0;k<n;k++){//列举最后所在星球
                        if((i>>k&1)==1)//当前状态i下有k
                            //选择当前状态和当前状态除去j后加上k到j的进攻代价的最小值
                            dp[i][j]=Math.min(dp[i][j],dp[i-(1<<j)][k]+dis[k][j]*w[j]);
                    }
                }
            }
        }
        double res=0x3f3f3f3f;
        int t=(1<<n)-1;//最终态
        for (int i=0;i<n;i++){
            res=Math.min(dp[t][i],res);//最终态下最后进攻的星球中代价最小的为答案
        }
        System.out.printf("%.2f",res);//格式化输出
        scan.close();
    }
    static double dis(int x1,int y1,int z1,int x2,int y2,int z2){
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)+Math.pow(z2-z1,2));
    }
}
