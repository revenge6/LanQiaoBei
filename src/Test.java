import shujuku.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

//实践类，测试代码运行结果
public class Test {
    public static void main(String[] args) {
        Service service=new Service("jdbc:sqlite:D:\\java\\idea-workspace\\LanQiaoBei\\test.db");
        Xuliehua xuliehua=new Xuliehua();
//        System.out.println(isSerializable(new Test().getClass()));
//        System.out.println(isSerializable(new GGG(1,1,1,"gt").getClass()));
//        System.out.println(isSerializable(new Xuliehua().getClass()));
//        m();
        System.out.println(serializeToString(xuliehua));
    }
    public static void m(){
        DataBase.DeleteTable("shujuku$GGG");
        DataBase.DeleteTable("Xuliehua");
        DataBase.DeleteTable("Map");
        DataBase.DeleteTable("Attribute");
        System.out.println("删除表成功！");
    }
    public static boolean isSerializable(Class<?> clazz) {
        // 检查类是否实现了 Serializable 接口
        return Serializable.class.isAssignableFrom(clazz);
    }
    public static String serializeToString(Object obj){
        if(obj==null || !isSerializable((obj.getClass()))){
            return "";
        }
        try{
            //将对象存储到字节序列
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            //写入对象
            oos.writeObject(obj);
            oos.flush();
            byte[] bytes=baos.toByteArray();
            String str="";
            for (byte b : bytes) {
                str+=b + " ";
            }
            oos.close();//关闭资源
            return str;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
