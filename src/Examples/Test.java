package Examples;

import SqliteJavaCRUD.*;

import java.util.List;

//实践类，测试代码运行结果
public class Test {
    public static void main(String[] args) {
        //创建Service主控类对象service
        Service service = new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        //模拟构造GtTest和GGG类的对象
        GtTest[] objs1=new GtTest[3];
        GGG[] objs2=new GGG[3];
        for (int i=0;i<3;i++){
            objs1[i]=new GtTest(i,i,(i+90)+"","a"+i);
            objs2[i]=new GGG(i,i,i+90,"a"+i);
        }
        //存储对象
        for (int i=0;i<3;i++){
            boolean flag1=service.Add(objs1[i]);
            boolean flag2=service.Add(objs2[i],"id");
            if (!flag1){
                System.out.println("GtTest对象"+i+"添加失败");
            }
            if (!flag2){
                System.out.println("GGG对象"+i+"添加失败");
            }
        }
        //修改信息
        for (int i=0;i<3;i++){
            objs1[i].age+=10;//GtTest所有对象年龄增加10岁
            objs2[i].grade+=10;//GGG所有对象成绩增加10分
        }
        for (int i=0;i<3;i++){
            service.Update(objs1[i]);
            service.Update(objs2[i],"id");
        }
        //删除某些对象信息
        service.Delete(objs1[0]);
        service.Delete(objs2[0]);
        //查询对象信息
        System.out.println(service.SelectByID(objs1[0]));//该对象已经被删除，输出为null
        System.out.println(service.SelectByID(objs1[1]));
        System.out.println(service.SelectByID(objs1[2]));
        System.out.println("==========================");
        //该对象已经被删除，输出为null
        System.out.println(service.SelectByID(ObjReflect.GetClzName(objs2[0]),"id","0"));
        //后面两个对象都存在
        System.out.println(service.SelectByID(ObjReflect.GetClzName(objs2[0]),"id","1"));
        System.out.println(service.SelectByID(ObjReflect.GetClzName(objs2[0]),"id","2"));
        System.out.println("===========================");

        service.Add(new GtTest(11,3,"93","a3"));
        List<Object> list = service.SelectByExample(objs1[1],new String[]{"age"});
        for (Object obj: list){
            System.out.println(obj);
        }
    }
}
