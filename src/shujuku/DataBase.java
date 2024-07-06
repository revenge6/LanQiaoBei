package shujuku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    String url;//数据库链接
    String clzname;
    String[][] fields;//内容数组
    //系统表初始化函数
    public void begintable(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            // 创建MAP表SQL语句
            String sqlM = "CREATE TABLE IF NOT EXISTS Map (" +
                    "Clzname varchar(255) PRIMARY KEY, " +
                    "Table_name varchar(255) NOT NULL " +
                    ")";
            // 执行SQL语句
            stmt.executeUpdate(sqlM);
            //创建属性表
            String sqlA = "CREATE TABLE IF NOT EXISTS Attribute (" +
                    "Clzname varchar(255) not null , " +
                    "Att_name varchar(255)  NOT NULL, " +
                    "IsKey tinyint(1) not null DEFAULT 0," +
                    "Att_type varchar(100)," +
                    "FOREIGN KEY (Clzname) REFERENCES Map(Clzname)," +
                    "primary key(Clzname,Att_name)" +
                    ")";
            // 执行SQL语句
            stmt.executeUpdate(sqlA);
            System.out.println("初始化完成");

            // 关闭资源
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("找不到JDBC驱动类：" + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库连接失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //创建表函数
    public boolean Creattable(String url,String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");

            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);

            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            // 创建类数据表SQL语句
            String sql="CREATE TABLE IF NOT EXISTS TN (" ;
            for(int i=0;i<fields.length;i++){
                if (i==0) {sql=sql+fields[i][1]+" "+fields[i][0]+"PRIMARY KEY,";}
                if (i==fields.length-1) {sql=sql+fields[i][1]+" "+fields[i][0]+")";}
                else{sql=sql+fields[i][1]+" "+fields[i][0]+",";}
            }


            // 执行SQL语句
            stmt.executeUpdate(sql);
            //插入值insert into table1(field1,field2) values(value1,value2)
            String sql1="insert into TN(" ;
            String sql2="values(";
            for(int i=0;i<fields.length;i++){
                if (i==fields.length-1) {sql1=sql1+fields[i][1]+")";sql2=sql2+fields[i][2]+")";}
                else{ sql1=sql1+fields[i][1]+",";sql2=sql2+fields[i][2]+",";}

            }
            String sql3=sql1+sql2;
            stmt.executeUpdate(sql3);
            // 关闭资源
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean Insertable(String url,String[][] fields,String tablename){
        //对于类数据表进行插值
        try {
            Class.forName("org.sqlite.JDBC");

            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);

            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            // 创建类数据表SQL语句
            String sql1="insert into "+tablename+"(" ;
            String sql2="values(";
            for(int i=0;i<fields.length;i++){
                if (i==fields.length-1) {sql1=sql1+fields[i][1]+")";sql2=sql2+fields[i][2]+")";}
                else{ sql1=sql1+fields[i][1]+",";sql2=sql2+fields[i][2]+",";}

            }
            // 执行SQL语句
            String sql3=sql1+sql2;
            stmt.executeUpdate(sql3);
            // 关闭资源
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean updateRtable(String url,String[][] fields,String tablename,String clzname){

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            // 更新Map表
            String sql="insert into Map(Clzname,Table_name) values("+clzname+","+tablename+")" ;
            stmt.executeUpdate(sql);
            //更新属性表 INSERT INTO 表名 (列1, 列2, 列3, ...)
            //VALUES
            //    (值1_1, 值1_2, 值1_3, ...),
            //    (值2_1, 值2_2, 值2_3, ...),
            //    ...
            //    (值N_1, 值N_2, 值N_3, ...);
            String sql1="INSERT INTO Attribute (Clzname, Att_name, IsKey, Att_type) values";
            String sql2=" ";
            for(int i=0;i<fields.length;i++) {
                if (i != fields.length - 1) {
                    if (i == 0) {
                        sql2 = sql2 + "(" + clzname + "," + fields[i][1] + "," + "1," + fields[i][0] + "),";
                    } else {
                        sql2 = sql2 + "(" + clzname + "," + fields[i][1] + "," + "0," + fields[i][0] + "),";
                    }
                } else {
                    sql2 = sql2 + "(" + clzname + "," + fields[i][1] + "," + "0," + fields[i][0] + ");";
                }
            }

            // 执行SQL语句
            String sql3=sql1+sql2;
            stmt.executeUpdate(sql3);
            // 关闭资源
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
