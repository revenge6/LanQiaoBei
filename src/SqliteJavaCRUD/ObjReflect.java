package SqliteJavaCRUD;

import java.io.*;
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
    //将数据库中的序列化数据反序列化为对象
    public static Object DeserializeFromString(String byteStream) {
        if (byteStream == null || byteStream=="") {
            return null;
        }
        try {
            //根据字符串构造字节序列
            String[] byteStrings = byteStream.trim().split(" ");
            byte[] bytes = new byte[byteStrings.length];
            for (int i = 0; i < byteStrings.length; i++) {
                bytes[i] = Byte.parseByte(byteStrings[i]);
            }
            //反序列化字节序列
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object temp=ois.readObject();//对象
            bais.close();
            ois.close();
            return temp;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
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
    //反射获取给定属性的属性值和属性名字符串，用于Select查询
    public static String[][] GetProperties(Object obj, String[] fieldNames) {
        Class clazz = obj.getClass();
        String[] fieldValues = new String[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            try {
                // 获取字段
                Field field = clazz.getDeclaredField(fieldNames[i]);
                // 设置字段为可访问
                field.setAccessible(true);
                // 获取字段的值
                Object value = field.get(obj);
                // 将值转换为字符串
                fieldValues[i] = (value == null) ? "null" : value.toString();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                fieldValues[i] = "Error";
            }
        }
        String[][] str=new String[fieldNames.length][3];
        for (int i=0;i<fieldNames.length;i++){
            str[i][1]=fieldNames[i];
            str[i][2]=fieldValues[i];
        }
        return str;
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
