package 直播课.day4;

import java.util.Scanner;

public class T19 {
    static long[][][][] dp;//dp i j k  i位  j个封闭图形 k 0/1 触顶
    static int[] v={1,0,0,0,1,0,1,0,2,1};//每个树的封闭图形数
    static int k;
    static int[] L;
    static int[] R;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);

        String l,r;
        dp=new long[20][30][2][2];
        for (int i=0;i<20;i++){
            for (int j=0;j<30;j++){
                for (int k=0;k<2;k++){
                    for (int m=0;m<2;m++){
                        dp[i][j][k][m]=-1;
                    }
                }
            }
        }
        l= scan.next();
        r= scan.next();
        k= scan.nextInt();
        L=new int[l.length()+1];
        R=new int[r.length()+1];
        for (int i=1;i<=l.length();i++){
            L[i]=l.charAt(i-1)-'0';
        }
        for (int i=1;i<=r.length();i++){
            R[i]=r.charAt(i-1)-'0';
        }
        System.out.println(dfs(1,0,1,1));
        System.out.println(dfs1(1,0,1,1));
        System.out.println((int)((chk(L)==k)?1:0));
        scan.close();
    }
    static long dfs(int pos,int sum,int op,int lead){
        if(sum>k)
            return 0;
        if(pos == R.length-1 )
            return sum==k? 1 : 0;
        if(dp[pos][sum][op][lead]!=-1)
            return dp[pos][sum][op][lead];
        long res=0;
        int maxn= op==1?R[pos]:9;
        int opg,leadg;
        for (int i=0;i<=maxn;i++){
            int ncnt=v[i];
            if(lead==1 && i==0){
                ncnt=0;
            }
            opg= (op==1 && R[pos]==i)? 1:0;
            leadg= (lead==1 && i==0 )? 1:0;
            res+=dfs(pos+1,sum+ncnt,opg,leadg);
        }
        dp[pos][sum][op][lead]=res;
        return res;
    }
    static long dfs1(int pos,int sum,int op,int lead){
        if(sum>k)
            return 0;
        if(pos == L.length-1 )
            return sum==k? 1 : 0;
        if(dp[pos][sum][op][lead]!=-1)
            return dp[pos][sum][op][lead];
        long res=0;
        int maxn= op==1?L[pos]:9;
        int opg,leadg;
        for (int i=0;i<=maxn;i++){
            int ncnt=v[i];
            if(lead==1 && i==0){
                ncnt=0;
            }
            opg= (op==1 && L[pos]==i)? 1:0;
            leadg= (lead==1 && i==0 )? 1:0;
            res+=dfs1(pos+1,sum+ncnt,opg,leadg);
        }
        dp[pos][sum][op][lead]=res;
        return res;
    }
    static int chk(int[] a){
        int sum=0;
        for(int i=1;i<a.length;i++){
            sum+=v[a[i]];
        }
        return sum;
    }
}
