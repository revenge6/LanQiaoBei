package Examples;

import SqliteJavaCRUD.IPersistentStore;

import java.io.Serializable;

public class Complex implements IPersistentStore, Serializable {
    private int id;//主键
    private GtTest testObject = new GtTest(1, 1, "20", "test");//GtTest类对象
    private int sno;//非主键属性

    private int[][] aArray=new int[][]{{1,2},{3,4}};//二维Integer数组对象
    public Complex(int id,int sno){
        this.id=id;
        this.sno=sno;
    }

    @Override
    public String getPriKey() {
        return "id";
    }

    @Override
    public String getPriKeyValue() {
        return id+"";
    }
}
