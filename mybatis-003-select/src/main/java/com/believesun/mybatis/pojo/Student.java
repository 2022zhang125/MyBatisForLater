package com.believesun.mybatis.pojo;

public class Student {
    private Long id;
    private String name;
    private String xh;
    private Character sex;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", xh='" + xh + '\'' +
                ", sex=" + sex +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Student(Long id, String name, String xh, Character sex) {
        this.id = id;
        this.name = name;
        this.xh = xh;
        this.sex = sex;
    }

    public Student() {
    }
}
