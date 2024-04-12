package com.college.student.repository.mappers;

import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListStudentsWithAssociationsExtractor implements ResultSetExtractor<List<Student>> {
    @Override
    public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Student> students = new ArrayList<>();
        Student currentStudent = null;

        while (rs.next()) {
            int rollNo = rs.getInt("ROLL_NO");

            // If currentStudent is null or the rollNo changes, create a new Student object
            if (currentStudent == null || currentStudent.getRollNo() != rollNo) {
                currentStudent = new Student();
                currentStudent.setRollNo(rollNo);
                currentStudent.setName(rs.getString("NAME"));
                currentStudent.setAge(rs.getByte("AGE"));
                currentStudent.setPhoneNo(rs.getLong("PHONE_NUMBER"));
                currentStudent.setGender(rs.getString("GENDER"));
                currentStudent.setAddressList(new ArrayList<>()); // Initialize address list
                currentStudent.setAdmission(new Admission()); // Initialize admission
                students.add(currentStudent);
            }

            // Add address to the current student's address list
            Address address = new Address();
            address.setCountry(rs.getString("COUNTRY"));
            address.setState(rs.getString("STATE"));
            address.setCity(rs.getString("CITY"));
            currentStudent.getAddressList().add(address);

            // Set admission details for the current student
            currentStudent.getAdmission().setCourse(rs.getString("COURSE"));
            currentStudent.getAdmission().setSection(rs.getInt("SECTION"));
            currentStudent.getAdmission().setAdmissionYear(rs.getInt("ADMISSION_YEAR"));
        }

        return students;
    }
}
