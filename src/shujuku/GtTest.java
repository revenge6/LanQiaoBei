package shujuku;

public class GtTest implements IPersistentStore{
    int age;
    int id;
    int grade;

    public GtTest(int age, int id, int grade) {
        this.age = age;
        this.id = id;
        this.grade = grade;
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
