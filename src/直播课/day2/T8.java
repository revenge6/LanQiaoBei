package 直播课.day2;

import java.util.Scanner;

/**
 * 小明的宠物袋————状压dp
 * 每行一个状态
 */
public class T8 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int N,M;
        int[] map;
        N= scan.nextInt();
        M= scan.nextInt();
        map=new int[N+1];
        int ST=1<<M;
        int[][] dp;
        dp=new int[N+1][1<<M];
        for (int i=1;i<=N;i++)
            for (int j=M-1;j>=0;j++){
                map[i]|=(scan.nextInt()<<j);//构造每行的状态
            }
        int ans=0;
        for(int i=1;i<=N;i++){
            for (int k=0;k<ST;k++){
                //检查最初只有食物状态时和我们枚举的状态是否有重合
                // 即枚举的状态为宠物放置状态，筛选出在原来食物位置放置宠物的状态
                if(noFood(map[i],k)&&notFight(k)){
                    int btk=num(k);
                    for (int pre=0;pre<ST;pre++){
                        //检查是否出现打架情况，当前行和上一行状态在相同位数为1说明有打架
                        //这里不能检查上一行状态是否符合食物的位置没有宠物，即判断条件不能加&&noFood(pre,map[i-1])
                        //其实我也不知道为啥不能加&&noFood(pre,map[i-1])
                        if(noFood(k,pre)&&noFood(pre,map[i-1])){
                            /**
                             * yua 1 1 0 0 0
                             * pre 1 1 0 1 0
                             * k   0 0 1 0 1
                             */
                            dp[i][k]=Math.max(dp[i][k],dp[i-1][pre]+btk);
                        }
                    }
                }
                ans=Math.max(dp[i][k],ans);
            }
        }
//最大放宠物数只需要在dp最后一行找吗？不行！因为存在粮食放置导致不确定位置
//        for(int i=0;i<ST;i++)
//            ans=Math.max(dp[N][i],ans);
        System.out.println(ans);
        scan.close();
    }
    static boolean noFood(int a,int b){//上下不打架或食物位置是否错放宠物
        return (a&b)==0;
    }
    static boolean notFight(int a){//左右不打架
        return (a&(a>>1))==0;
    }
    static int num(int a){//a状态下的宠物数
        int x=0;
        while (a!=0){
            x+=a&1;
            a>>=1;
        }
        return x;
    }
}
