package 直播课.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class T17 {
    static List<Integer>[] g;//邻接表结构~
    static int[] dp;//记录以当前节点为根的子树在题目要求下的最大值
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int N= scan.nextInt();
        g=new ArrayList[N+1];
        dp=new int[N+1];
        for (int i=0;i<=N;i++){
            g[i]=new ArrayList<>();
        }
        for (int i=2;i<=N;i++){
            int fa= scan.nextInt();
            g[fa].add(i);//将第i个节点归于其父亲的邻接表那行
        }
        dfs(1);
        System.out.println(dp[1]);//以1为根的子树在题目要求下的最大高度
        scan.close();
    }
    static void dfs(int u){
        int ma=0;
        for(int v:g[u]){//遍历所有儿子
            dfs(v);
            ma=Math.max(ma,dp[v]);//儿子中高度最大的值
            //System.out.println("=="+"ma="+ma+" dp["+v+"]="+dp[v]);
        }
        dp[u]=ma+g[u].size();//dp为儿子中最大值加上u节点下所有儿子节点个数
        //System.out.println("u="+u+" dp[u]="+dp[u]+" ma="+ma+" g[u].size()="+g[u].size());
    }
}
