package 直播课.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/***
 * 金明的预算方案
 *  1.将依赖背包转化为分组背包！
 *  2.使用二维数组还要对跳过的行赋值为上一行！
 *  3.使用一维数组只能从n->0遍历
 */
public class T4 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        int n,m;
        n= scan.nextInt();
        m= scan.nextInt();
        int[][] info;
        info=new int[3][m+1];
        List<Integer>[] G=new ArrayList[65];
        for (int i = 0; i < G.length; i++) {
            G[i] = new ArrayList<>();
        }
        for (int i=1;i<=m;i++){
            info[0][i]= scan.nextInt();//价钱
            info[1][i]= scan.nextInt();//重要度
            info[2][i]= scan.nextInt();//附属
            info[1][i]*=info[0][i];
            if(info[2][i]!=0)
                G[info[2][i]].add(i);
        }
        int[][] dp;
        dp=new int[m+1][n+10];
        //
        for (int i=1;i<=m;i++){
            if(info[2][i]==0)
                for (int j=n;j>=0;j-=10){
                    dp[i][j]=dp[i-1][j];
                    if(j-info[0][i]>=0){
                        dp[i][j]=Math.max(dp[i][j],dp[i-1][j-info[0][i]]+info[1][i]);
//                        System.out.println(j+":"+(dp[i-1][j-info[0][i]]+info[1][i])+" "+i);
                    }
                    if(G[i].size()>0){
                        for (int k=0;k<G[i].size();k++){
                            int temp=G[i].get(k);
                            if(j-info[0][i]-info[0][temp]>=0){
                                dp[i][j]=Math.max(dp[i][j],dp[i-1][j-info[0][i]-info[0][temp]]+info[1][i]+info[1][temp]);
//                            System.out.println(j+":"+(info[1][i]+info[1][temp]));
                            }
                        }
                        if(G[i].size()>1){
                            int p1=G[i].get(0);
                            int p2=G[i].get(1);
                            if(j-info[0][i]-info[0][p1]-info[0][p2]>=0){
                                dp[i][j]=Math.max(dp[i][j],dp[i-1][j-info[0][i]-info[0][p1]-info[0][p2]]+info[1][i]+info[1][p1]+info[1][p2]);
//                            System.out.println(j+":"+(info[1][i]+info[1][p1]+info[1][p2]));
                            }
                        }
                    }
                }
            else{//二维数组跳过的还要赋值为上一行的数据
                for (int j=n;j>=0;j-=10)
                dp[i][j]=dp[i-1][j];
            }
        }
        System.out.println(dp[m][n]);
        scan.close();
    }
}
