package shujuku;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        //初步测试我的检查列属性是否一致
        DataBase dataBase=new DataBase();
//        dataBase.begintable("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
//        TableRecord record=new TableRecord(dataBase);
//        if(record.checkTabFields()){
//            System.out.println("true");
//        }else {
//            System.out.println("false");
//        }
        ObjReflect objReflect=new ObjReflect();
        System.out.println(objReflect.getClzName(objReflect));

    }
}
