package 直播课.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 小明的背包6————树上背包
 */
public class T15 {
    static int N,V;
    static int[] w,v;
    static List<List<Integer>> G;//邻接表
    static int[][] dp;//dp[i][j]代表以i为根的子树，体积为j时的最大价值
    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);
        N= scan.nextInt();
        V= scan.nextInt();
        w=new int[N+1];
        v=new int[N+1];
        G=new ArrayList<>();
        for (int i=0;i<=N;i++){
            G.add(new ArrayList<>());//初始化多一个以0为根，其目的是将无依赖关系的森林中的每个树关系起来
        }
        for (int i=1;i<=N;i++){
            v[i]= scan.nextInt();
            w[i]= scan.nextInt();
            G.get(scan.nextInt()).add(i);//建立依赖关系树
        }
        dp=new int[N+1][V+1];
        dfs(0);//从我们创建的真根结点开始
        System.out.println(dp[0][V]);
        scan.close();
    }
    static void dfs(int u){
        for (int i=v[u];i<=V;i++){
            dp[u][i]=w[u];//将选择根节点的dp赋初始值
        }
        for (int child:G.get(u)){
            dfs(child);//递归孩子
            for (int j=V;j>=v[u]+v[child];j--){//为什么从最大的V开始？应该因为为0-1背包，一种组合只能选择一次~
                for (int k=v[child];k<=j-v[u];k++){//分给子树的体积
                    dp[u][j]=Math.max(dp[u][j],dp[u][j-k]+dp[child][k]);//很简答的递归方程
                }
            }
        }
    }
}
