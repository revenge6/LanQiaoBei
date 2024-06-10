import java.util.Arrays;
import java.util.Scanner;

public class Temp {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int num=scan.nextInt();

        int[] s=new int[num+2];
        for(int i=1;i<=num+1;i++){
            s[i]= scan.nextInt();
        }

        int way=n;
        int times=0;
        String str="";
        for(int i=1;i<=num+1;i++){
            if(way>=s[i]){
                way-=s[i];
            }else if(n>=s[i]){
                times++;
                str+=(i-1)+" ";
                way=n-s[i];
            }else {
                System.out.println("No Solution");
                return;
            }
        }
        System.out.println(times);
        System.out.println(str);
        scan.close();
    }

}
