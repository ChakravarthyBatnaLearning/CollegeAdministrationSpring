package com.college.student.repository.mappers;

import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.constants.AddressConstants;
import com.college.student.repository.constants.AdmissionConstants;
import com.college.student.repository.constants.StudentConstants;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentRowMapper implements RowMapper<Student> {

    private final Map<Integer, Student> studentMap = new HashMap<>();

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        //mapping student;
        student.setRollNo(rs.getInt(StudentConstants.ROLL_NO.toString()));
        student.setName(rs.getString(StudentConstants.NAME.toString()));
        student.setAge(rs.getByte(StudentConstants.AGE.toString()));
        student.setPhoneNo(rs.getLong(StudentConstants.PHONE_NUMBER.toString()));
        student.setGender(rs.getString(StudentConstants.GENDER.toString()));
        return student;
    }
}
