package Java14b;

import java.util.Scanner;

public class T7 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n= scan.nextInt();
        int[] pile=new int[n+1];
        int[] col=new int[n+1];

        for (int i=1;i<=n;i++){
            pile[i]= scan.nextInt();
        }
        for (int i=1;i<=n;i++){
            col[i]= scan.nextInt();
        }

        scan.close();
    }
}
