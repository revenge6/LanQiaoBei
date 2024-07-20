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
        TableStructure.url=url;
    }
    public boolean Add(IPersistentStore obj){
        String clzName=ObjReflect.GetClzName(obj);
        String tableName=DataBase.GetTableName(clzName);
        String[][] fields=ObjReflect.GetFields(obj);
        String priKey=obj.getPriKey();
        String priKeyValue= obj.getPriKeyValue();
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
}
