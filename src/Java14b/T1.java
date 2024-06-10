package Java14b;

import java.util.Scanner;

public class T1 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int t=2023;
        int num=0;
        while (t-->0){
            num++;
            while (!isH(num)){
                num++;
            }
        }
        System.out.println(num);
        scan.close();
    }
    static boolean isH(int n){
        return n%sum(2,n)==0&&n%sum(8,n)==0&&n%sum(10,n)==0&&n%sum(16,n)==0;
    }
    //进制数位和
    static int sum(int c,int n){
        int sum=0;
        while (n>0){
            sum+=n%c;
            n=n/c;
        }
        return sum;
    }
}
