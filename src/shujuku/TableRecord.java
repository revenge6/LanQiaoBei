package shujuku;

import java.sql.*;

public class TableRecord {
    static String url;
    DataBase dataBase;
    TableRecord(DataBase dataBase){
        if(dataBase.url==""||dataBase.url==null)
            return;
        url=dataBase.url;
        this.dataBase=dataBase;
    }

    //插入记录函数——wym
    public static boolean insert(String tableName, String[][] fields) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            // 创建Statement对象来执行SQL语句
            Statement stmt=conn.createStatement();
            // 构造插入表SQL语句

            //这里需要考虑属性不为int时需要加单引号！！！

            String sql = "insert into "+tableName+" (";
            for (int i = 0; i < fields.length; i++) {
                sql+=fields[i][1];
                if (i < fields.length - 1) {
                    sql+=", ";
                }
            }
            sql+=") values (";
            for (int i = 0; i < fields.length; i++) {
                if(fields[i][0]!="int")
                    sql+="'";
                sql+=fields[i][2];
                if(fields[i][0]!="int")
                    sql+="'";
                if (i != fields.length-1) {
                    sql+=",";
                }
            }
            sql+=");";
            // 执行插入语句
            int count=stmt.executeUpdate(sql);
            // 检查是否成功插入数据
            stmt.close();
            conn.close();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // 输出错误信息
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //更新函数——wym
//    public void Update(IPersistentStore store) {
//        try{
//            Class.forName("org.sqlite.JDBC");
//            Connection conn = DriverManager.getConnection(url);
//            // 创建Statement对象来执行SQL语句
//            Statement stmt=conn.createStatement();
//
//            String persistentStorePriKey=store.getPriKey();
//            String priKeyValue=store.getPriKeyValue();
//            ObjReflect objReflect = new ObjReflect();
//            //获取对象类名
//            String clzName=objReflect.GetClzName(store);
//            //获取对象属性数组
//            String[][] fields=objReflect.GetFields(store);
//            //在Map表中找到tableName
//            String str="select tableName from Map where clzName= '"+clzName+"';";
//            ResultSet rs=stmt.executeQuery(str);
//            String tableName="";
//            if (rs.next())
//                tableName=rs.getString("tableName");
//
//            String sql="select "+persistentStorePriKey+" from "+tableName+" where persistentStorePriKey='"+priKeyValue+"';";
//            ResultSet rs1=stmt.executeQuery(str);
//            //根据结果集结果判断是否类表中有这一行元素
//            if (rs1.next()){
//                String upd = "update " + clzName + "set ";
//                for(int i=0;i<fields[0].length;i++){
//                    upd+=fields[i][1]+"="+fields[i][2];
//                    if(i!=fields[0].length)
//                        upd+=",";
//                }
//                upd+=" where persistentStorePriKey='" + priKeyValue +"';";
//                stmt.executeUpdate(upd);
//            }{
//                //如果没有则执行插入
//                insert(clzName,fields);
//            }
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public void Update(Object obj, String persistentStorePriKey, String priKeyValue) {
//        try{
//            Class.forName("org.sqlite.JDBC");
//            Connection conn = DriverManager.getConnection(url);
//            // 创建Statement对象来执行SQL语句
//            Statement stmt=conn.createStatement();
//
//            ObjReflect objReflect = new ObjReflect();
//            //获取对象类名
//            String clzName=objReflect.GetClzName(obj);
//            //获取对象属性数组
//            String[][] fields=objReflect.GetFields(obj);
//            //在Map表中找到tableName
//            String str="select tableName from Map where clzName= '"+clzName+"';";
//            ResultSet rs=stmt.executeQuery(str);
//            String tableName="";
//            if (rs.next())
//                tableName=rs.getString("tableName");
//
//            String sql="select "+persistentStorePriKey+" from "+tableName+" where persistentStorePriKey='"+priKeyValue+"';";
//            ResultSet rs1=stmt.executeQuery(str);
//            //根据结果集结果判断是否类表中有这一行元素
//            if (rs1.next()){
//                String upd = "update " + clzName + "set ";
//                for(int i=0;i<fields[0].length;i++){
//                    upd+=fields[i][1]+"="+fields[i][2];
//                    if(i!=fields[0].length)
//                        upd+=",";
//                }
//                upd+=" where persistentStorePriKey='" + priKeyValue +"';";
//                stmt.executeUpdate(upd);
//            }{
//                //如果没有则执行插入
//                insert(clzName,fields);
//            }
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
//删除一个对象——lab
//
//public void Delete(IPersistentStore store) {
//    try {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(url);
//        Statement stmt = conn.createStatement();
//
//        String persistentStorePriKey = store.getPriKey();
//        String priKeyValue = store.getPriKeyValue();
//        ObjReflect objReflect = new ObjReflect();
//        String clzName = objReflect.GetClzName(store);
//
//        // 获取tableName
//        String str = "select tableName from Map where clzName='" + clzName + "';";
//        ResultSet rs = stmt.executeQuery(str);
//        String tableName = "";
//        if (rs.next()) {
//            tableName = rs.getString("tableName");
//        }
//
//        // 构建删除SQL语句
//        String sql = "delete from " + tableName + " where " + persistentStorePriKey + "='" + priKeyValue + "'";
//        stmt.executeUpdate(sql);
//
//        stmt.close();
//        conn.close();
//    } catch (SQLException e) {
//        throw new RuntimeException(e);
//    } catch (ClassNotFoundException e) {
//        throw new RuntimeException(e);
//    }
//}
//
//public void Delete(Object obj, String persistentStorePriKey, String priKeyValue) {
//    try {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(url);
//        Statement stmt = conn.createStatement();
//
//        ObjReflect objReflect = new ObjReflect();
//        String clzName = objReflect.GetClzName(obj);
//
//        // 获取tableName
//        String str = "select tableName from Map where clzName='" + clzName + "';";
//        ResultSet rs = stmt.executeQuery(str);
//        String tableName = "";
//        if (rs.next()) {
//            tableName = rs.getString("tableName");
//        }
//
//        // 构建删除SQL语句
//        String sql = "delete from " + tableName + " where " + persistentStorePriKey + "='" + priKeyValue + "'";
//        stmt.executeUpdate(sql);
//
//        stmt.close();
//        conn.close();
//    } catch (SQLException e) {
//        throw new RuntimeException(e);
//    } catch (ClassNotFoundException e) {
//        throw new RuntimeException(e);
//    }
//}
//
//public void Delete(Class<?> clz, String persistentStorePriKey, String priKeyValue) {
//    try {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(url);
//        Statement stmt = conn.createStatement();
//
//        // 获取tableName
//        String str = "select tableName from Map where clzName='" + clz.getName() + "';";
//        ResultSet rs = stmt.executeQuery(str);
//        String tableName = "";
//        if (rs.next()) {
//            tableName = rs.getString("tableName");
//        }
//
//        // 构建删除SQL语句
//        String sql = "delete from " + tableName + " where " + persistentStorePriKey + "='" + priKeyValue + "'";
//        stmt.executeUpdate(sql);
//
//        stmt.close();
//        conn.close();
//    } catch (SQLException e) {
//        throw new RuntimeException(e);
//    } catch (ClassNotFoundException e) {
//        throw new RuntimeException(e);
//    }
//}
