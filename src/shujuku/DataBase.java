package shujuku;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataBase {
    static String url = "jdbc:sqlite:\\D:\\java\\idea-workspace\\LanQiaoBei\\test.db";//数据库链接

    public DataBase(String url) {
        this.url = url;
        InitialTable();
    }

    public static boolean DeleteTable(String tableName){//系统表函数，删除系统表中对应数据表
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //构造删除表语句并执行
            String sql="drop table "+tableName+";";
            stmt.executeUpdate(sql);
            if(tableName=="Map"||tableName=="Attribute")
                return true;
            String clzName=GetClzName(tableName);
            String sqlDM="delete from Map where tableName ='"+tableName+"';";
            stmt.executeUpdate(sqlDM);
            String sqlDA="delete from Attribute where clzName='"+clzName+"';";
            stmt.executeUpdate(sqlDA);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("删除表失败，表不存在！");
            return false;
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
    public static String GetTableName(String clzName) {//查询系统表中是否存在数据表函数
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
    public static String GetClzName(String tableName) {//在系统表里面查询获得类名函数
        try {
            //加载驱动类
            Class.forName("org.sqlite.JDBC");
            // 创建数据库连接
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt = conn.createStatement();
            //在Map表中找到clzName
            String str = "select clzName from Map where tableName='" + tableName + "';";
            ResultSet rs = stmt.executeQuery(str);
            String clzName = "";
            if (rs.next())
                clzName = rs.getString("clzName");
            stmt.close();
            conn.close();
            return clzName;
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

            // 创建MAP表SQL语句并执行
            String sqlM = "CREATE TABLE IF NOT EXISTS Map (" +
                    "clzName varchar(255) PRIMARY KEY, " +
                    "tableName varchar(255) NOT NULL " +
                    ")";
            stmt.executeUpdate(sqlM);
            //创建属性表SQL语句并执行
            String sqlA = "CREATE TABLE IF NOT EXISTS Attribute (" +
                    "clzName varchar(255) not null , " +
                    "attitudeName varchar(255)  NOT NULL, " +
                    "isKey tinyint(1) not null DEFAULT 0," +
                    "attitudeType varchar(100)," +
                    "FOREIGN KEY (clzName) REFERENCES Map(clzName)," +
                    "primary key(clzName,attitudeName)" +
                    ")";
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

    //类型转换器
    public static Map<String,String> kindSwitch=new LinkedHashMap<>();
    public static void InitialKindSwitch(){
        kindSwitch.put("int","INTEGER");
        kindSwitch.put(ObjReflect.GetClzName((int)1),"INTEGER");
        kindSwitch.put(ObjReflect.GetClzName("1"),"TEXT");
        kindSwitch.put(ObjReflect.GetClzName((double)1.0),"REAL");
        kindSwitch.put(ObjReflect.GetClzName((long)1),"INTEGER");
    }
    //创建表函数
    public static boolean CreateTable(String clzName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //表名转换
            String tableName = switchTableName(clzName);
            // 创建类数据表SQL语句并执行
            String sql = "create table if not exists " + tableName + " (";
            for (int i = 0; i < fields.length; i++) {
                //数据类型转换！
                String kind=kindSwitch.get(fields[i][0])!=null?kindSwitch.get(fields[i][0]):"NONE";
                if (i == 0)
                    sql = sql + fields[i][1] + " " + kind + " primary key,";
                else if (i == fields.length - 1)
                    sql = sql + fields[i][1] + " " + kind + ");";
                else
                    sql = sql + fields[i][1] + " " + kind + ",";
            }


            String sql1="ALTER TABLE "+tableName+" ADD COLUMN Byte_Stream BLOB ;";//对于创建好的表增加一列字节流属性
            stmt.execute(sql);
            stmt.execute(sql1);


            // 关闭资源
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            System.out.println("创建表失败！"+e.getMessage());
        }
        //更新系统表
        return UpdateSystemTable(fields, clzName, switchTableName(clzName));
    }
    //创建表函数？？
    public static boolean CreateTempTable(String clzName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //表名转换
            String tableName = "temp_"+switchTableName(clzName);
            // 创建类数据表SQL语句并执行
            String sql = "create table if not exists " + tableName + " (";
            for (int i = 0; i < fields.length; i++) {
                //数据类型转换！
                String kind=kindSwitch.get(fields[i][0])!=null?kindSwitch.get(fields[i][0]):"NONE";
                if (i == 0)
                    sql = sql + fields[i][1] + " " + kind + " primary key,";
                else if (i == fields.length - 1)
                    sql = sql + fields[i][1] + " " + kind + ");";
                else
                    sql = sql + fields[i][1] + " " + kind + ",";
            }
            String sql1="ALTER TABLE "+tableName+" ADD COLUMN Byte_Stream BLOB ;";//对于创建好的表增加一列字节流属性
            stmt.execute(sql);
            stmt.execute(sql1);
            // 关闭资源
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            System.out.println("创建表失败！"+e.getMessage());
        }
        //更新系统表
        return UpdateSystemTable(fields, clzName, switchTableName(clzName));
    }
    //更新Map和Attribute
    //更新属性表 INSERT INTO 表名 (列1, 列2, 列3, ...)
    //VALUES
    //    (值1_1, 值1_2, 值1_3, ...),
    //    (值2_1, 值2_2, 值2_3, ...),
    //    ...
    //    (值N_1, 值N_2, 值N_3, ...);
    public static boolean UpdateSystemTable(String[][] fields,String clzName,String tableName) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            //更新Map表
            String sql = "INSERT INTO Map(clzName,tableName) VALUES ('" + clzName + "','" + tableName + "');";
            stmt.executeUpdate(sql);
            //更新Attribute表
            String sql1 = "INSERT INTO Attribute (clzName, attitudeName, isKey, attitudeType) VALUES ";
            sql1 += "('" + clzName + "','" + fields[0][1] + "'," + "1,'" + fields[0][0] + "');";
            stmt.executeUpdate(sql1);
            for (int i = 1; i < fields.length; i++) {
                String sql2 = "INSERT INTO Attribute (clzName, attitudeName, isKey, attitudeType) VALUES ";
                sql2 += "('" + clzName + "','" + fields[i][1] + "'," + "0,'" + fields[i][0] + "');";
                stmt.executeUpdate(sql2);
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
    public static boolean CheckTabFields(String tableName,String[][] fields) {
        try {
            //连接数据库
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String clzName=GetClzName(tableName);
            //1.获取类对应的类表的列头
            ResultSet resultSet = stmt.executeQuery("select attitudeName,attitudeType from Attribute where clzName='"+clzName+"'");
            Map<String,String> columns=new LinkedHashMap<>();
            //遍历结果集并将列头和列数据类型保存到数组中
            while (resultSet.next()) {
                columns.put(resultSet.getString("attitudeName"),resultSet.getString("attitudeType"));
            }
            //2.先判断是否属性数目想同，以免遍历出现数组越界
            if (columns.size() != fields.length) {
                return false;
            }
            //3.检查属性类型是否一致
            for (int j = 0; j < columns.size(); j++) {
                //通过以field的每个属性名为键，寻找对应的值，如果不一致则说明：(1)属性类型不同；(2)属性不存在,值为null
                if (!columns.get(fields[j][1]).equals(fields[j][0])) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println(tableName+"不存在!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("找不到JDBC驱动类：" + e.getMessage());
            e.printStackTrace();
        }
        return false;
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
                String sql="insert into Attribute values('"+clzName+"','"+newCols[i][1]+"','"+"0"+"','"+newCols[i][0]+"');";
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
    public static boolean Del_column(String clzName,String column){
        //delete from 表名 where 列名  = 值;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            String sql="delete from Attribute where clzName='"+clzName+"' and attitudeName='"+column +"';";
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
    public static boolean Update_datatype(String clzName,String field,String newType){
        //update 表名 set 列名 = 值 where 列名=值
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            String sql="update Attribute set attitudeType= '"+newType+"' where clzName='"+clzName+"' and attitudeName='"+field +"';";
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
    //改主键-1
    public static boolean AlterKey(String clzName,String tableName,String pastKey,String newKey){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();//数据库连接
            String sqlAK1="update "+tableName+" set isKey= '0' where clzName = '"+clzName+"' and attitudeName = '"+pastKey+"';";
            String sqlAK2="update "+tableName+" set isKey= '1' where clzName = '"+clzName+"' and attitudeName = '"+newKey+"';";
            stmt.executeUpdate(sqlAK1);
            stmt.executeUpdate(sqlAK2);
            stmt.close();
            conn.close();
            return true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean Add_columns(String clzname, String[][] newCols) {
        Connection conn = null;
        Statement stmt = null;
        boolean add_columns = false;
        try {
            // 注册SQLite JDBC驱动
            Class.forName("org.sqlite.JDBC");
            // 连接到数据库
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String tablename=DataBase.GetTableName(clzname);

            // 循环处理每个要添加的列
            for (int i = 0; i < newCols.length; i++) {
                String colType = newCols[i][0];
                String colName = newCols[i][1];

                // 构建添加列的SQL语句
                String addColumnSql = "ALTER TABLE " + tablename + " ADD COLUMN " + colName + " " + colType;
                // 执行SQL语句
                stmt.executeUpdate(addColumnSql);

                // 如果成功添加列，修改主键属性
                if (colName.equals("primary_key_column_name")) {
                    String setPrimaryKeySql = "UPDATE clz_table SET isprimaryKey = true WHERE column_name = '" + colName + "'";
                    stmt.executeUpdate(setPrimaryKeySql);
                }
            }

            add_columns = true; // 操作成功返回值为true
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接和资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return add_columns; // 返回操作结果
    }

    public static void main(String[] args) {
        // 测试
        String clzname = "shujuku.GtTest"; // clzname
        String[][] newCols = {
                {"INT", "new_column1"}, // 添加一个名为new_column1，类型为INT的列
        };

        boolean result = Add_columns(clzname, newCols);
        if (result) {
            System.out.println("列添加成功");
        } else {
            System.out.println("列添加失败");
        }
    }
}
