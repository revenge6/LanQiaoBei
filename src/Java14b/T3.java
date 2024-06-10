package Java14b;

import java.util.Scanner;

public class T3 {
    static long x[]=new long[5];
    static long y[]=new long[5];
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        for (int i=1;i<5;i++){
            x[i]= scan.nextLong();
            y[i]= scan.nextLong();
        }
        long s1=(x[2]-x[1])*(y[2]-y[1]);
        long s2=(x[4]-x[3])*(y[4]-y[3]);
        long overx=Math.max(0,Math.min(x[2],x[4])-Math.max(x[1],x[3]));
        long overy=Math.max(0,Math.min(y[2],y[4])-Math.max(y[1],y[3]));
        if(overy>0&overx>0){
            System.out.println(s1+s2-overx*overy);
        }else {
            System.out.println(s1+s2);
        }
        scan.close();
    }
}
