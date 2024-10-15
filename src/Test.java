import Examples.GGG;
import Examples.GtTest;
import shujuku.*;



//实践类，测试代码运行结果
public class Test {
    public static void main(String[] args) {
        Service service=new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        GtTest one = new GtTest(1,1,"99","gt");
        GGG two = new GGG(1,2,90,"hf");
//        service.Add(one);
//        service.Add(two,"id");
        service.AlterKey(ObjReflect.GetClzName(new GtTest(1,1,"1","name")),"id","age");
        service.AlterKey(ObjReflect.GetClzName(new GGG(1,1,1,"name")),"id","age");
    }
    public static void m(){
        DataBase.DeleteTable("shujuku$GGG");
        DataBase.DeleteTable("shujuku$GtTest");
        DataBase.DeleteTable("Xuliehua");
        DataBase.DeleteTable("XXX");
        DataBase.DeleteTable("Map");
        DataBase.DeleteTable("Attribute");
        System.out.println("删除表成功！");
    }
}
