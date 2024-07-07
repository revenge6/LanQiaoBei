package shujuku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DatabaseUtils {

    private static final String url = "url";

    // 函数定义
    public boolean insert(String tableName, String[][] fields) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 获取数据库连接
            conn = DriverManager.getConnection(url);

            // 构造插入表SQL语句
            StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            for (int i = 0; i < fields.length; i++) {
                sql.append(fields[i][1]);
                if (i < fields.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") VALUES (");
            for (int i = 0; i < fields.length; i++) {
                sql.append("?");
                if (i < fields.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");

            // 创建 PreparedStatement 对象来执行SQL语句
            pstmt = conn.prepareStatement(sql.toString());

            // 设置参数值
            for (int i = 0; i < fields.length; i++) {
                pstmt.setString(i + 1, fields[i][2]); // 默认字符串类型
            }

            // 执行插入语句
            int count = pstmt.executeUpdate();

            // 检查是否成功插入数据
            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // 输出错误信息
            return false;
        } finally {
            // 关闭连接和语句
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
