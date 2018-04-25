package com.example.gconf.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by issac.hu on 2018/3/20.
 */
public class TestAtomicReference {

    public static class Person{
       private int id;
       private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }


        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        Person p1 = new Person(1, "p1");
        AtomicReference<Person> ap=new AtomicReference<>(p1);
        ap.compareAndSet(p1,new Person(3,"new person"));
        System.out.println(ap);

    }
}
