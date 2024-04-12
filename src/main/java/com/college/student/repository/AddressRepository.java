package com.college.student.repository;

import com.college.student.constant.AddressType;
import com.college.student.pojo.Address;

import java.util.List;

public interface AddressRepository {
    boolean addStudentAddress(Address studentAddress, int studentRollNo);

    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType);

    boolean deleteAllStudentAddresses(int studentRoll);

    boolean isStudentHaveAddress(int studentRollNo);

    List<Address> getStudentAddresses(int studentRollNo);

    Address getStudentAddressByRollNo(int rollNo, AddressType addressType);
}
