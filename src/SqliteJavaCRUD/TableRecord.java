package SqliteJavaCRUD;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TableRecord {
    static String url;
    DataBase dataBase;
    public TableRecord(DataBase dataBase){
        if(dataBase.url==""||dataBase.url==null)
            return;
        url=dataBase.url;
        this.dataBase=dataBase;
    }
    //插入记录函数
    public static boolean Add(String tableName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();
            // 构造插入表SQL语句
            String sql = "INSERT INTO "+tableName+" (";
            for (int i = 0; i < fields.length; i++) {
                sql+=fields[i][1];
                if (i < fields.length - 1) {
                    sql+=", ";
                }
            }
            sql+=") values (";
            for (int i = 0; i < fields.length; i++) {
                //boolean flag=DataBase.kindSwitch.get(fields[i][0])=="INTEGER"||DataBase.kindSwitch.get(fields[i][0])=="REAL";
                sql+="'"+fields[i][2]+"'";
                if (i != fields.length-1) {
                    sql+=",";
                }
            }
            sql+=");";
            int count=stmt.executeUpdate(sql);
            // 检查是否成功插入数据
            stmt.close();
            conn.close();
            return count > 0;
        } catch (SQLException e) {
            System.out.println("插入对象失败，已添加过相同主键值对象！");
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    //删除一个对象
    public static boolean Delete(String tableName,String priKey,String priKeyValue) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            String sql = "delete from " + tableName + " where " + priKey + "='" + priKeyValue + "';";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean Delete(String tableName,String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            String sql = "delete from " + tableName + " where ";
            for (int i=0;i< fields.length;i++){
                sql+=fields[i][1]+" = '"+fields[i][2]+"'";
                if(i< fields.length-1)
                    sql+=" and ";
            }
            sql+=";";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean Update(String tableName,String[][] fields) {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();

            String sql="update "+tableName+" set ";
            for (int i=0;i<fields.length;i++){
                sql+=fields[i][1]+"='"+fields[i][2]+"'";
                if(i!= fields.length-1)
                    sql+=",";
            }
            sql+=" where "+fields[0][1]+"='"+fields[0][2]+"';";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object SearchByID(String tableName, String persistentStorePriKey, String priKeyValue){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();
            String sqlS="select "+ ObjReflect.xuliehua +
                    " from "+tableName +
                    " where "+persistentStorePriKey + " = '"+priKeyValue+"';";
            ResultSet rs = stmt.executeQuery(sqlS);
            String byteStream="";
            if (rs.next()) {
                byteStream = rs.getString(ObjReflect.xuliehua);
            }
            stmt.close();
            conn.close();
            return ObjReflect.DeserializeFromString(byteStream);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Object> SearchByFields(String tableName,String[][] fields){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();
            // 构造插入表SQL语句
            String sqlS="select "+ ObjReflect.xuliehua +
                    " from "+tableName +
                    " where ";
            for (int i = 0; i < fields.length; i++) {
                sqlS+=fields[i][1]+"='"+fields[i][2]+"' ";
                if (i < fields.length -1) {
                    sqlS+=" and ";
                }
            }
            sqlS+=";";
            ResultSet rs = stmt.executeQuery(sqlS);
            String byteStream="";
            List<Object> objs=new LinkedList<>();
            while (rs.next()) {
                byteStream = rs.getString(ObjReflect.xuliehua);
                objs.add(ObjReflect.DeserializeFromString(byteStream));
            }
            stmt.close();
            conn.close();
            return objs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}