package shujuku;

public class Service {

    public Service(String url){
        setURL(url);
        DataBase.InitialTable();
    }
    public static void setURL(String url){
        DataBase.url=url;
        TableRecord.url=url;
        TableStructure.url=url;
    }
    public boolean Add(IPersistentStore obj){
        String clzName=ObjReflect.GetClzName(obj);
        String tableName=DataBase.GetTableName(clzName);
        String[][] fields=ObjReflect.GetFields(obj);
        String priKey=obj.getPriKey();
        String priKeyValue= obj.getPriKeyValue();
        //补：插入额外字节数组列objBytes

        if(tableName!=""){
            if(!DataBase.CheckTabFields(tableName,fields)){
                TableStructure.UpdateTable(tableName,fields);
            }
        }else {
            DataBase.CreateTable(clzName,fields);
            tableName=DataBase.GetTableName(clzName);
        }

        return TableRecord.insert(tableName,fields);
    }
}
