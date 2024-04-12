package com.college.student.repository.mappers;

import com.college.student.constant.AddressType;
import com.college.student.pojo.Address;
import com.college.student.pojo.Student;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
         Address address = new Address();
        address.setCountry(rs.getString("COUNTRY"));
        address.setState(rs.getString("STATE"));
        address.setCity(rs.getString("CITY"));
        address.setRollNo(rs.getInt("ROLL_NO"));
        address.setAddressType(AddressType.valueOf(rs.getString("ADDRESS_TYPE")));
        return address;
    }
}
