package shujuku;

public class TableRecord implements IPersistentStore {
    String[][] fields;
    String clzName;
    Object obj;
    public TableRecord(Object obj){
        this.obj=obj;
        ObjReflect objReflect = new ObjReflect();
        fields=objReflect.getFields(obj);
    }
    //检查列属性是否一致类--郭拓
    public boolean checkTabFields() {
        //没有流程图按自己想法写
        ObjReflect objReflect = new ObjReflect();
        String[][] str = objReflect.getFields(obj);
        //先判断是否属性数目想同，以免一会儿遍历出现数组越界
        if (str[0].length != fields[0].length) {
            return false;
        }
        for (int j = 0; j < str[0].length; j++) {
            //1.检查属性类型是否一致
            if (str[0][j] != fields[0][j]) {
                return false;
            }
        }
        for (int j = 0; j < str[1].length; j++) {
            //2.检查属性名是否一致
            if (str[1][j] != fields[1][j]) {
                return false;
            }
        }
        //不需要检查属性的值是否一样！
        return true;
    }

    // 假设主键列名称是一个字符串
    private String primaryKeyColumn;
    // 假设主键值是一个整数
    private int primaryKeyValue;

    // 构造函数，初始化主键列和值
    public TableRecord(String primaryKeyColumn, int primaryKeyValue) {
        this.primaryKeyColumn = primaryKeyColumn;
        this.primaryKeyValue = primaryKeyValue;
    }

    // 实现接口中定义的 getPriKey() 方法
    @Override
    public String getPriKey() {
        return this.primaryKeyColumn;
    }

    // 实现接口中定义的 getPriKeyValue() 方法
    @Override
    public Object getPriKeyValue() {
        return this.primaryKeyValue;
    }


}

