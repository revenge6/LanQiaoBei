package shujuku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DatabaseUtils {

    private static final String url = "url";
    private String[][] fields;
    // 假设主键列名称是一个字符串
    private String primaryKeyColumn;
    public String getPriKey() {
        return this.primaryKeyColumn;
    }

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
    public void swapRow() {
        String primaryKey = getPriKey(); // 获取主键值

        // 找到主键所在的行索引
        int rowIndex = findRow(primaryKey);

        if (rowIndex == -1) {
            System.out.println("主键值未找到或数组为空");
            return;
        }

        // 交换找到的行与第一行的数据
        swapRows(0, rowIndex);
    }

    // 根据主键查找对应的行索引
    private int findRow(String primaryKey,String[][] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i][1].equals(primaryKey)) {
                return i; // 返回主键所在行的索引
            }
        }
        return -1; // 没有找到主键值，返回-1
    }

    // 交换数组的两行数据
    private void swapRows(int row1, int row2) {
        String[] temp = fields[row1];
        fields[row1] = fields[row2];
        fields[row2] = temp;
    }
}

