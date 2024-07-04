package shujuku;

public class TableRecord implements IPersistentStore {
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

    // 其他可能的方法和逻辑
}

// 使用 PersistentStoreImpl 类
public class Main {
    public static void main(String[] args) {
        // 创建 TableRecord 的实例
        TableRecord store = new TableRecord("id", 1);

        // 使用接口中定义的方法
        System.out.println("Primary Key Column: " + store.getPriKey());
        System.out.println("Primary Key Value: " + store.getPriKeyValue());
    }
}