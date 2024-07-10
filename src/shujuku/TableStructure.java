package shujuku;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TableStructure {
    static String url;
    DataBase dataBase;
    TableStructure(DataBase dataBase){
        if(dataBase.url==""||dataBase.url==null)
            return;
        url=dataBase.url;
        this.dataBase=dataBase;
    }
    public static void UpdateTable(String tableName,String[][] fields){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();
            //1.获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("pragma table_info( "+tableName+" )");
            // 获取结果集的列数
            //ResultSetMetaData metaData = resultSet.getMetaData();
            //int columnCount = metaData.getColumnCount();
            // 创建二维字符串数组来保存列头和列数据类型
            //String[][] columns = new String[columnCount][2];
            //List<FieldInfo> columns=new ArrayList<>();
            Map<String,String> columns=new HashMap<>();
            // 遍历结果集并将列头和列数据类型保存到数组中
            while (resultSet.next()) {
                columns.put(resultSet.getString("name"),resultSet.getString("type"));
            }
            //创建保存需要添加新列的数组
            //String[][] newCols=new String[fields[0].length][2];
            //List<FieldInfo> newCols=new ArrayList<>();
            //创建保存需要更新的列的数组
            //String[][] updateCols=new String[fields[0].length][2];
            //List<FieldInfo> updateCols=new ArrayList<>();

            //遍历fields
            for(int i=0;i<fields[0].length;i++){
                if(columns.get(fields[i][1])==null){
                    //插入列，新列名为 fields[i][1]
                    AlterList(tableName,fields[i][1],fields[i][0]);
                }else if(!columns.get(fields[i][1]).equals(fields[i][0])){
                    columns.remove(fields[i][1]);
                    //更新列，列属性更改为fields[i][0]
                    UpdateList(tableName,fields[i][1],fields[i][0]);
                }
            }
            //遍历columns，删除其中的列
            if(!columns.isEmpty()){
                String[] keys=columns.keySet().toArray(new String[0]);
                for(int i=0;i< keys.length;i++){
                    //删除列，列名为keys[i]
                    DeleteList(tableName,keys[i]);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //插入列
    private static boolean AlterList(String tableName, String newList, String newListType){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();

            String sql="alter table "+tableName+" add column "+newList+" "+newListType;
            int count=stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            if(count==0)
                return false;
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //更新列
    private static boolean UpdateList(String tableName, String list, String newFieldType){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();

            String sql="alter table "+tableName+" modify column "+list+" "+newFieldType;
            int count=stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            if(count==0)
                return false;
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //删除列
    private static boolean DeleteList(String tableName, String list){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();

            String sql="alter table "+tableName+" drop column "+list;
            int count=stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            if(count==0)
                return false;
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
