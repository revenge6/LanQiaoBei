package 直播课.day1;

import java.util.Scanner;

public class Add2 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int T;
        T= scan.nextInt();
        //while
        int kt;
        kt= scan.nextInt();
        int[] p;
        int[] s;
        p=new int[kt+1];
        s=new int[kt+1];
        for (int i=1;i<=kt;i++){
            p[i]= scan.nextInt();
            s[i]= scan.nextInt();
        }


        scan.close();
    }
}
