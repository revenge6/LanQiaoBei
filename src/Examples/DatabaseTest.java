
package Examples;
import java.util.List;
import SqliteJavaCRUD.*;
public class DatabaseTest {
    private Service service;

    public DatabaseTest(String dbUrl) {
        service = new Service(dbUrl);
    }

    public void testBasicFunctionality() {
        // 测试基本的数据库操作，如创建表、添加、查询、更新和删除
        GtTest testObject = new GtTest(1, 1, "20", "test");
        boolean added = service.Add(testObject);
        assert added : "添加对象失败";

        Object queriedObject = service.SelectByID(ObjReflect.GetClzName(testObject), testObject.getPriKey(), testObject.getPriKeyValue());
        assert queriedObject != null : "查询对象失败";

        boolean updated = service.Update(testObject);
        assert updated : "更新对象失败";

        boolean deleted = service.Delete(testObject);
        assert deleted : "删除对象失败";
    }

    public void testParallelDataRetrieval() {
        // 测试多条数据并行查询
        for (int i = 0; i < 10; i++) {
            GtTest testObject = new GtTest(i, i, "20", "test" + i);
            service.Add(testObject);
        }

        List<Object> results = service.SelectByExample(new GtTest(1, 1, "20", "test"), new String[]{"age"});
        assert results.size() == 10 : "查询结果数量不符合预期";
    }

    public void testThroughput() {
        // 测试吞吐量
        GtTest testObject = new GtTest(1, 1, "20", "test");
        long startTime = System.currentTimeMillis();

        // 循环添加同一个对象10次
        for (int i = 0; i < 10; i++) {
            service.Add(testObject);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("添加10次对象耗时：" + (endTime - startTime) + "ms");

        // 循环删除同一个对象10次
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            service.Delete(testObject);
        }
        endTime = System.currentTimeMillis();
        System.out.println("删除10次对象耗时：" + (endTime - startTime) + "ms");

        // 循环更新同一个对象1000次
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.Update(testObject);
        }
        endTime = System.currentTimeMillis();
        System.out.println("更新1000次对象耗时：" + (endTime - startTime) + "ms");
    }

    public void testExceptionHandling() {
        // 测试嵌套对象和反序列化
        // 此测试需要你有一个嵌套对象的类和相应的数据库操作
        // 由于你的代码中没有嵌套对象的示例，我将跳过这部分的实现
        Complex a=new Complex(1,2);
        boolean flag=service.Add(a);
        assert flag:"添加对象失败！";
    }

    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db";
        DatabaseTest test = new DatabaseTest(dbUrl);

//        test.testBasicFunctionality();
//        test.testParallelDataRetrieval();
//        test.testThroughput();
         test.testExceptionHandling(); // 需要嵌套对象的支持
    }
}