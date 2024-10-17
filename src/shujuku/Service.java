package shujuku;

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
        return TableRecord.Add(tableName,newFields);
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
        return TableRecord.Add(tableName,newFields);
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
                return TableRecord.Add(tableName,newFields);
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
                return TableRecord.Add(tableName,newFields);
            }
        }
        return TableRecord.Update(tableName,newFields);
    }
    public boolean AlterKey(String clzName,String pastKey,String newKey){
        if(pastKey.equals(newKey)){//重复则直接返回
            return true;
        }
        String tableName=DataBase.GetTableName(clzName);
        TableStructure.AlterKey(clzName,tableName,newKey);
        DataBase.AlterKey(clzName,tableName,pastKey,newKey);
        return true;
    }
    public Object Select(String clzName, String persistentStorePriKey, String priKeyValue) {
        String tableName = DataBase.GetTableName(clzName);
        if (tableName !="") {
            return TableRecord.Search(tableName,persistentStorePriKey,priKeyValue);
        }else {
            return null;
        }
    }
    public Object Select_obj(Object obj) {
        String clzName = ObjReflect.GetClzName(obj);
        String[][] fields=ObjReflect.GetFields(obj);
        String tableName = DataBase.GetTableName(clzName);
        if (tableName !="") {
            return TableRecord.Search(tableName,fields);
        }else {
            return null;
        }
    }
    public Object Select_jk(IPersistentStore obj) {
        String clzName = ObjReflect.GetClzName(obj);
        String tableName = DataBase.GetTableName(clzName);
        String persistentStorePriKey= obj.getPriKey();
        String priKeyValue= obj.getPriKeyValue();
        if (tableName !="") {
            return TableRecord.Search(tableName,persistentStorePriKey,priKeyValue);
        }else {
            return null;
        }
    }

}
