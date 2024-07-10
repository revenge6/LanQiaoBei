package shujuku;

import java.lang.reflect.Field;

public class ObjReflect {
    //属性类型
    static String[] fieldType;
    //属性名
    static String[] fieldName;
    //属性值
    static String[] fieldValue;
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
                fieldValue[i]=fields[i].get(obj).toString();
            } catch (Exception e) {
                fieldValue[i]="";
            }
        }
    }
    //反射获取属性数组
    public static String[][] GetFields(Object obj){
        Reflect(obj);
        String[][] str=new String[fieldType.length][3];//拼接数组
        for (int i=0;i<fieldType.length;i++){
            str[i][0]=fieldType[i];
            str[i][1]=fieldName[i];
            str[i][2]=fieldValue[i];
        }
        return str;
    }
    //反射获取类名
    public static String GetClzName(Object obj){
        Class clazz = obj.getClass();
        String clzname=clazz.getName();
        return clzname;
    }
}
