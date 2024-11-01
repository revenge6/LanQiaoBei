package SqliteJavaCRUD;
//lab
public interface IPersistentStore {
    String persistentStorePriKey = "";
    String priKeyValue="";
    // 定义一个方法，返回主键列的名称
    String getPriKey();

    // 定义一个方法，返回主键的值
    String getPriKeyValue();
}
