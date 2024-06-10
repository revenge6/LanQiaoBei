package 搜索算法;

import java.util.Scanner;

public class T2 {
    static int N;
    static int ba1[];
    static int ba2[];
    static int path[];
    static int X[]={1,0,-1,0};
    static int Y[]={0,-1,0,1};
    static boolean flag[][];
    static int ip=0;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        N= scan.nextInt();
        ba1=new int[N];
        ba2=new int[N];
        path=new int[N*N];
        flag=new boolean[N+1][N+1];
        for (int i=0;i<N;i++)
            ba1[i]= scan.nextInt();
        for (int i=0;i<N;i++)
            ba2[i]= scan.nextInt();
        flag[0][0]=true;
        path[ip++]=0;
        ba1[0]--;
        ba2[0]--;
        dfs(0,0);
        scan.close();
    }
    static boolean check(int x,int y){
        if(x!=N-1||y!=N-1)
            return false;
        for (int i=0;i<N;i++){
            if(ba1[i]!=0||ba2[i]!=0)
                return false;
        }
        for (int i=0;i<ip;i++){
            System.out.print(path[i]+" ");
        }
        return true;
    }
    static boolean pd(int x,int y){
        if(x<0||y<0||x>=N||y>=N)
            return false;
        if(flag[x][y])
            return false;
        if(ba1[y]<=0||ba2[x]<=0)
            return false;
        return true;
    }
    static void dfs(int x,int y){
        int r;
        int c;
        if(check(x,y)){
            return;
        }
        for (int j=0;j<4;j++){
            r=x+X[j];
            c=y+Y[j];
            if(pd(r,c)){
                flag[r][c]=true;
                path[ip++]=r*N+c;
                ba1[c]--;
                ba2[r]--;
                dfs(r,c);
                flag[r][c]=false;
                ba1[c]++;
                ba2[r]++;
                ip--;
            }
        }
    }
}
