import shujuku.*;



//实践类，测试代码运行结果
public class Test {
    public static void main(String[] args) {
        Service service=new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        GtTest one = new GtTest(1,1,"99","gt");
        GtTest two = new GtTest(1,2,"90","hf");
        GGG three=new GGG(3,1,111,"kk");
//        service.Add(one);
//        service.Add(two);
//        service.Add(three,"id");
//        GtTest ones = new GtTest(2,1,"9","ggt");
//        service.Update(ones);
        GGG threes=new GGG(11,1,11,"ikun");
        service.Update(threes,"id");
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
