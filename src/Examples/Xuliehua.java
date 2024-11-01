package Examples;

import SqliteJavaCRUD.IPersistentStore;

import java.io.Serializable;
import java.sql.SQLException;

//以这个类为测试类，将类的实例对象添加到数据库中
public class Xuliehua implements IPersistentStore, Serializable {
    static byte aByte=1;
    static short aShort=1;
    static int anInt=1;
    static long aLong=1;

    double aDouble=1.0;

    float aFloat=1.0f;
    char aChar='a';
    boolean aBoolean=true;
    String a="123";
    static void print(String[][] str){
        for (int i=0;i<str.length;i++){
            for (int j=0;j<str[i].length;j++){
                System.out.print(str[i][j]+" ");
            }
            System.out.println();
        }
    }

    @Override
    public String getPriKey() {
        return "anInt";
    }

    @Override
    public String getPriKeyValue() {
        return anInt+"";
    }
}
