package Java14b;

import java.util.Scanner;
//做不出来

/**
 * 暴力bfs+枚举
 */
public class T5 {
    static int N;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        N= scan.nextInt();
        int[][] a=new int[N+1][N+1];
        int[][] b=new int[N+1][N+1];
        for (int i=1;i<=N;i++)
            for (int j=1;j<=N;j++)
                a[i][j]=scan.nextInt();
        for (int i=1;i<=N;i++)
            for (int j=1;j<=N;j++)
                b[i][j]=scan.nextInt();
        Node n=new Node();
        for (int i=1;i<=N;i++)
            for (int j=1;j<=N;j++){
                if(a[i][j]==1){
                    n.x=j;
                    n.y=i;

                }
            }

        scan.close();
    }
    static int x[]={1,0,-1,0};
    static int y[]={0,1,0,-1};
    static boolean[][] flag;
    static int bfs(int x,int y){
        if(x>0&&x<=N&&y>0&&y<=N)
            return 0;
        if(!flag[x][y])
            return 0;
        flag[x][y]=false;
        return bfs(x+1,y)+bfs(x-1,y)+bfs(x,y+1)+bfs(x,y-1)+1;
    }
    static class Node{
        int x;
        int y;
    }
}
