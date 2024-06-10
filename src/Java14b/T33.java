package Java14b;

import java.util.Scanner;

public class T33 {
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
        if(notCover()){
            System.out.println(s1+s2);
        }else if(in()){
            System.out.println(Math.max(s1,s2));
        }else {
            if(ex1()){
                System.out.println(s1+s2-s(x[2],y[2],x[3],y[3]));
            }else if(ex2()){
                System.out.println(s1+s2-s(x[2],y[1],x[3],y[4]));
            }else if(ex3()){
                System.out.println(s1+s2-s(x[1],y[1],x[4],y[4]));
            }else if(ex4()){
                System.out.println(s1+s2-s(x[1],y[2],x[4],y[3]));
            }else {
                System.out.println(s1+s2);
            }
        }
        scan.close();
    }
    static boolean notCover(){
        return x[2]<=x[3]||x[1]>=x[4]||y[1]>=y[4]||y[2]<=y[3];
    }
    static boolean in(){
        return (x[1]<=x[3]&&x[2]>=x[4]&&y[1]<=y[3]&&y[2]>=y[4])||(x[1]>=x[3]&&x[2]<=x[4]&&y[1]>=y[3]&&y[2]<=y[4]);
    }
    static boolean ex1(){
        return x[1]<=x[3]&&x[3]<=x[2]&&y[1]<=y[3]&&y[3]<=y[2];
    }
    static boolean ex2(){
        return x[1]<=x[3]&&x[3]<=x[2]&&y[1]<=y[4]&&y[4]<=y[2];
    }
    static boolean ex3(){
        return x[1]<=x[4]&&x[4]<=x[2]&&y[1]<=y[4]&&y[4]<=y[2];
    }
    static boolean ex4(){
        return x[1]<=x[4]&&x[4]<=x[2]&&y[1]<=y[3]&&y[3]<=y[2];
    }
    static long s(long x1,long y1,long x2,long y2){
        return Math.abs((x1-x2)*(y1-y2));
    }
}
