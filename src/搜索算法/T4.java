package 搜索算法;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class T4 {
    static Queue<Node> q;
    static int n,m;
    static char[][] g;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        n= scan.nextInt();
        m= scan.nextInt();
        q=new LinkedList<Node>();
        int k;
        g=new char[n][m];
        for (int i=0;i<n;i++){
            String str= scan.next();
            for (int j=0;j<m;j++){
                g[i][j]=str.charAt(j);
                if(g[i][j]=='g'){
                    Node a=new Node();
                    a.x=i;
                    a.y=j;
                    q.add(a);
                }
            }
        }
        k= scan.nextInt();

        bfs(k);
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                System.out.print(g[i][j]);
            }
            System.out.println();
        }
        scan.close();
    }
    static int x[]={1,0,-1,0};
    static int y[]={0,-1,0,1};
    static void bfs(int k){
        int time=q.size();
        while (!q.isEmpty()){
            Node t=q.poll();
            time--;
            for (int i=0;i<4;i++){
                int xx=t.x+x[i];
                int yy=t.y+y[i];
                if(xx>=0&&yy>=0&&xx<n&&yy<m&&g[xx][yy]=='.'){
                    Node a=new Node();
                    a.x=xx;
                    a.y=yy;
                    q.add(a);
                    g[xx][yy]='g';
                }
            }
            if(time==0){
                time=q.size();
                k--;
                if(k==0)
                    break;
            }
        }
    }
    static class Node{
        int x;
        int y;
    }
}
