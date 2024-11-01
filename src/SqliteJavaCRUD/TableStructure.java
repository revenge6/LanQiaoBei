package SqliteJavaCRUD;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static SqliteJavaCRUD.DataBase.kindSwitch;

public class TableStructure {
    static String url;
    DataBase dataBase;
    public TableStructure(DataBase dataBase) {
        if (DataBase.url == "" || DataBase.url == null)
            return;
        url = DataBase.url;
        this.dataBase = dataBase;
    }
    //更新表
    public static void UpdateTable(String tableName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();
            String clzName = DataBase.GetClzName(tableName);
            //1.获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("select attributeName,attributeType from Attribute where clzName='" + clzName + "';");
            Map<String, String> columns = new LinkedHashMap<>();
            // 遍历结果集并将列头和列数据类型保存到数组中
            while (resultSet.next()) {
                columns.put(resultSet.getString("attributeName"), resultSet.getString("attributeType"));
            }
            stmt.close();
            conn.close();
            //2.遍历fields
            for (int i = 0; i < fields.length-1; i++) {
                if (columns.get(fields[i][1]) == null) {
                    //插入列，新列名为 fields[i][1]
                    AlterList(tableName, fields[i][1], fields[i][0]);
                    DataBase.Alter_column(clzName, fields[i][1],fields[i][0]);
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
            String kind = kindSwitch.get(newListType)!=null?kindSwitch.get(newListType):"NONE";
            String sql = "alter table " + tableName + " add column " + newList + " " + kind+";";
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
            ResultSet resultSet = stmt.executeQuery("select attributeName,attributeType from Attribute where clzName='" + clzName + "';");
            Map<String, String> columns = new LinkedHashMap<>();
            while (resultSet.next()) {
                columns.put(resultSet.getString("attributeName"), resultSet.getString("attributeType"));
            }
            //获取主键列属性名
            ResultSet rs=stmt.executeQuery("select attributeName from Attribute where clzName='" + clzName + "' and isKey=1;");
            String priKey=rs.getString("attributeName");
            //更换数据类型
            columns.put(field,newFieldType);
            String[] keys=columns.keySet().toArray(new String[0]);
            //1.创建新表
            String newTableName=tableName+"Temp";
            String sqlC = "create table if not exists " + newTableName;
            String fieldsSql=" (";
            for (int i = 0; i < keys.length; i++) {
                String kind=kindSwitch.get(columns.get(keys[i]))!=null?kindSwitch.get(columns.get(keys[i])):"NONE";
                if (priKey.equals(keys[i]))
                    fieldsSql = fieldsSql + keys[i] + " " + kind + " primary key,";
                else
                    fieldsSql = fieldsSql + keys[i] + " " + kind + ",";
            }
            String xuliehuaKind=kindSwitch.get(columns.get(ObjReflect.GetClzName("")))!=null?kindSwitch.get(columns.get(ObjReflect.GetClzName(""))):"NONE";
            fieldsSql=fieldsSql+ObjReflect.xuliehua+" "+xuliehuaKind+")";
            sqlC=sqlC+fieldsSql+";";
            stmt.execute(sqlC);
            //2.插入新表
            String sqlI="Insert into "+newTableName+" (";
            String fieldsSql2="";
            for(int i=0;i< keys.length;i++){
                fieldsSql2=fieldsSql2+keys[i]+",";
            }
            fieldsSql2=fieldsSql2+ObjReflect.xuliehua;
            sqlI=sqlI+fieldsSql2+")"+" select "+fieldsSql2+" from "+tableName;
            stmt.execute(sqlI);
            //3.删除旧表
            String sqlD="drop table "+tableName+";";
            stmt.execute(sqlD);
            //4.将新表重命名为旧表
            String sqlA="Alter table "+newTableName+" rename to "+tableName+";";
            stmt.execute(sqlA);

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //删除列
    private static boolean DeleteList(String tableName, String list) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            String clzName = DataBase.GetClzName(tableName);
            // 获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("select attributeName,attributeType from Attribute where clzName='" + clzName + "';");
            Map<String, String> columns = new LinkedHashMap<>();
            while (resultSet.next()) {
                columns.put(resultSet.getString("attributeName"), resultSet.getString("attributeType"));
            }
            columns.remove(list);
            String temp = tableName + "Temp";
            //1.构造创建表语句
            String sql = "create table " + temp + " as select ";
            String[] keys = columns.keySet().toArray(new String[0]);
            for (int i = 0; i < keys.length; i++) {
                sql=sql+keys[i]+",";
            }
            sql=sql+ObjReflect.xuliehua;
            sql += " from " + tableName + ";";
            stmt.execute(sql);
            //2.删除旧表
            String sqlD = "drop table " + tableName + ";";
            stmt.execute(sqlD);
            //3.将新表名修改为类名
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
    //改主键-2
    public static boolean AlterKey(String clzName,String tableName, String newKey) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            // 获取列头信息
            ResultSet resultSet = stmt.executeQuery("select attributeName, attributeType from Attribute where clzName='" + clzName + "';");
            Map<String, String> columns = new LinkedHashMap<>();
            while (resultSet.next()) {
                columns.put(resultSet.getString("attributeName"), resultSet.getString("attributeType"));
            }
            // 检查新主键是否在列中
            if (!columns.containsKey(newKey)) {
                throw new RuntimeException("The new key is not a valid column in the table.");
            }
            // 创建临时表的 SQL 语句
            String tempTableName = tableName + "Temp";
            StringBuilder createTempTable = new StringBuilder("CREATE TABLE " + tempTableName + " (");

            String[] keys = columns.keySet().toArray(new String[0]);
            for (int i = 0; i < keys.length; i++) {
                String type = kindSwitch.get(columns.get(keys[i]));
                if (keys[i].equals(newKey)) {
                    createTempTable.append(keys[i]).append(" ").append(type).append(" PRIMARY KEY,");
                } else {
                    createTempTable.append(keys[i]).append(" ").append(type+",");
                }
            }
            String xuliehuaKind=kindSwitch.get(columns.get(ObjReflect.GetClzName("")))!=null?kindSwitch.get(columns.get(ObjReflect.GetClzName(""))):"NONE";
            createTempTable.append(ObjReflect.xuliehua).append(" ").append(xuliehuaKind);
            createTempTable.append(");");
            //System.out.println("Create Table SQL: " + createTempTable);  // 打印 SQL 语句用于调试
            // 执行创建表的 SQL
            stmt.execute(createTempTable.toString());
            //插入新表
            String sqlI="Insert into "+tempTableName+" (";
            String fieldsSql2="";
            for(int i=0;i< keys.length;i++){
                fieldsSql2=fieldsSql2+keys[i]+",";
            }
            fieldsSql2=fieldsSql2+ObjReflect.xuliehua;
            sqlI=sqlI+fieldsSql2+")"+" select "+fieldsSql2+" from "+tableName;
            stmt.execute(sqlI);

            // 删除旧表
            String dropOldTable = "DROP TABLE IF EXISTS " + tableName + ";";
            //System.out.println("Drop Table SQL: " + dropOldTable);  // 打印 SQL 语句用于调试
            stmt.execute(dropOldTable);

            // 将临时表重命名为原表名
            String renameTable = "ALTER TABLE " + tempTableName + " RENAME TO " + tableName + ";";
            //System.out.println("Rename Table SQL: " + renameTable);  // 打印 SQL 语句用于调试
            stmt.execute(renameTable);

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
