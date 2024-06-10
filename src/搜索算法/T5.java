package 搜索算法;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class T5 {
    static int n,m;
    static boolean fg[][];
    static int[][] migo;
    static Queue<Node> q;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        n= scan.nextInt();
        m= scan.nextInt();
        fg=new boolean[n][m];
        migo=new int[n][m];
        q=new LinkedList<Node>();
        for(int i=0;i<n;i++){
            String str= scan.next();
            for(int j=0;j<m;j++){
                migo[i][j]=str.charAt(j)-'0';
            }
        }
        Node a=new Node();
        /**
         *  DRRURRDDDR
         */
        a.st="";
        a.x=0;
        a.y=0;
        q.add(a);
        fg[0][0]=true;
        bfs();
        System.out.println(path);
        scan.close();
    }
    static class Node{
        String st="";
        int x;
        int y;
    }
    static int x[]={0,-1,1,0};
    static int y[]={1,0,0,-1};
    static String[] kind={"D","L","R","U"};
    static int path=1;
    static void bfs(){//186
        int time=q.size();
        while (!q.isEmpty()){
            Node t=q.poll();
            time--;
            for (int i=0;i<4;i++){
                int xx=t.x+x[i];
                int yy=t.y+y[i];
                if(xx>=0&&yy>=0&&xx<m&&yy<n&&!fg[yy][xx]&&migo[yy][xx]!=1){
                    Node a=new Node();
                    a.st=t.st+kind[i];
                    a.x=xx;
                    a.y=yy;
                    q.add(a);
                    fg[yy][xx]=true;
                    if(xx==m-1&&yy==n-1){
                        System.out.println(a.st);
                        System.out.println(a.st.length());
                        return;
                    }
                }
            }
            if(time==0){
                time=q.size();
                path++;
            }
        }
    }

}
