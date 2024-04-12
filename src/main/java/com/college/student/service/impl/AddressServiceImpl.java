package com.college.student.service.impl;

import com.college.student.repository.constants.AddressType;
import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) {
        return addressRepository.addStudentAddress(studentAddress, studentRollNo);
    }

    @Override
    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType) {
        return addressRepository.updateStudentAddressByRollNo(rollNo, address, addressType);
    }

    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) {
        return addressRepository.deleteAllStudentAddresses(studentRoll);
    }

    @Override
    public boolean isStudentHaveAddress(int studentRollNo) {
        return addressRepository.isStudentHaveAddress(studentRollNo);
    }

    @Override
    public List<Address> getStudentAddresses(int studentRollNo) {
        return addressRepository.getStudentAddresses(studentRollNo);
    }

    @Override
    public Address getStudentAddressByRollNo(int rollNo, AddressType addressType) {
        return addressRepository.getStudentAddressByRollNo(rollNo, addressType);
    }


}
