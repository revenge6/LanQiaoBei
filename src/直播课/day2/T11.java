package 直播课.day2;

import java.util.Scanner;

public class T11 {
    static int mod=998244353;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        int n= scan.nextInt();
        int a[]=new int[n+1];
        for(int i=1;i<=n;i++)
            a[i]= scan.nextInt();
        int ST=1<<3;
        int[] num=new int[ST];
        for (int i=0;i<ST;i++){
            num[i]=numof1(i);//8中状态的1数量
        }
        int dp[][][] = new int[n+1][ST][ST];
        dp[0][0][0]=1;//状态位第一位为i+1 第二位为i 第三位为i-1 例如 4 为 100 代表i+1有地雷，其它无雷
        dp[0][4][0]=1;
        dp[0][0][4]=1;
        dp[0][4][4]=1;
        int x=0;
        for (int i=1;i<=n;i++){
            //枚举上下两个状态
            for(int j=0;j<ST;j++){
                for (int k=0;k<ST;k++){
                    if(dp[i-1][j][k]==0)//筛选上一个状态的有效态
                        continue;
                    for (int s=0;s<2;s++){
                        for (int t=0;t<2;t++){
                            //构造现在i列状态
                            int as=(j>>1) | (s<<2);
                            int bt=(k>>1) | (t<<2);
                            if(num[as]+num[bt]==a[i]){//满足输入的列雷的数目要求则
                                dp[i][as][bt]+=dp[i-1][j][k];
                                if(dp[i][as][bt]>=mod)
                                    dp[i][as][bt]-=mod;
                            }
                        }
                    }
                }
            }
        }
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                x+=dp[n][i][j];
                x%=mod;
            }
        }
        System.out.println(x);
        scan.close();
    }
    static int numof1(int a){
        int x=0;
        while(a!=0){
            if((a&1)==1)
                x++;
            a>>=1;
        }
        return x;
    }
}
