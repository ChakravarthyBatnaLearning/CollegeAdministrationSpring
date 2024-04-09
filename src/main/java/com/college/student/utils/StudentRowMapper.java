package com.college.student.utils;

import com.college.student.pojo.Address;
import com.college.student.pojo.Student;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        Address address = new Address();

        //mapping student;
        student.setRollNo(rs.getInt("rollNo"));
        student.setName(rs.getString("name"));
        student.setAge(rs.getByte("age"));
        student.setPhoneNo(rs.getLong("phoneNumber"));
        student.setGender(rs.getString("GENDER"));
        //mapping address;
        address.setRollNo(rs.getInt("rollNo"));
        address.setCountry(rs.getString("country"));
        address.setState(rs.getString("state"));
        address.setCity(rs.getString("city"));

        if (address.getCountry() != null || address.getCity() != null || address.getState() != null) {
            student.setAddress(address);
        }

        return student;
    }
}
