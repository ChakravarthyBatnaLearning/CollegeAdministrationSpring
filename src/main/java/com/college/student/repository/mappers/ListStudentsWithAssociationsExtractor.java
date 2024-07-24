package com.college.student.repository.mappers;

import com.college.student.repository.constants.AddressConstants;
import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.constants.AdmissionConstants;
import com.college.student.repository.constants.StudentConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListStudentsWithAssociationsExtractor implements ResultSetExtractor<List<Student>> {
    @Override
    public List<Student> extractData(ResultSet studentResultSet) throws SQLException, DataAccessException {
        Map<Integer,Student> studentMap = new HashMap<>();
        Student currentStudent = null;

        while (studentResultSet.next()) {
            int rollNo = studentResultSet.getInt(StudentConstants.ROLL_NO.toString());

            // If currentStudent is null or the rollNo changes, create a new Student object
            if (currentStudent == null || !(studentMap.containsKey(rollNo))) {
                currentStudent = new Student();
                currentStudent.setRollNo(rollNo);
                currentStudent.setName(studentResultSet.getString(StudentConstants.NAME.toString()));
                currentStudent.setAge(studentResultSet.getByte(StudentConstants.AGE.toString()));
                currentStudent.setPhoneNo(studentResultSet.getLong(StudentConstants.PHONE_NUMBER.toString()));
                currentStudent.setGender(studentResultSet.getString(StudentConstants.GENDER.toString()));
                currentStudent.setAddressList(new ArrayList<>()); // Initialize address list
                currentStudent.setAdmission(new Admission()); // Initialize admission
                studentMap.put(currentStudent.getRollNo(),currentStudent);
                // Set admission details for the current student
                currentStudent.getAdmission().setCourse(studentResultSet.getString(AdmissionConstants.COURSE.toString()));
                currentStudent.getAdmission().setSection(studentResultSet.getInt(AdmissionConstants.SECTION.toString()));
                currentStudent.getAdmission().setAdmissionYear(studentResultSet.getInt(AdmissionConstants.ADMISSION_YEAR.toString()));
            }

            // Add address to the current student's address list
            Address address = new Address();
            address.setCountry(studentResultSet.getString(AddressConstants.COUNTRY.toString()));
            address.setState(studentResultSet.getString(AddressConstants.STATE.toString()));
            address.setCity(studentResultSet.getString(AddressConstants.CITY.toString()));
            currentStudent.getAddressList().add(address);

        }

        return new ArrayList<>(studentMap.values());
    }
}
