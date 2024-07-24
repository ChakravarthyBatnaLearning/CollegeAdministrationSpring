package com.college.student.repository.mappers;

import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.constants.AddressConstants;
import com.college.student.repository.constants.AdmissionConstants;
import com.college.student.repository.constants.StudentConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentWithAssociationsRowExtractor implements ResultSetExtractor<Student> {
    @Override
    public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
        Student student = null;
        List<Address> addresses = new ArrayList<>();
        Admission admission = null;
        boolean hasAddress = false;
        boolean hasAdmission = false;

        while (rs.next()) {
            if (student == null) {
                student = new Student();
                student.setRollNo(rs.getInt(StudentConstants.ROLL_NO.toString()));
                student.setName(rs.getString(StudentConstants.NAME.toString()));
                student.setAge(rs.getByte(StudentConstants.AGE.toString()));
                student.setPhoneNo(rs.getLong(StudentConstants.PHONE_NUMBER.toString()));
                student.setGender(rs.getString(StudentConstants.GENDER.toString()));
            }

            String country = rs.getString(AddressConstants.COUNTRY.toString());
            String state = rs.getString(AddressConstants.STATE.toString());
            String city = rs.getString(AddressConstants.CITY.toString());

            if (country != null || state != null || city != null) {
                Address address = new Address();
                address.setCountry(country);
                address.setState(state);
                address.setCity(city);
                addresses.add(address);
                hasAddress = true;
            }

            String course = rs.getString(AdmissionConstants.COURSE.toString());
            Integer section = rs.getInt(AdmissionConstants.SECTION.toString());
            Integer admissionYear = rs.getInt(AdmissionConstants.ADMISSION_YEAR.toString());

            if (course != null && section != null && admissionYear != null) {
                if (admission == null) {
                    admission = new Admission();
                    admission.setCourse(course);
                    admission.setSection(section);
                    admission.setAdmissionYear(admissionYear);
                    hasAdmission = true;
                }
            }
        }

        if (student != null) {
            if (hasAddress) {
                student.setAddressList(addresses);
            } else {
                student.setAddressList(null);
            }
            if (hasAdmission) {
                student.setAdmission(admission);
            } else {
                student.setAdmission(null);
            }
        }

        return student;
    }
}
