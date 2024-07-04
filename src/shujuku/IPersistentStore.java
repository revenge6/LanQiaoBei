package shujuku;

public interface IPersistentStore {
    // 定义一个方法，返回主键列的名称
    String getPriKey();

    // 定义一个方法，返回主键的值
    Object getPriKeyValue();

}
