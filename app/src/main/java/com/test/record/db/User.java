package com.test.record.db;


import java.util.Objects;

public class User {
    private String name;            //用户名
    private String password;        //密码

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        //自反性
        if (this == o) return true;
        //任何对象不等于null，比较是否为同一类型
        if (!(o instanceof User)) return false;
        //强制类型转换
        User person = (User) o;
        //比较属性值
        return getName() == person.getName() &&
                Objects.equals(getName(), person.getName()) &&
                Objects.equals(getPassword(), person.getPassword());
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
}
