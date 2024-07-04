package shujuku;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class update {
   String yourUrl, yourUsername, yourPassword;
   //1.修改主键
   public void  AlterKey(String pastkey,String newkey,String clzName){
       String dropPrimaryKeySQL = "ALTER TABLE clzName DROP PRIMARY KEY";
       try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
            Statement stmt = conn.createStatement()) {
           stmt.executeUpdate(dropPrimaryKeySQL);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       String addPrimaryKeySQL = "ALTER TABLE clzName ADD PRIMARY KEY (newkey)";
       try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);
            Statement stmt = conn.createStatement()) {
           stmt.executeUpdate(addPrimaryKeySQL);
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
    //在clzname表操作对updateCols数据类型修改，updateCols[][]存什么？
    //列名，表名
  public void   AlterType(String updateCol,String clzName){//将clzname表的updateCol列的数据类型改成int
      String dropPrimaryKeySQL = "ALTER TABLE clzName modify column updateCol int";
      try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
           Statement stmt = conn.createStatement()) {
          stmt.executeUpdate(dropPrimaryKeySQL);
      } catch (SQLException e) {
          e.printStackTrace();
      }

  }
}
