package 直播课.day1;

import java.util.Scanner;

/**
 * 完全背包——健身
 */
public class T1 {
    //定义题目变量
    static int n,m,q;
    static int[] t;
    static int[] k;
    static int[] s;
    //每块连续的健身天数————每两个外出日期的时间间隔和最后一个外出时间到最后时间的间隔
    static int[] capacity;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        n= scan.nextInt();
        m= scan.nextInt();
        q= scan.nextInt();

        t=new int[q+2];
        k=new int[m+1];
        s=new int[m+1];
        capacity=new int[q+2];
        for (int i=1;i<=q;i++){
            t[i]= scan.nextInt();
        }
        t[q+1]=n+1;//为了方便计算capicity的最后一个时间间隔而设计

        int ans=0;
        for (int i=1;i< capacity.length;i++){
            capacity[i]=t[i]-t[i-1]-1;
            ans=Math.max(ans,capacity[i]);//找连续健身天数的最大值
        }
        for (int i=1;i<=m;i++){
            k[i]= (int)Math.pow(2,scan.nextInt());
            s[i]= scan.nextInt();
        }
        long sum=0;
        package1(ans);//只需要一次完全背包就能计算出所有的结果
        for (int i=1;i<capacity.length;i++){
            sum+=dp[m][capacity[i]];//根据一次完全背包的结果得出所有时间间隔的最大健身收获
        }
        System.out.println(sum);
        scan.close();
    }
    //一个在最大时间间隔内的完全背包
    static long[][] dp;
    public static void package1(int capicity){
        dp=new long[m+1][capicity+1];
        for (int i=1;i<=m;i++){
            for (int j=1;j<=capicity;j++){
                if(j<k[i])
                    dp[i][j]=dp[i-1][j];//状态转移方程
                else
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-k[i]]+s[i]);//状态转移方程
            }
        }
    }
}
