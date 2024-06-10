package Java14b;

import java.math.BigInteger;

public class Pow {
    static long mod=1000000007;
    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            if(Math.pow(2,i)%mod==pow(2,i)){
                continue;
            }
            System.out.println("Math.pow(2,i)="+Math.pow(2,i)+" pow(2,i)="+pow(2,i));
        }
        
    }
    static long pow(int a,int b){
        long base=a;
        long ans=1;
        while (b>0){
            if((b&1)==1){
                ans=ans*base%mod;
            }
            base=base*base%mod;
            b=b>>1;
        }
        return ans;
    }
}
