package com.college.student.utils;

import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompleteStudentDataMapper implements ResultSetExtractor<Student> {
    @Override
    public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
        Student student = null;
        List<Address> addresses = new ArrayList<>();
        Admission admission = null;

        while (rs.next()) {
            if (student == null) {
                student = new Student();
                student.setRollNo(rs.getInt("ROLL_NO"));
                student.setName(rs.getString("NAME"));
                student.setAge(rs.getByte("AGE"));
                student.setPhoneNo(rs.getLong("PHONE_NUMBER"));
                student.setGender(rs.getString("GENDER"));
            }

            Address address = new Address();
            address.setCountry(rs.getString("COUNTRY"));
            address.setState(rs.getString("STATE"));
            address.setCity(rs.getString("CITY"));
            addresses.add(address);

            if (admission == null) {
                admission = new Admission();
                admission.setCourse(rs.getString("COURSE"));
                admission.setSection(rs.getInt("SECTION"));
                admission.setAdmissionYear(rs.getInt("ADMISSION_YEAR"));
            }
        }

        if (student != null) {
            student.setAddressList(addresses);
            student.setAdmission(admission);
        }

        return student;
    }
}
