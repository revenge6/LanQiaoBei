package 直播课.day1;

import java.util.Scanner;

/**
 * 线性dp————保险箱
 * dp[i]={  dp[i-1]+y[i]-x[i]  y[i]>x[i],   不产生进借位
 *          dp[i-1]+y[i]+10-x[i]    x[i]>=y[i], 产生进位
 *          dp[i-1]+x[i]+10-y[i]    y[i]>x[i],  产生借位
 *          dp[i-1]+x[i]-y[i]   x[i]>=y[i]  不产生进借位
 *          }
 *
 */
public class T2 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        //输入
        int n= scan.nextInt();
        int[] x=new int[n+1];
        int[] y=new int[n+1];
        String in1= scan.next();
        String in2= scan.next();
        for (int i=1;i<=n;i++){
            x[i]= in1.charAt(i-1)-'0';
            y[i]=in2.charAt(i-1)-'0';
        }
        int[][] dp=new int[n+1][3];
        //
        int ab=x[n]-y[n];
        int ab1;
        int ab2;
        dp[n][0]=Math.abs(ab);
        dp[n][1]=10-ab;
        dp[n][2]=10+ab;
        for(int i=n-1;i>0;i--){
            ab=x[i]-y[i];
            ab1=x[i]+1-y[i];
            ab2=x[i]-1-y[i];
            dp[i][0]=findMin(dp[i+1][0]+Math.abs(ab),dp[i+1][1]+Math.abs(ab1),dp[i+1][2]+Math.abs(ab2));
            dp[i][1]=findMin(dp[i+1][0]+10-ab,dp[i+1][1]+10-ab1,dp[i+1][2]+10-ab2);
            dp[i][2]=findMin(dp[i+1][0]+10+ab,dp[i+1][1]+10+ab1,dp[i+1][2]+10+ab2);
        }
        System.out.println(findMin(dp[1][0],dp[1][1],dp[1][2]));
        scan.close();
    }
    static int findMin(int a,int b,int c){
        return Math.min(a,Math.min(b,c));
    }
}
