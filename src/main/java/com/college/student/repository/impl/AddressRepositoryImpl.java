package com.college.student.repository.impl;

import com.college.student.repository.constants.AddressType;
import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.mappers.AddressRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressRepositoryImpl implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);
    private static final String INSERT_QUERY = "INSERT INTO ADDRESS (COUNTRY, STATE, CITY, ROLL_NO, ADDRESS_TYPE) VALUES (?,?,?,?,?)";
    private static final String LIST_QUERY = "SELECT COUNTRY, STATE, CITY, ROLL_NO, ADDRESS_TYPE FROM ADDRESS WHERE ROLL_NO = ?";
    private static final String UPDATE_ADDRESS_QUERY = "UPDATE ADDRESS SET COUNTRY = ?, STATE = ?, CITY = ?, ADDRESS_TYPE = ? WHERE ROLL_NO = ? AND ADDRESS_TYPE = ?";
    private static final String DELETE_QUERY = "DELETE FROM ADDRESS WHERE ROLL_NO = ?";
    private static final String SELECT_QUERY = "SELECT COUNTRY, STATE, CITY, ROLL_NO, ADDRESS_TYPE FROM ADDRESS WHERE ROLL_NO = ? AND ADDRESS_TYPE = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) {
        int addressRowsEffected = 0;
        try {
            addressRowsEffected = jdbcTemplate.update(INSERT_QUERY, studentAddress.getCountry(), studentAddress.getState(), studentAddress.getCity()
                    , studentAddress.getAddressType(), studentRollNo);

            if (addressRowsEffected == 0) {
                logger.error("Exception While Adding the Student Address {}", studentAddress);
            }
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    @Override
    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType) {
        try {
            int rowsEffected = jdbcTemplate.update(UPDATE_ADDRESS_QUERY, address.getCountry(), address.getState(),
                    address.getCity(), address.getAddressType(), rollNo, addressType.name());
            if (rowsEffected == 0) {
                logger.error("Student Doesn't have Address to Update : {}", address + " To Execute Update Query");
            }
        } catch (Exception e) {
            throw e;
        }
        return address;
    }


    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) {
        try {
            jdbcTemplate.update(DELETE_QUERY, studentRoll);
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
            return jdbcTemplate.query(LIST_QUERY, new Object[]{studentRollNo}, new AddressRowMapper());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Address getStudentAddressByRollNo(int rollNo, AddressType addressType) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY, new Object[]{rollNo, addressType.name()}, new AddressRowMapper());
        } catch (Exception e) {
            throw e;
        }
    }
}
