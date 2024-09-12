package shujuku;

import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainClass {
    static String url = "jdbc:sqlite:D:\\\\java\\\\idea-workspace\\\\LanQiaoBei\\\\test.db";


    // 第一个函数 Select,传入类名主键名主键值
    public Object Select(String clzName, String persistentStorePriKey, Object priKeyValue) {
        String tableName = DataBase.GetTableName(clzName);

        if (tableName != null) {
            // 构建 SQL 查询语句
            String sql = "SELECT * FROM \"" + tableName + "\" WHERE \"" + persistentStorePriKey + "\" = ?";

            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setObject(1, priKeyValue);
                ResultSet rs = pstmt.executeQuery();

                Object result = null;
                if (rs.next()) {
                    result = rs.getObject(1);
                }

                rs.close();
                pstmt.close();
                conn.close();
                return result;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }
    public static void main(String[] args) {
        MainClass instance = new MainClass();
        Object result = instance.Select("shujuku.GtTest", "id", 3);
        System.out.println(result);
    }
/*
    // 第二个函数 Select_obj，传入对象obj
    public Object Select_obj(Object obj) {
        // 使用反射获取对象的类名
        String clzName = obj.getClass().getName();

        // 从系统表 map 中获取类名对应的表名
        String tablename = DataBase.GetTableName(clzName);

        if (tablename != null) {
            // 构造 SQL 查询语句，查询对象的属性值作为条件
            String sql = constructSQL(obj, tablename);

            // 执行查询操作
            Object result = executeQuery(sql);
            return result;//传出查询到的记录
        } else {
            return null; // 如果没有找到对应的表名，返回 null
        }
    }

    // 第三个函数 Select_jk，传入一个实现IPersistentStore接口的对象Object
    public Object Select_jk(IPersistentStore obj) {
        // 使用接口方法获取对象的主键和主键值
        String primaryKey = obj.getPriKey();
        Object primaryKeyValue = obj.getPriKeyValue();

        // 遍历系统表 map 获取表名
        String clzName = obj.getClass().getName();
        String tablename = DataBase.GetTableName(clzName);

        if (tablename != null) {
            // 构造 SQL 查询语句，根据主键和主键值查询记录
            String sql = "SELECT * FROM " + tablename + " WHERE " + primaryKey + " = ?";
            Object result = executeQuery(sql, primaryKeyValue);
            return result;//返回查询结果
        } else {
            return null; // 如果没有找到对应的表名返回null
        }
    }
*/
    //执行sql操作
 /*   private Object executeQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object result = null;

        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 连接数据库
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
            // 准备 SQL 语句
            stmt = conn.prepareStatement(sql);
            // 设置 SQL 参数
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            // 执行查询
            rs = stmt.executeQuery();
            result = rs;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // 实际情况应该根据具体需求处理异常
        } finally {
            // 关闭数据库连接资源
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 构造 SQL 查询语句，根据对象的属性值作为条件
    private String constructSQL(Object obj, String tablename) {
        return "SELECT * FROM " + tablename + " WHERE ..."; // 构造 SQL语句
    }
*/

}


