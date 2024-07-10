import shujuku.DataBase;
import shujuku.GtTest;
import shujuku.Service;

public class Test {
    public static void main(String[] args) {
        Service service=new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        DataBase.InitialTable();
        GtTest one=new GtTest(18,1,98);
        GtTest two=new GtTest(19,2,60);
        service.Add(one);
        service.Add(two);
//        m();
    }
    public static void m(){
        DataBase.DeleteTable("Map");
        DataBase.DeleteTable("Attribute");
        DataBase.DeleteTable("shujuku$GtTest");
    }
}
