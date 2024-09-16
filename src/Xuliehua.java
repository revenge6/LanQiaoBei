import shujuku.IPersistentStore;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

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
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
       //print(ObjReflect.GetFields(new xuliehua()));
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
//        Statement stmt = conn.createStatement();
//        String sql="";
//        stmt.execute("alter table shujuku$GtTest drop column grade;");
//        stmt.close();
//        conn.close();
//        sql+="Create table gt ( aByte NONE, aShort NONE , anInt NONE, aLong NONE , aDouble NONE , " +
//                "aFloat NONE , aChar NONE , aBoolean NONE , a NONE);";
//        stmt.execute(sql);
//        String sqlI="Insert into gt (aByte,aShort,anInt,aLong) Values ("+aByte+","+aShort+","+anInt+","+aLong+");";
//        stmt.execute(sqlI);
    }
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
