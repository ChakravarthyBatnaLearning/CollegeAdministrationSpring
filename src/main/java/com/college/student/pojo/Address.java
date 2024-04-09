package com.college.student.pojo;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Cloneable, Serializable, Comparable<Address> {
    private static final long serialVersionUID = 25235232423L;
    private String country;
    private String state;
    private String city;
    private int rollNo;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, state, city, rollNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Address address = (Address) obj;
        return rollNo == address.rollNo;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Address{" + "country='" + country + '\'' + ", state='" + state + '\'' + ", city='" + city + '\'' + ", rollNo=" + rollNo + '}';
    }

    @Override
    public int compareTo(@NotNull Address o) {
        return Integer.compare(rollNo, ((Address) o).rollNo);
    }
}
