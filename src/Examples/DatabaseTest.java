
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
        int testCount = 1000; // 测试次数
        GtTest testObject = new GtTest(1, 1, "20", "test");
        
        // 预先创建测试对象数组，避免创建对象的时间影响测试结果
        GtTest[] addObjects = new GtTest[testCount];
        GtTest[] updateObjects = new GtTest[testCount];
        for (int i = 0; i < testCount; i++) {
            addObjects[i] = new GtTest(1, i, "20", "test");
            updateObjects[i] = new GtTest(2, i, "21", "updated");
        }
        
        // 测试添加操作
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            service.Add(addObjects[i]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("添加" + testCount + "次对象耗时：" + (endTime - startTime) + "ms");
        
        // 测试查询操作
        startTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            Object result = service.SelectByID(ObjReflect.GetClzName(testObject), "id", String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("查询" + testCount + "次对象耗时：" + (endTime - startTime) + "ms");
        
        // 测试更新操作
        startTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            service.Update(updateObjects[i], "id");
        }
        endTime = System.currentTimeMillis();
        System.out.println("更新" + testCount + "次对象耗时：" + (endTime - startTime) + "ms");
        
        // 测试删除操作
        startTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            service.Delete(ObjReflect.GetClzName(testObject), "id", String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("删除" + testCount + "次对象耗时：" + (endTime - startTime) + "ms");
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
        test.testThroughput();
//         test.testExceptionHandling(); // 需要嵌套对象的支持
    }
}