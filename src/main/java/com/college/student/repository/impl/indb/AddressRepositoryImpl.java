package com.college.student.repository.impl.indb;

import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.utils.AddressRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressRepositoryImpl implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);
    private static final String insertQuery = "INSERT INTO address VALUES (?,?,?,?,?)";
    private static final String listQuery = "SELECT * FROM address WHERE ROLL_NO = ?";
    private static final String updateAddressQuery = "UPDATE address SET COUNTRY = ?, STATE = ?, CITY = ?, ADDRESS_TYPE = ? WHERE ROLL_NO = ?";
    private static final String deleteQuery = "DELETE FROM address WHERE ROLL_NO = ?";
    private static final String getPermanentAddressQuery = "SELECT * FROM address WHERE ROLL_NO = ? AND ADDRESS_TYPE = 'PERMANENT'";
    private static final String getTemporaryAddressQuery = "SELECT * FROM address WHERE ROLL_NO = ? AND ADDRESS_TYPE = 'TEMPORARY'";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) {
        int addressRowsEffected = 0;
        try {
            addressRowsEffected = jdbcTemplate.update(insertQuery, studentAddress.getCountry(), studentAddress.getState(), studentAddress.getCity()
                    , studentAddress.getAddressType(), studentRollNo);

            if (addressRowsEffected == 0) {
                logger.error("Exception While Adding the Student Address {}", studentAddress);
            }
        } catch (Exception e) {

        }
        return true;
    }

    @Override
    public Address updateStudentPermanetAddress(Address studentAddress, int studentRollNo) {
        int rowsEffected = 0;
        try {
            rowsEffected = jdbcTemplate.update(updateAddressQuery, studentAddress.getCountry(),
                    studentAddress.getState(), studentAddress.getCity(), studentAddress.getAddressType(), studentRollNo);
            if (rowsEffected == 0) {
                logger.error("Student Doesn't have Address to Update : {}", studentAddress + " To Execute Update Query");
            }
        } catch (Exception e) {

        }
        return studentAddress;
    }

    @Override
    public Address updateStudentTemporaryAddress(Address studentAddress, int studentRollNo) {
        int rowsEffected = 0;
        try {
            rowsEffected = jdbcTemplate.update(updateAddressQuery, studentAddress.getCountry(),
                    studentAddress.getState(), studentAddress.getCity(), studentAddress.getAddressType(), studentRollNo);
            if (rowsEffected == 0) {
                logger.error("Student Doesn't have Address to Update : {}", studentAddress + " To Execute Update Query");
            }
        } catch (Exception e) {

        }
        return studentAddress;
    }

    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) {
        try {
            jdbcTemplate.update(deleteQuery, studentRoll);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean isStudentHaveAddress(int studentRollNo) {
        return getStudentAddresses(studentRollNo) != null;
    }

    @Override
    public List<Address> getStudentAddresses(int studentRollNo) {
        try {
            return jdbcTemplate.query(listQuery, new Object[]{studentRollNo}, new AddressRowMapper());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Address getStudentPermanentAddress(int studentRollNo) {
        try {
            return jdbcTemplate.queryForObject(getPermanentAddressQuery, new Object[]{studentRollNo}, new AddressRowMapper());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Address getStudentTemporaryAddress(int studentRollNo) {
        try {
            return jdbcTemplate.queryForObject(getTemporaryAddressQuery, new Object[]{studentRollNo}, new AddressRowMapper());
        } catch (Exception e) {
            throw e;
        }
    }
}
