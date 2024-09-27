package shujuku;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
    static String url = "jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db";

    public static Object Select(String clzName, String persistentStorePriKey, Object priKeyValue) {
        // 根据类名获取对应的数据库表名
        String tableName = DataBase.GetTableName(clzName);
        if (tableName != null) {
            // 构造 SQL 查询语句
            String sql = "SELECT * FROM \"" + tableName + "\" WHERE \"" + persistentStorePriKey + "\" = ?";
            try {
                // 加载 SQLite JDBC 驱动
                Class.forName("org.sqlite.JDBC");
                // 连接到数据库
                Connection conn = DriverManager.getConnection(url);
                // 准备 SQL 语句
                PreparedStatement pstmt = conn.prepareStatement(sql);
                // 设置查询条件中的主键值
                pstmt.setObject(1, priKeyValue);
                // 执行查询
                ResultSet rs = pstmt.executeQuery();

                Object result = null;
                // 遍历结果集
                while (rs.next()) {
                    // 列从指定读取字节流
                    String byteStream = rs.getString(ObjReflect.xuliehua);

                    // 将字节流反序列化为对象
                    result = DeserializeFromString(byteStream);
                    // 假设 DeserializeFromString 是一个将字节流转换回对象的方法
                }

                // 关闭结果集、语句和连接
                rs.close();
                pstmt.close();
                conn.close();
                return result;
            } catch (SQLException e) {
                throw new RuntimeException(e); // 处理 SQL 异常
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e); // 处理类未找到异常
            }
        } else {
            return null; // 如果表名为空，则返回 null
        }
    }
    // 第二个函数 Select_obj，传入对象obj
    public static Object Select_obj(Object obj) {
        // 使用反射获取对象的类名
        String clzName = ObjReflect.GetClzName(obj);

        // 从系统表 map 中获取类名对应的表名
        String tableName = DataBase.GetTableName(clzName);

        if (tableName != null) {
            // 构建 SQL 查询语句
            String[][] fields = ObjReflect.GetFields(obj);
            StringBuilder sql = new StringBuilder("SELECT * FROM \"" + tableName + "\" WHERE ");
            List<Object> values = new ArrayList<>();

            for (int i = 0; i < fields.length; i++) { // 修正这里以包括所有字段
                if (fields[i][2] != null) { // 确保有值
                    sql.append("\"").append(fields[i][1]).append("\" = ? AND ");
                    values.add(fields[i][2]); // 使用字段值
                }
            }

            // 去掉最后的 " AND "
            if (sql.length() > 5) {
                sql.setLength(sql.length() - 5);
            }

            Object result = null;
            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());

                // 设置参数
                for (int i = 0; i < values.size(); i++) {
                    pstmt.setObject(i + 1, values.get(i));
                }

                ResultSet rs = pstmt.executeQuery();
                List<Object> results = new ArrayList<>();
                while (rs.next()) {
                    // 从 ResultSet 中读取字节流
                    String byteStream = rs.getString(ObjReflect.xuliehua);

                    // 将字节流反序列化为对象
                    result = DeserializeFromString(byteStream);
                    results.add(result); // 添加反序列化结果到列表
                }

                rs.close();
                pstmt.close();
                conn.close();

                // 返回反序列化结果列表
                return results.isEmpty() ? null : results; // 返回 null 表示没有记录
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null; // 返回 null 表示找不到表名
        }
    }
    // 第三个函数 Select_jk，传入一个实现IPersistentStore接口的对象Object
    public static Object Select_jk(IPersistentStore obj) {
        // 使用接口方法获取对象的主键和主键值
        String primaryKey = obj.getPriKey(); // 获取主键字段名
        Object primaryKeyValue = obj.getPriKeyValue(); // 获取主键值

        // 获取对象的类名，并映射到对应的表名
        String clzName = ObjReflect.GetClzName(obj);

        // 从系统表 map 中获取类名对应的表名
        String tableName = DataBase.GetTableName(clzName);

        if (tableName != null) {
            // 构造 SQL 查询语句，根据主键和主键值查询记录
            String sql = "SELECT * FROM \"" + tableName + "\" WHERE \"" + primaryKey + "\" = ?";

            try {
                // 加载 SQLite JDBC 驱动
                Class.forName("org.sqlite.JDBC");
                // 连接到数据库
                Connection conn = DriverManager.getConnection(url);
                // 准备 SQL 语句
                PreparedStatement pstmt = conn.prepareStatement(sql);
                // 设置查询条件中的主键值
                pstmt.setObject(1, primaryKeyValue);
                // 执行查询
                ResultSet rs = pstmt.executeQuery();

                Object result = null;
                // 遍历结果集
                if (rs.next()) {
                    // 从结果集中读取字节流
                    String byteStream = rs.getString(ObjReflect.xuliehua);

                    // 将字节流反序列化为对象
                    result = DeserializeFromString(byteStream); // 假设 DeserializeFromString 是一个将字节流转换回对象的方法
                }

                // 关闭结果集、语句和连接
                rs.close();
                pstmt.close();
                conn.close();
                return result; // 返回查询结果
            } catch (SQLException e) {
                throw new RuntimeException("SQL Exception occurred", e); // 处理 SQL 异常
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("SQLite JDBC Driver not found", e); // 处理类未找到异常
            }
        } else {
            return null; // 如果没有找到对应的表名返回 null
        }
    }
    // 从字符串表示形式反序列化为对象的方法
    private static Object DeserializeFromString(String byteStream) {
        if (byteStream == null || byteStream.isEmpty()) {
            return null; // 如果字节流为空，返回 null
        }
        try {
            // 按空格分割字节字符串
            String[] byteStrings = byteStream.trim().split(" ");
            byte[] bytes = new byte[byteStrings.length];
            // 将每个字节字符串转换为字节
            for (int i = 0; i < byteStrings.length; i++) {
                bytes[i] = Byte.parseByte(byteStrings[i]);
            }
            // 将字节数组转换为对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            ois.close(); // 关闭对象输入流
            return obj; // 返回反序列化后的对象
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e); // 处理输入输出异常和类未找到异常
        }
    }

    public static void main(String[] args) {
        MainClass instance = new MainClass();
        GtTest one = new GtTest(1,1,"99","gt");
        Object result = instance.Select(ObjReflect.GetClzName(one), one.getPriKey(), one.getPriKeyValue());
        Object result1 = instance.Select_jk(one);
        Object result2 = instance.Select_obj(one);
        // 输出结果
        if (result != null) {
//            System.out.println("查询成功，结果为：" + one);
            System.out.println("查询成功，结果为：" + result.toString());
            System.out.println("查询成功，结果为：" + result1.toString());
            System.out.println("查询成功，结果为：" + result2.toString());
        } else {
            System.out.println("未找到对应的记录。");
        }
    }
}
//执行sql操作

