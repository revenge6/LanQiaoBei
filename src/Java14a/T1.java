package Java14a;

import java.util.Scanner;

public class T1 {
    static int month[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
    static int temp[]={0,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,10,2,3,4,5,6,7,8,9,10,11,3,4};
    static long ans=0;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        for (int i=1900;i<=9999;i++){
            if(isLeap(i))
                month[2]=29;
            else
                month[2]=28;
            ans+=dp(i);
        }
        System.out.println(ans);
        scan.close();
    }
    static int dp(int y){
        int sum=0;
        for (int i=1;i<=12;i++){
            for (int j=1;j<=month[i];j++){
                if(sum(y)==temp[i]+temp[j])
                    sum++;
            }
        }
        return sum;
    }
    static int sum(int i){
        int sum=0;
        while (i>0){
            sum+=i%10;
            i/=10;
        }
        return sum;
    }
    static boolean isLeap(int a){
        return (a%4==0&&a%100!=0)||a%400==0;
    }
}
