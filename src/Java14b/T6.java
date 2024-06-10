package Java14b;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class T6 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        int N;
        N= scan.nextInt();
        long a[]=new long[N+1];
        for (int i=1;i<=N;i++){
            a[i]= scan.nextLong();
        }
        Queue<Long> q=new LinkedList<Long>();
        Arrays.sort(a);
        long cost=0;
        N++;
        boolean second=false;
        while (N-->0){
            if(!q.isEmpty()&&q.peek()>=a[N]){
                q.poll();
            }else {
                cost+=a[N];
                if(second){
                    q.add(a[N]/2);
                    second=false;
                }else{
                    second=true;
                }
            }
        }
        System.out.println(cost);
        scan.close();
    }
}
