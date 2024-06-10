package Java14b;

import java.util.Scanner;

public class T9 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        long jie=1;
        long sum=0;
        long mod=(long)1e10;

        for (int i=2;i<=50;i++){
            sum=(sum+jie)%mod;
            jie=jie*i%mod;
            System.out.println(jie);
        }
        scan.close();
    }
}
