package Examples;

import SqliteJavaCRUD.IPersistentStore;

import java.io.Serializable;

//以这个类为测试类，将类的实例对象添加到数据库中
public class GtTest implements IPersistentStore, Serializable {

    int age;
    int id;
    String grade;
    String name;
    public GtTest(int age, int id,String grade,String name) {
        this.age = age;
        this.id = id;
        this.grade = grade;
        this.name=name;
    }
    @Override
    public String getPriKey() {
        return "id";
    }

    @Override
    public String toString() {
        return "GtTest{" +
                "age=" + age +
                ", id=" + id +
                ", grade='" + grade + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getPriKeyValue() {
        return id+"";
    }
}
