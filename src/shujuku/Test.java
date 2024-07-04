package shujuku;

public class Test {
    //测试
    public static void main(String[] args) throws IllegalAccessException {
        ObjReflect objReflect=new ObjReflect();
        String[][] str= objReflect.getFields(objReflect);
        for (int i=0;i< str.length;i++){
            for (int j=0;j<str[i].length;j++){
                System.out.print(str[i][j]+" ");
            }
            System.out.println();
        }
    }
}
