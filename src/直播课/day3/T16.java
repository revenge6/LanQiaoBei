package 直播课.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 卖树————换根dp
 */
public class T16 {
    static int n,k,c;
    static List<Integer>[] G;
    static int dep[],mdp[];
    static long ans=0;
    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);

        int t=scan.nextInt();
        while (t-->0){
            n= scan.nextInt();//结点数
            k= scan.nextInt();//边长度
            c= scan.nextInt();//代价
            dep=new int[n+1];//保存基本信息，换根只会影响被换根节点，待换子节点
            mdp=new int[n+1];//结点最大深度
            G=new ArrayList[n+1];
            ans=0;//ans不及时置零只能过10%···
            for (int i=0;i<=n;i++){
                G[i]=new ArrayList<>();
            }
            for (int i=1;i<n;i++){
                int u=scan.nextInt();
                int v=scan.nextInt();
                //双向边
                G[u].add(v);
                G[v].add(u);
            }
            dfs(1,0,0);
            dfs1(1,0);
            System.out.println(ans);
        }

        scan.close();
    }
    static void dfs(int u,int f,int dt){
        mdp[u]=0;
        dep[u]=dt;//以1为根节点各个结点深度，初始1的深度当然为0
        for (int v:G[u]){
            if(v==f)//因为G[u]存储的是邻接点所以这里要用一个参数表示父亲，因为：后面要递归，不能儿子去找父亲
                continue;
            dfs(v,u,dt+1);//递归参数，子树根节点v，他的父亲结点u，以及深度+1
            mdp[u]=Math.max(mdp[u],mdp[v]+1);
            /**
                1
             2  3   4
             5
             mdp[2]=max(0,mdp[5]+1)=max(0,1+1)=2
             */
        }
    }
    static void dfs1(int u,int f){
        //重新转移
        int tmpf=0,Mx1=0,Mx2=0;
        for(int v:G[u]){
            tmpf=Math.max(tmpf,mdp[v]+1);//找当前结点邻接点为根子树深度的最大值，目的：知道数的最大深度而已
        }
        ans=Math.max(ans,(long)tmpf*k-(long)dep[u]*c);//不加long只能过50%
        //根变为儿子
        int pre = mdp[u];//记录换根前的u
        for(int v:G[u]){//好像是找到邻接点的子树深度最大的值+1，Mx1和第二大Mx2+1
            if(mdp[v]+1>Mx1){
                Mx2=Mx1;
                Mx1=mdp[v]+1;
            }else if(mdp[v]+1>Mx2) {
                Mx2=mdp[v]+1;
            }
        }

        for(int v:G[u]){
            if(v==f)
                continue;//不能找到父亲身上
            if(mdp[v]+1==Mx1){//找到处了根节点和父亲外深度最大的结点
                mdp[u]=Mx2;//当前根结点换为v，深度为Mx2
            }else {//
                mdp[u]=Mx1;//
            }
            dfs1(v,u);
        }
        mdp[u]=pre;
    }
}
