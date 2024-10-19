import Examples.GtTest;
import shujuku.*;

import java.util.List;

//实践类，测试代码运行结果
public class Test {
    public static void main(String[] args) {
        Service service = new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        GtTest one = new GtTest(1, 1, "99", "gt1");
        GtTest one1 = new GtTest(2, 2, "99", "gt1");
        GtTest one2 = new GtTest(1, 3, "98", "gt");
        GtTest one3 = new GtTest(2, 4, "98", "gt");
//        service.Add(one);
//        service.Add(one1);
//        service.Add(one2);
//        service.Add(one3);
//        Object result = service.SelectByID(ObjReflect.GetClzName(one), one.getPriKey(), one.getPriKeyValue());
        List<Object> result1 = service.SelectByExample(one,new String[]{"grade"});
//        Object result2 = service.SelectByID(one1);
        for(Object obj:result1){
            System.out.println("查询成功，结果为：" + obj);
        }
//            System.out.println("查询成功，结果为：" + result.toString());
//            System.out.println("查询成功，结果为：" + result1.toString());
//            System.out.println("查询成功，结果为：" + result2.toString());
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
