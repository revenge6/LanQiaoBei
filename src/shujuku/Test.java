package shujuku;

public class Test {
    //测试
    public static void main(String[] args) throws IllegalAccessException {
        DataBase dataBase=new DataBase();
        dataBase.begintable("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        TableRecord record=new TableRecord(dataBase);
        if(record.checkTabFields()){
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }
}
