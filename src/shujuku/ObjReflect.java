package shujuku;

import java.lang.reflect.Field;

public class ObjReflect {
    String[] fieldType;
    String[] fieldName;
    String[] fieldValue;

    String name="gt";
    String age="19";
    //反射函数--郭拓
    private void reflect(Object obj) {
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
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String[][] getFields(Object obj){
        reflect(obj);
        String[][] str=new String[3][];
        str[0]=fieldType;
        str[1]=fieldName;
        str[2]=fieldValue;
        return str;
    }

}
