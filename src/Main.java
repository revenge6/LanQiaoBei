import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int sum=scan.nextInt();
        int[] a=new int[n+1];
        for(int i=1;i<=n;i++){
            a[i]= scan.nextInt();
        }
        tanxin(n,sum,a);
        Dp(n,sum,a);
        scan.close();
    }
    static void Dp(int n,int sum,int[] a){
        int dp[][]=new int[n+1][sum+1];
        //dp[i][j] 到第i面额金币前，凑出j元的 最少张数
        for(int i=0;i<=n;i++)
            Arrays.fill(dp[i],Integer.MAX_VALUE/2);
        for(int i=1;i<=n;i++)
            dp[i][a[i]]=1;
        for (int i=0;i<=n;i++)
            dp[i][0]=0;
        for (int i=1;i<=n;i++){
            for (int j=0;j<=sum;j++){
                if(j<a[i])
                    dp[i][j]=dp[i-1][j];
                else
                    dp[i][j]=Math.min(dp[i-1][j],Math.min(dp[i-1][j-a[i]]+1,dp[i][j-a[i]]+1));
            }
        }
        StringBuilder st=new StringBuilder(sum+"=");
        int x=n;
        int y=sum;
        while (sum>0){
            if(dp[x][y]==dp[x-1][y]){
                x--;
            }else{
                sum-=a[x];
                st.append(a[x]+"+");
                y-=a[x];
            }
        }
        System.out.println(st.substring(0,st.length()-1));
    }
    static void tanxin(int n,int sum,int a[]){
        StringBuilder st=new StringBuilder(sum+"=");
        int []ans=new int[n+1];//代表
        int ip=n;
        while (sum>0){
            int times=0;
            if(sum>=a[ip]){
                times=sum/a[ip];
                sum-=times*a[ip];
                ans[ip]+=times;
            }else{
                ip--;
            }
        }

        for (int i=n;i>=0;i--){
            while (ans[i]>0){
                st.append(a[i]+"+");
                ans[i]--;
            }
        }
        System.out.println(st.substring(0,st.length()-1));
    }
}