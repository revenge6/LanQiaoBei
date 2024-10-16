package shujuku;
import Examples.GtTest;

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

import static shujuku.TableStructure.url;

public class Service {

    public Service(String url){
        setURL(url);
        DataBase.InitialTable();
        DataBase.InitialKindSwitch();
    }
    public static void setURL(String url){
        DataBase.url=url;
        TableRecord.url=url;
        TableStructure.url =url;
    }
    public boolean Add(IPersistentStore store){
        String clzName=ObjReflect.GetClzName(store);
        String tableName=DataBase.GetTableName(clzName);
        String[][] fields=ObjReflect.GetFields(store);
        String priKey=store.getPriKey();
        //将主键列提前
        String[][] newFields=new String[fields.length][3];
        int t=1;
        for (int i=0;i< fields.length;i++){
            if(fields[i][1].equals(priKey)){
                newFields[0][0]=fields[i][0];
                newFields[0][1]=fields[i][1];
                newFields[0][2]=fields[i][2];
            }else {
                newFields[t][0]=fields[i][0];
                newFields[t][1]=fields[i][1];
                newFields[t++][2]=fields[i][2];
            }
        }
        if(tableName!=""){
            if(!DataBase.CheckTabFields(tableName,newFields)){
                TableStructure.UpdateTable(tableName,newFields);
                //直接重新创建表更稳妥
//                DataBase.DeleteTable(tableName);
//                DataBase.CreateTable(clzName,newFields);
                //tableName=DataBase.GetTableName(clzName);
            }
        }else {
            DataBase.CreateTable(clzName,newFields);
            tableName=DataBase.GetTableName(clzName);
        }
        return TableRecord.insert(tableName,newFields);
    }
    public boolean Add(Object obj,String priKey){
        String clzName=ObjReflect.GetClzName(obj);
        String tableName=DataBase.GetTableName(clzName);
        String[][] fields=ObjReflect.GetFields(obj);
        //将主键列提前
        String[][] newFields=new String[fields.length][3];
        int t=1;
        for (int i=0;i< fields.length;i++){
            if(fields[i][1].equals(priKey)){
                newFields[0][0]=fields[i][0];
                newFields[0][1]=fields[i][1];
                newFields[0][2]=fields[i][2];
            }else {
                newFields[t][0]=fields[i][0];
                newFields[t][1]=fields[i][1];
                newFields[t++][2]=fields[i][2];
            }
        }
        //补：插入额外字节数组列objBytes
        if(tableName!=""){
            if(!DataBase.CheckTabFields(tableName,newFields)){
                //TableStructure.UpdateTable(tableName,newFields);
                //直接重新创建表更稳妥
                DataBase.DeleteTable(tableName);
                DataBase.CreateTable(clzName,newFields);
                tableName=DataBase.GetTableName(clzName);
            }
        }else {
            DataBase.CreateTable(clzName,newFields);
            tableName=DataBase.GetTableName(clzName);
        }
        return TableRecord.insert(tableName,newFields);
    }
    public boolean Delete(IPersistentStore store){
        String clzName=ObjReflect.GetClzName(store);
        String tableName=DataBase.GetTableName(clzName);
        String priKey=store.getPriKey();
        String priKeyValue= store.getPriKeyValue();

        //补：插入额外字节数组列objBytes
        if(tableName=="") {
            System.out.println("还未创建此类的表！");
            return false;
        }
        return TableRecord.Delete(tableName,priKey,priKeyValue);
    }
    public boolean Delete(Object obj){
        String clzName=ObjReflect.GetClzName(obj);
        String tableName=DataBase.GetTableName(clzName);
        String[][] fields=ObjReflect.GetFields(obj);
        //补：插入额外字节数组列objBytes
        if(tableName=="") {
            System.out.println("还未创建此类的表！");
            return false;
        }
        return TableRecord.Delete(tableName,fields);
    }
    public boolean Delete(String clzName,String priKey,String priKeyValue){
        String tableName=DataBase.GetTableName(clzName);
        //补：插入额外字节数组列objBytes
        if(tableName=="") {
            System.out.println("还未创建此类的表！");
            return false;
        }
        return TableRecord.Delete(tableName,priKey,priKeyValue);
    }
    public boolean Update(IPersistentStore store){
        String clzName=ObjReflect.GetClzName(store);
        String tableName=DataBase.GetTableName(clzName);
        String[][] fields=ObjReflect.GetFields(store);
        String priKey=store.getPriKey();
        //String priKeyValue= store.getPriKeyValue();
        //将主键列提前
        String[][] newFields=new String[fields.length][3];
        int t=1;
        for (int i=0;i< fields.length;i++){
            if(fields[i][1].equals(priKey)){
                newFields[0][0]=fields[i][0];
                newFields[0][1]=fields[i][1];
                newFields[0][2]=fields[i][2];
            }else {
                newFields[t][0]=fields[i][0];
                newFields[t][1]=fields[i][1];
                newFields[t++][2]=fields[i][2];
            }
        }
        //补：插入额外字节数组列objBytes
        if(tableName==""){
           return Add(store);
        }else {
            if(!DataBase.CheckTabFields(tableName,newFields)){
//                TableStructure.UpdateTable(tableName,newFields);
                //直接重新创建表更稳妥
                DataBase.DeleteTable(tableName);
                DataBase.CreateTable(clzName,newFields);
                tableName=DataBase.GetTableName(clzName);
                return TableRecord.insert(tableName,newFields);
            }
        }
        return TableRecord.Update(tableName,newFields);
    }
    public boolean Update(Object obj,String priKey){
        String clzName=ObjReflect.GetClzName(obj);
        String tableName=DataBase.GetTableName(clzName);
        String[][] fields=ObjReflect.GetFields(obj);

        //将主键列提前
        String[][] newFields=new String[fields.length][3];
        int t=1;
        for (int i=0;i< fields.length;i++){
            if(fields[i][1].equals(priKey)){
                newFields[0][0]=fields[i][0];
                newFields[0][1]=fields[i][1];
                newFields[0][2]=fields[i][2];
            }else {
                newFields[t][0]=fields[i][0];
                newFields[t][1]=fields[i][1];
                newFields[t++][2]=fields[i][2];
            }
        }
        //补：插入额外字节数组列objBytes
        if(tableName==""){
            return Add(obj,priKey);
        }else {
            if(!DataBase.CheckTabFields(tableName,newFields)){
//                TableStructure.UpdateTable(tableName,newFields);
                //直接重新创建表更稳妥
                DataBase.DeleteTable(tableName);
                DataBase.CreateTable(clzName,newFields);
                tableName=DataBase.GetTableName(clzName);
                return TableRecord.insert(tableName,newFields);
            }
        }
        return TableRecord.Update(tableName,newFields);
    }
    //改主键
    public boolean AlterKey(String clzName,String pastKey,String newKey){
        if(pastKey.equals(newKey)){//重复则直接返回
            return true;
        }
        String tableName=DataBase.GetTableName(clzName);
        TableStructure.AlterKey(clzName,tableName,newKey);
        DataBase.AlterKey(clzName,tableName,pastKey,newKey);
        return true;
    }

    public static Object Select(String clzName, String persistentStorePriKey, Object priKeyValue) {
        String tableName = DataBase.GetTableName(clzName);
        if (tableName != null) {
            String sql = "SELECT * FROM \"" + tableName + "\" WHERE \"" + persistentStorePriKey + "\" = ?";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setObject(1, priKeyValue);
                ResultSet rs = pstmt.executeQuery();

                Object result = null;
                while (rs.next()) {
                    String byteStream = rs.getString(ObjReflect.xuliehua);
                    result = DeserializeFromString(byteStream);
                }
                return result;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static Object Select_obj(Object obj) {
        String clzName = ObjReflect.GetClzName(obj);
        String tableName = DataBase.GetTableName(clzName);
        if (tableName != null) {
            String[][] fields = ObjReflect.GetFields(obj);
            StringBuilder sql = new StringBuilder("SELECT * FROM \"" + tableName + "\" WHERE ");
            List<Object> values = new ArrayList<>();

            for (int i = 0; i < fields.length; i++) {
                if (fields[i][2] != null) {
                    sql.append("\"").append(fields[i][1]).append("\" = ? AND ");
                    values.add(fields[i][2]);
                }
            }

            if (sql.length() > 5) {
                sql.setLength(sql.length() - 5);
            }

            List<Object> results = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

                for (int i = 0; i < values.size(); i++) {
                    pstmt.setObject(i + 1, values.get(i));
                }

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String byteStream = rs.getString(ObjReflect.xuliehua);
                    results.add(DeserializeFromString(byteStream));
                }
                return results.isEmpty() ? null : results;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static Object Select_jk(IPersistentStore obj) {
        String primaryKey = obj.getPriKey();
        Object primaryKeyValue = obj.getPriKeyValue();
        String clzName = ObjReflect.GetClzName(obj);
        String tableName = DataBase.GetTableName(clzName);

        if (tableName != null) {
            String sql = "SELECT * FROM \"" + tableName + "\" WHERE \"" + primaryKey + "\" = ?";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setObject(1, primaryKeyValue);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String byteStream = rs.getString(ObjReflect.xuliehua);
                    return DeserializeFromString(byteStream);
                }
            } catch (SQLException e) {
                throw new RuntimeException("SQL Exception occurred", e);
            }
        }
        return null;
    }

    private static Object DeserializeFromString(String byteStream) {
        if (byteStream == null || byteStream.isEmpty()) {
            return null;
        }
        try {
            String[] byteStrings = byteStream.trim().split(" ");
            byte[] bytes = new byte[byteStrings.length];
            for (int i = 0; i < byteStrings.length; i++) {
                bytes[i] = Byte.parseByte(byteStrings[i]);
            }
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                return ois.readObject();
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
