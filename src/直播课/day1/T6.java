package 直播课.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class T6 {
    public static void main(String[] args) throws IOException {
        StreamTokenizer st=new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        int n;
        int V;
        st.nextToken();
        n= (int)st.nval;
        st.nextToken();
        V= (int)st.nval;
        int[] w;
        int[] v;
        int[] s;
        w=new int[n+1];
        v=new int[n+1];
        s=new int[n+1];
        for (int i=1;i<=n;i++){
            st.nextToken();
            w[i]= (int)st.nval;
            st.nextToken();
            v[i]= (int)st.nval;
            st.nextToken();
            s[i]= (int)st.nval;
            if(s[i]==0)
                s[i]=V/w[i];
        }
        int[] dp;
        dp=new int[V+1];
        //
        for (int i=1;i<=n;i++){
            for (int k=1;k<=s[i]&&k*w[i]<=V;k++)
            for (int j=V;j>=w[i];j--){
                dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
            }
        }
        System.out.println(dp[V]);
    }
}
