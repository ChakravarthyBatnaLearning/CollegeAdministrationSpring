package com.college.student.repository;

import com.college.student.pojo.Address;

import java.util.List;

public interface AddressRepository {
    boolean addStudentAddress(Address studentAddress, int studentRollNo);

    Address updateStudentPermanetAddress(Address studentAddress, int studentRollNo);

    Address updateStudentTemporaryAddress(Address studentAddress, int studentRollNo);

    boolean deleteAllStudentAddresses(int studentRoll);

    boolean isStudentHaveAddress(int studentRollNo);

    List<Address> getStudentAddresses(int studentRollNo);

    Address getStudentPermanentAddress(int studentRollNo);

    Address getStudentTemporaryAddress(int studentRollNo);
}
