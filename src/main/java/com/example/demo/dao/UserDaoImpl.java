package com.example.demo.dao;

public class UserDaoImpl implements UserDao {
    private Integer uid;
    private String name;
    private Integer age;

    public UserDaoImpl(Integer uid, String name) {
        super();
        this.uid = uid;
        this.name = name;
    }

    public UserDaoImpl(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    @Override
    public void save() {
        System.out.println("User dal save" + uid + name + age);
    }
}
