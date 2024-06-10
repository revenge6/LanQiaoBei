package 搜索算法;

import java.util.Scanner;

public class T3 {
    static long ans=0;
    static long temp=0;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        String N= scan.next();

        int A= scan.nextInt();
        int B= scan.nextInt();

        int[] n=new int[N.length()];
        for (int i=0;i<N.length();i++){
            n[i]=N.charAt(i)-'0';
        }
        dfs(n,0,A,B);
        System.out.println(temp);
        scan.close();
    }

    static void dfs(int[] n,int i,int A,int B){
        if(i==n.length){
            temp=Math.max(ans,temp);
            return;
        }
        ans=10*ans+Math.min(9,n[i]+A);
        int t=Math.min(9-n[i],A);
        dfs(n,i+1,A-t,B);
        ans/=10;
        if(n[i]+1<=B){
            ans=10*ans+9;
            dfs(n,i+1,A,B-n[i]-1);
        }else {
            ans=10*ans+n[i];
            dfs(n,i+1,A,B);
        }
        ans/=10;
    }
}
