package shujuku;

import java.io.Serializable;

//以这个类为测试类，将类的实例对象添加到数据库中
public class GGG implements Serializable {
    int age;
    int id;
    int grade;
    String name;
    public GGG(int age, int id,int grade,String name) {
        this.age = age;
        this.id = id;
        this.grade=grade;
        this.name=name;
    }
}
