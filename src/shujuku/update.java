package shujuku;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class update {
    String primaryKeyColumn;
    int primaryKeyValue;
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
      String updateKeySQL = "ALTER TABLE clzName modify column updateCol int";
      try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
           Statement stmt = conn.createStatement()) {
          stmt.executeUpdate(updateKeySQL);
      } catch (SQLException e) {
          e.printStackTrace();
      }

  }
  public  void Update(Object obj, String persistentStorePriKey, int priKeyValue){
ObjReflect a=new ObjReflect();
String b[][]=a.getFields(obj);
TableRecord c=new TableRecord(primaryKeyColumn,primaryKeyValue);
String clzname=c.clzName;
if(priKeyValue!=primaryKeyValue){
    String insert="insert into"+clzname +"values";
    for(int i=0;i<a.fieldValue.length;i++){
        insert+=a.fieldValue[i];
    }
    try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(insert);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
else {
    String upd="update"+ clzname+"set"+primaryKeyValue+"="+priKeyValue+"where primarykey="+primaryKeyValue;
    try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(upd);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  }
public void Update(IPersistentStore store){
      String prikey= store.getPriKey();//主键名
      String  prikeyvalue= (String) store.getPriKeyValue();//主键值
      String classname=store.getClass().getName();//类名-->表名
ObjReflect fs=new ObjReflect();
fs.getFields(store);
boolean flag=true;
for(int i=0;i<fs.fieldValue.length;i++){
    if(fs.fieldValue[i]==prikeyvalue){
        String deletesql="delete from "+classname+"where primaryKeyValue="+prikeyvalue;
        try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(deletesql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insert="insert into"+classname +"values";
        for(int d=0;d<fs.fieldValue.length;d++){
            insert+=fs.fieldValue[d];
        }
        try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        flag=false;
        break;
    }
}
if(flag==true){
    String insert="insert into"+classname +"values";
    for(int i=0;i<fs.fieldValue.length;i++){
        insert+=fs.fieldValue[i];
    }
    try (Connection conn = DriverManager.getConnection(yourUrl, yourUsername, yourPassword);//登录数据库
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(insert);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
}
