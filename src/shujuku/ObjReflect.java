package shujuku;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

public class ObjReflect {
    //属性类型
    static String[] fieldType;
    //属性名
    static String[] fieldName;
    //属性值
    static String[] fieldValue;
    //序列化列的列名
    static String xuliehua="Byte_Stream";
    //检查该对象的类是否实现Serializable接口
    public static boolean isSerializable(Class<?> clazz) {
        // 检查类是否实现了 Serializable 接口
        return Serializable.class.isAssignableFrom(clazz);
    }
    //获取序列化字节的字符串形式，每个字节以空格分割
    public static String SerializeToString(Object obj){
        if(obj==null){
            return "";
        }
        //检测未实现序列化的对象后异常退出操作
        if(!isSerializable((obj.getClass() ))){
            System.out.println(ObjReflect.GetClzName(obj)+"未实现序列化接口！");
            System.exit(-1);
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
    //反射获取属性数组——反射
    private static void Reflect(Object obj) {
        Class clazz = obj.getClass();
        // 获取类的属性信息
        Field[] fields = clazz.getDeclaredFields();
        fieldType=new String[fields.length];
        fieldName=new String[fields.length];
        fieldValue=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            //属性类型
            fieldType[i]=fields[i].getType().getName();
            //属性名
            fieldName[i]=fields[i].getName();
            //属性值
            try {
                if(!fields[i].isAccessible()) {
                    fields[i].setAccessible(true);
                }
                fieldValue[i]=fields[i].get(obj).toString();
            } catch (Exception e) {
                fieldValue[i]=null;
            }
        }
    }
    //反射获取属性数组
    public static String[][] GetFields(Object obj){
        Reflect(obj);
        String[][] str=new String[fieldType.length+1][3];//拼接数组
        for (int i=0;i<fieldType.length;i++){
            str[i][0]=fieldType[i];
            str[i][1]=fieldName[i];
            str[i][2]=fieldValue[i];
        }
        str[fieldType.length][0]=GetClzName("");
        str[fieldType.length][1]=xuliehua;
        str[fieldType.length][2]=SerializeToString(obj);
        return str;
    }
    //反射获取类名
    public static String GetClzName(Object obj){
        Class clazz = obj.getClass();
        String clzname=clazz.getName();
        return clzname;
    }
}
