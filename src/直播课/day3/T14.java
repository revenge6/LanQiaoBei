package 直播课.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 生命之树————树状dp
 */
public class T14 {
    static int n;
    static int[] a;
    static List<List<Integer>> G;
    static long ans=0;
    static long dp[];
    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);

        n= scan.nextInt();
        a=new int[n+1];
        dp=new long[n+1];
        for (int i=0;i<n;i++)
            a[i]= scan.nextInt();
        G=new ArrayList<>();
        for (int i=0;i<n;i++){
            G.add(new ArrayList<>());
        }
        for (int i=0;i<n-1;i++){
            int u=scan.nextInt();
            int v=scan.nextInt();
            G.get(u-1).add(v-1);
            G.get(v-1).add(u-1);
        }
        dfs(0,-1);
        System.out.println(ans);
        scan.close();
    }
    static void dfs(int u,int f){
        dp[u]=a[u];
        for(int v : G.get(u)){
            if(v==f)
                continue;
            dfs(v,u);
            if(dp[v]>=0)
                dp[u]+=dp[v];
        }
        ans=Math.max(ans,dp[u]);
    }
}
