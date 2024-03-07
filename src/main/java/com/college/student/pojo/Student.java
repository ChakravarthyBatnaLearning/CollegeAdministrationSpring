package com.college.student.pojo;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

//POJO-plain old java object's
//it's to store the student data;
public class Student implements Serializable, Comparable<Student>, Cloneable {

    private static final long serialVersionUID = 5868686868678586L;
    private int rollNo;
    private String name;
    private byte age;
    private long phoneNo;
    private String gender;

    public int getRollNo() {
        return this.rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return this.age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public long getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phoneNo=" + phoneNo +
                '}';
    }

    @Override
    public int compareTo(@NotNull Student student) {
        if (this.age > student.getAge() && this.gender.equals("FEMALE")) return -1;
        if (this.age > student.getAge() && this.gender.equals("MALE")) return -1;
        if (this.age > student.getAge() && this.gender.equals("OTHER")) return -1;
        return Byte.compare(student.getAge(), this.age);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        return getClass() == object.getClass();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
