package 直播课.day3;

import java.util.*;

public class T13 {
    static int N=5050;

    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);

        List<Integer>[] g=new ArrayList[N];
        for (int i=0;i<N;i++) {
            g[i]=new ArrayList<>();
        }
        int[] a=new int[N];
        int[] b=new int[N];
        int[] sz=new int[N];
        int[][] dp=new int[N][N];
        int[] tmp=new int[N];

        int n= scan.nextInt();
        int w= scan.nextInt();

        for (int i=1;i<=n;i++){
            a[i]= scan.nextInt();
        }
        for (int i=0;i<=n;i++)
        Arrays.fill(dp[i],Integer.MAX_VALUE/2);

        b[1]=Integer.MAX_VALUE/2;
        for (int i=2;i<=n;i++){
            int fa= scan.nextInt();
            int cost= scan.nextInt();
            b[i]=cost;
            g[fa].add(i);
        }
        dfs(g,a,b,sz,dp,tmp,1);
        for (int i=n;i>=0;i--){
            if(dp[1][i]<=w){
                System.out.println(i);
                break;
            }
        }
        scan.close();
    }
    static void dfs(List<Integer>[] g,int[] a,int[] b,int[] sz,int[][] dp,int[] tmp,int u){
        sz[u]=1;
        dp[u][0]=0;
        for (int v:g[u]){
            dfs(g,a,b,sz,dp,tmp,v);
            for (int i=1;i<=sz[u]+sz[v];i++){
                tmp[i]=Integer.MAX_VALUE;
            }
            for (int j=0;j<=sz[u];j++){
                for (int k=0;k<=sz[v];k++){
                    tmp[j+k]=Math.min(tmp[j+k],dp[u][j]+dp[v][k]);
                }
            }
            sz[u]+=sz[v];
            for (int j=1;j<=sz[u];j++){
                dp[u][j]=tmp[j];
            }
        }
        dp[u][sz[u]-1]=Math.min(dp[u][sz[u]-1],a[u]);
        dp[u][sz[u]]=Math.min(dp[u][sz[u]],b[u]);
    }
}
