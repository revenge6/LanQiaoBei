package shujuku;

import java.sql.*;

public class DataBase {
    static String url = "";//数据库链接

    public DataBase(String url) {
        this.url = url;
        InitialTable();
    }

    public static boolean DeleteTable(String tableName){
        try{
            //加载驱动类
            Class.forName("org.sqlite.JDBC");
            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            String sql="drop table "+tableName+";";
            boolean flag=stmt.execute(sql);

            stmt.close();
            conn.close();
            return flag;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //    public static boolean TableExist(String tableName){
//        try{
//            Class.forName("org.sqlite.JDBC");
//            // 创建数据库连接
//            Connection conn = DriverManager.getConnection(url);
//            // 创建Statement对象来执行SQL语句
//            Statement stmt = conn.createStatement();
//
//            String sql="select name FROM sqlite_master WHERE type='table' AND name='"+ tableName +"';";
//            if(stmt.execute(sql)){
//                stmt.close();
//                conn.close();
//                return false;
//            }
//            stmt.close();
//            conn.close();
//            return true;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public static String GetTableName(String clzName) {
        try {
            //加载驱动类
            Class.forName("org.sqlite.JDBC");
            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();
            //在Map表中找到tableName
            String str = "select tableName from Map where clzName='" + clzName + "';";
            ResultSet rs = stmt.executeQuery(str);
            String tableName = "";
            if (rs.next())
                tableName = rs.getString("tableName");
            stmt.close();
            conn.close();
            return tableName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //系统表初始化函数————jmy
    public static void InitialTable() {
        try {
            //加载驱动类
            Class.forName("org.sqlite.JDBC");
            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            // 创建MAP表SQL语句
            String sqlM = "CREATE TABLE IF NOT EXISTS Map (" +
                    "clzName varchar(255) PRIMARY KEY, " +
                    "tableName varchar(255) NOT NULL " +
                    ")";
            // 执行SQL语句
            stmt.executeUpdate(sqlM);
            //创建属性表
            String sqlA = "CREATE TABLE IF NOT EXISTS Attribute (" +
                    "clzName varchar(255) not null , " +
                    "attitudeName varchar(255)  NOT NULL, " +
                    "isKey tinyint(1) not null DEFAULT 0," +
                    "attitudeType varchar(100)," +
                    "FOREIGN KEY (clzName) REFERENCES Map(clzName)," +
                    "primary key(clzName,attitudeName)" +
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

    //创建表函数——表名转换
    public static String switchTableName(String clzName) {
        String tableName = clzName.replace('.', '$');
        return tableName;
    }

    //创建表函数
    public static boolean CreateTable(String clzName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();
            //表名转换并存到map表语句
            String tableName = switchTableName(clzName);
            // 创建类数据表SQL语句
            String sql = "create table if not exists " + tableName + " (";
            for (int i = 0; i < fields.length; i++) {
                if (i == 0)
                    sql = sql + fields[i][1] + " " + fields[i][0] + " primary key,";
                else if (i == fields.length - 1)
                    sql = sql + fields[i][1] + " " + fields[i][0] + ");";
                else
                    sql = sql + fields[i][1] + " " + fields[i][0] + ",";
            }
            if (fields.length == 1)
                sql.replace(",", ");");
            // 执行SQL语句
            stmt.execute(sql);
            // 关闭资源
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        if (UpdateSystemTable(fields, clzName, switchTableName(clzName)))
            return true;
        else
            return false;
    }

    //创建表后更新Map和Attribute
    public static boolean UpdateSystemTable(String[][] fields, String clzName, String tableName) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            // 更新Map表
            String sql = "insert into Map(clzName,tableName) values('" + clzName + "','" + tableName + "');";
            stmt.executeUpdate(sql);
            //更新属性表 INSERT INTO 表名 (列1, 列2, 列3, ...)
            //VALUES
            //    (值1_1, 值1_2, 值1_3, ...),
            //    (值2_1, 值2_2, 值2_3, ...),
            //    ...
            //    (值N_1, 值N_2, 值N_3, ...);

            for (int i = 0; i < fields.length; i++) {
                // 执行SQL语句
                String sql1 = "insert into Attribute (clzName, attitudeName, isKey, attitudeType) values ";
                sql1 += "('" + clzName + "','" + fields[i][1] + "'," + "0,'" + fields[i][0] + "');";
                stmt.executeUpdate(sql1);
            }
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

    //检查列属性是否一致类(考虑顺序)--gt

    public static boolean CheckTabFields(String tableName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();

            //1.获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("pragma table_info( " + tableName + " )");
            // 获取结果集的列数
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 创建二维字符串数组来保存列头和列数据类型
            String[][] tableMetadata = new String[columnCount][2];
            // 遍历结果集并将列头和列数据类型保存到数组中
            int row = 0;
            while (resultSet.next()) {
                tableMetadata[row][0] = resultSet.getString("type");  // 列头
                tableMetadata[row][1] = resultSet.getString("name");  // 列数据类型
                row++;
            }
            //2.先判断是否属性数目想同，以免遍历出现数组越界
            if (row != fields[0].length) {
                return false;
            }
            for (int j = 0; j < row; j++) {
                //检查属性类型是否一致
                if (!tableMetadata[j][0].equals(fields[j][0])) {
                    return false;
                }
            }
            for (int j = 0; j < row; j++) {
                //检查属性名是否一致
                if (!tableMetadata[j][1].equals(fields[j][1])) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //系统表更新系列函数  蒋梦圆
    //1 增加一列的系统表更新  这一列不能是主键
    public static boolean Add_columns(String clzName,String[][] newCols,String fields[][]){
        //insert into 表名 values(值1,值2,...值n);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            for(int i=0;i<fields.length;i++) {
                String sql="insert into Attribute values("+clzName+","+newCols[i][1]+","+"0"+newCols[i][0]+")"+";";
                stmt.executeUpdate(sql);
            }
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    //2 删除列
    public static boolean Del_columns(String clzName,String[][] newCols,String[][] fields){
        //delete from 表名 where 列名  = 值;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            for(int i=0;i<fields.length;i++) {
                String sql="delete from Attribute where clzName="+clzName+"and attitudeName="+newCols[i][1] +";";
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public static boolean Update_datatype(String clzName,String[][] updateCols,String[][] fields){
        //update 表名 set 列名 = 值 where 列名=值
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            for(int i=0;i<fields.length;i++) {
                String sql="delete from Attribute set attitudeType= '"+updateCols[i][0]+"' where clzName="+clzName+" and attitudeName="+updateCols[i][1] +";";
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            }

        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public static boolean Del_table(String tableName){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            String sql="drop table "+tableName+";";
            stmt.executeUpdate(sql);
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
