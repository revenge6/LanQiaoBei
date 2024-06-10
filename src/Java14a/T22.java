package Java14a;

import java.util.Scanner;

public class T22 {
    static int ans=0;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        int[][] t=new int[6][6];
        t[1][1]=1;
        t[1][2]=0;
        t[1][3]=1;
        t[1][4]=0;
        t[1][5]=1;
        dfs(t,2,1);
        System.out.println(ans);
        scan.close();
    }
    static void dfs(int[][] t,int i,int j){
        if(i==6){
            if(t[5][1]==1)
                ans++;
            return;
        }
        // & | ^ 列举三种情况
        t[i][j]=t[i-1][j]&t[i-1][j+1];
        if(j<6-i)
            dfs(t,i,j+1);
        else
            dfs(t,i+1,1);
        t[i][j]=t[i-1][j]|t[i-1][j+1];
        if(j<6-i)
            dfs(t,i,j+1);
        else
            dfs(t,i+1,1);
        t[i][j]=t[i-1][j]^t[i-1][j+1];
        if(j<6-i)
            dfs(t,i,j+1);
        else
            dfs(t,i+1,1);
    }
}
