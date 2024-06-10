package Java14b;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class T8 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        PriorityQueue<User> q=new PriorityQueue<User>(new Comp());
        User a=new User(3,10);
        User b=new User(2,20);
        User c=new User(1,30);
        q.add(a);
        q.add(b);
        q.add(c);

        System.out.println(q.poll().toString());
        System.out.println(q.poll().toString());
        System.out.println(q.poll().toString());
        scan.close();
    }
    static class Comp implements Comparator<User> {

        @Override
        public int compare(User a, User b) {
            return a.id-b.id;
        }
    }
    static class User{

        int id;
        int year;
        User(int id,int year){
            this.id=id;
            this.year=year;
        }

        @Override
        public String toString() {
            return "id="+id+" year="+year;
        }
    }
}
