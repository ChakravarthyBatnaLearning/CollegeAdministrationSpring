package com.college.student.utils;

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

        //mapping student;
        student.setRollNo(rs.getInt("ROLL_NO"));
        student.setName(rs.getString("NAME"));
        student.setAge(rs.getByte("AGE"));
        student.setPhoneNo(rs.getLong("PHONE_NUMBER"));
        student.setGender(rs.getString("GENDER"));
        return student;
    }
}
