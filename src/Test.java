import shujuku.*;



//实践类，测试代码运行结果
public class Test {
    public static void main(String[] args) {
        Service service=new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        GtTest one = new GtTest(1,1,"99","gt");
        GtTest two = new GtTest(1,2,"90","hf");
//        GtTest three = new GtTest(1,3,"hf");
        service.Add(one);
        service.Add(two);
//        service.Add(three);
//        GtTest four =new GtTest(6,4,"100","jmy");
//        service.Add(four);
//        GtTest five =new GtTest(7,5,99,"ymj");/**/
//        service.Add(five);
//        service.Add(three,"id");
//        service.Delete(one);
//        service.Delete(ObjReflect.GetClzName(three),"id","1");
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
