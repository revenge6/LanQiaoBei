import shujuku.DataBase;
import shujuku.GtTest;
import shujuku.Service;

public class Test {
    public static void main(String[] args) {
        Service service=new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        GtTest one=new GtTest(18,1,98,"gttt");
        GtTest two=new GtTest(19,2,60,"hk");
//        GtTest four=new GtTest(19,4,"32","hk");
//        GtTest three=new GtTest(20,3,"hk");
        Xuliehua xuliehua=new Xuliehua();
//        service.Add(one);
//        service.Add(two);
//        service.Update(one);
//        service.Add(three);
//        service.Add(four);
//        service.Add(xuliehua);
//        service.Delete(two);
//        m();
    }
    public static void m(){
        DataBase.DeleteTable("shujuku$GtTest");
        DataBase.DeleteTable("Xuliehua");
        DataBase.DeleteTable("Map");
        DataBase.DeleteTable("Attribute");
        System.out.println("删除表成功！");
    }
}
