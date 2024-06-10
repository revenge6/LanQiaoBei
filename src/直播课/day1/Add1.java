package 直播课.day1;

import java.util.Scanner;

public class Add1 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int T= scan.nextInt();
        while (T-->0){
            //输入
            int N= scan.nextInt();
            int A= scan.nextInt();
            int B= scan.nextInt();
            int time= scan.nextInt();
            int[][] dp=new int[time+1][N+1];
            int[] in=new int[N+1];
            for (int i=1;i<=N;i++){
                in[i]= scan.nextInt();
            }

            int max=0;
            for(int i=1;i<=time;i++){
                for (int j=A*i;j<=N&&j<=i*B;j++){
                    int maxofbefore=0;
                    for (int k=A;k<=B;k++){
                        if(j-k>=0&&maxofbefore<dp[i-1][j-k])
                            maxofbefore=dp[i-1][j-k];
                    }
                    dp[i][j]=maxofbefore+in[j];
                    if(max<dp[i][j])
                        max=dp[i][j];
                }
            }
            System.out.println(max);
        }
        scan.close();
    }
}
