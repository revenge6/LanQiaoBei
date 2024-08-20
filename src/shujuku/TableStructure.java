package shujuku;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static shujuku.DataBase.kindSwitch;

public class TableStructure {
    static String url;
    DataBase dataBase;

    public TableStructure(DataBase dataBase) {
        if (DataBase.url == "" || DataBase.url == null)
            return;
        url = DataBase.url;
        this.dataBase = dataBase;
    }

    public static void UpdateTable(String tableName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();
            String clzName = DataBase.GetClzName(tableName);
            //1.获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("select attitudeName,attitudeType from Attribute where clzName='" + clzName + "';");
            Map<String, String> columns = new LinkedHashMap<>();
            // 遍历结果集并将列头和列数据类型保存到数组中
            while (resultSet.next()) {
                columns.put(resultSet.getString("attitudeName"), resultSet.getString("attitudeType"));
            }
            stmt.close();
            conn.close();
            //2.遍历fields
            for (int i = 0; i < fields.length; i++) {
                if (columns.get(fields[i][1]) == null) {
                    //插入列，新列名为 fields[i][1]
                    AlterList(tableName, fields[i][1], fields[i][0]);
                } else if (!columns.get(fields[i][1]).equals(fields[i][0])) {
                    columns.remove(fields[i][1]);
                    //更新列，列属性更改为fields[i][0]
                    UpdateList(tableName, fields[i][1], fields[i][0]);
                    DataBase.Update_datatype(clzName,fields[i][1],fields[i][0]);
                } else {
                    columns.remove(fields[i][1]);
                }
            }
            //遍历columns，删除其中的列
            if (!columns.isEmpty()) {
                String[] keys = columns.keySet().toArray(new String[0]);
                for (int i = 0; i < keys.length; i++) {
                    //删除列，列名为keys[i]
                    DeleteList(tableName, keys[i]);
                    DataBase.Del_column(clzName, keys[i]);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //插入列
    private static boolean AlterList(String tableName, String newList, String newListType) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            String sql = "alter table " + tableName + " add column " + newList + " " + newListType;
            int count = stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //更新列
    private static boolean UpdateList(String tableName, String field, String newFieldType) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            String clzName = DataBase.GetClzName(tableName);
            // 获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("select attitudeName,attitudeType from Attribute where clzName='" + clzName + "';");
            Map<String, String> columns = new LinkedHashMap<>();
            while (resultSet.next()) {
                columns.put(resultSet.getString("attitudeName"), resultSet.getString("attitudeType"));
            }
            columns.put(field,newFieldType);
            String[] keys=columns.keySet().toArray(new String[0]);
            //删除旧表
            String sqlD="drop table "+tableName+";";
            stmt.execute(sqlD);
            //创建新表
            String sql = "create table if not exists " + tableName + " (";
            for (int i = 0; i < keys.length; i++) {
                String kind=kindSwitch.get(columns.get(keys[i]))!=null?kindSwitch.get(columns.get(keys[i])):"NONE";
                if (i == 0)
                    sql = sql + keys[i] + " " + kind + " primary key,";
                else if (i == keys.length - 1)
                    sql = sql + keys[i] + " " + kind + ");";
                else
                    sql = sql + keys[i] + " " + kind + ",";
            }
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //    //删除列
//    private static boolean DeleteList(String tableName, String list){
//        try {
//            Class.forName("org.sqlite.JDBC");
//            Connection conn = DriverManager.getConnection(url);
//            // 创建Statement对象来执行SQL语句
//            Statement stmt=conn.createStatement();
//            String sql="alter table "+tableName+" drop column "+list+";";
//            int count=stmt.executeUpdate(sql);
//            stmt.close();
//            conn.close();
//            if(count==0)
//                return false;
//            return true;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    //删除列
    private static boolean DeleteList(String tableName, String list) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            String clzName = DataBase.GetClzName(tableName);
            // 获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("select attitudeName,attitudeType from Attribute where clzName='" + clzName + "';");
            Map<String, String> columns = new LinkedHashMap<>();
            while (resultSet.next()) {
                columns.put(resultSet.getString("attitudeName"), resultSet.getString("attitudeType"));
            }
            columns.remove(list);
            String temp = tableName + "Temp";
            //构造创建表语句
            String sql = "create table " + temp + " as select ";
            String[] keys = columns.keySet().toArray(new String[0]);
            for (int i = 0; i < keys.length; i++) {
                sql += keys[i];
                if (i != keys.length - 1) {
                    sql += ",";
                }
            }
            sql += " from " + tableName + ";";
            stmt.execute(sql);
            //删除旧表
            String sqlD = "drop table " + tableName + ";";
            stmt.execute(sqlD);
            //将新表名修改为类名
            String sqlR = "alter table " + temp + " rename to " + tableName+";";
            stmt.execute(sqlR);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
