import Examples.GtTest;
import shujuku.*;

//实践类，测试代码运行结果
public class Test {
    public static void main(String[] args) {
        Service service = new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        GtTest one = new GtTest(1, 1, "99", "gt");
//        service.Add(one);
        Object result = service.Select(ObjReflect.GetClzName(one), one.getPriKey(), one.getPriKeyValue());
        Object result1 = service.Select_jk(one);
        Object result2 = service.Select_obj(one);

        if (result != null) {
            System.out.println("查询成功，结果为：" + result.toString());
            System.out.println("查询成功，结果为：" + result1.toString());
            System.out.println("查询成功，结果为：" + result2.toString());
        } else {
            System.out.println("未找到对应的记录。");
        }
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
