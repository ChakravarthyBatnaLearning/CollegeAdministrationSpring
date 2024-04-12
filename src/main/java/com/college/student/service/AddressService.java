package com.college.student.service;

import com.college.student.repository.constants.AddressType;
import com.college.student.pojo.Address;

import java.util.List;

public interface AddressService {
    boolean addStudentAddress(Address studentAddress, int studentRollNo);

    Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType);

    boolean deleteAllStudentAddresses(int studentRoll);

    boolean isStudentHaveAddress(int studentRollNo);

    List<Address> getStudentAddresses(int studentRollNo);

    Address getStudentAddressByRollNo(int rollNo, AddressType addressType);
}
