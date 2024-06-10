package 搜索算法;

import java.util.Scanner;

public class T1 {
    static int N;
    static int ans=0;
    static int dp[];
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        N= scan.nextInt();
        dp=new int[N+1];
        dfs(1);
        System.out.println(ans);
        scan.close();
    }
    static void dfs(int n){
        if(n==N+1){
            ans++;
            return;
        }
        for (int i=1;i<=N;i++){
            dp[n]=i;
            if(pd(n))
                dfs(n+1);
        }
    }
    static boolean pd(int i){
        for (int j=1;j<i;j++){
            if(Math.abs(i-j)==Math.abs(dp[i]-dp[j])||dp[i]==dp[j])
                return false;
        }
        return true;
    }
}
