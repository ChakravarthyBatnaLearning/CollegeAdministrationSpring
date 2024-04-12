//StudentService class will call various method of StudentRepository to perform operations;
package com.college.student.service.impl;

import com.college.student.cache.lru_dll.LRUCache;
import com.college.student.constant.AddressType;
import com.college.student.constant.StorageType;
import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.AdmissionRepository;
import com.college.student.repository.StudentRepository;
import com.college.student.repository.factory.StudentRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StudentServiceImpl implements com.college.student.service.StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private static final LRUCache<Integer, Student> lruCache = new LRUCache<>(5);
    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final AdmissionRepository admissionRepository;

    public StudentServiceImpl(StorageType storageType, StudentRepositoryFactory studentRepositoryFactory, AddressRepository addressRepository, AdmissionRepository admissionRepository) {
        this.addressRepository = addressRepository;
        this.admissionRepository = admissionRepository;
        this.studentRepository = studentRepositoryFactory.getStudentRepositoryInstance(storageType);
    }

    public void addStudent(Student student) {
        this.studentRepository.addStudent(student);

        //adding address if it is not null;
        if (student.getAddressList() != null) {
            List<Address> addresses = student.getAddressList();
            for (Address address : student.getAddressList()) {
                addressRepository.addStudentAddress(address, student.getRollNo());
            }
        }
        //adding admission if it is not null;
        if (student.getAdmission() != null) {
            admissionRepository.addStudentAdmission(student.getAdmission(), student.getRollNo());
        }
    }

    public List<Student> listStudents(String flag) {
        return this.studentRepository.listStudents(flag);
    }

    public Student deleteStudentByRollNo(int rollNo) {
        Student student = getStudentByRollNo(rollNo);
        if (student.getAdmission() != null) admissionRepository.deleteStudentAdmission(rollNo);
        if (student.getAddressList() != null) {
            addressRepository.deleteAllStudentAddresses(rollNo);
        }
        return this.studentRepository.deleteStudent(rollNo);
    }

    public Student updateStudentDetailsByRollNo(Student updateStudent) {
        if (updateStudent.getAddressList() != null) {
            for (Address address : updateStudent.getAddressList()) {
                updateStudentAddressByRollNo(updateStudent.getRollNo(), address, address.getAddressType());
            }
        }
        if (updateStudent.getAdmission() != null) {
            updateStudentAdmission(updateStudent.getAdmission(), updateStudent.getRollNo());
        }
        return this.studentRepository.updateStudentByRollNo(updateStudent);
    }

    public Student getStudentByRollNo(int studentRollNo) {
        Student student = lruCache.get(studentRollNo);
        if (student == null) {
            student = this.studentRepository.getStudentData(studentRollNo);
            lruCache.put(studentRollNo, student);
            logger.info("Cache is Empty, Student Added to Cache, Now Cache size is : {}", lruCache.size());
        }
        return student;
    }

    public boolean isStudentExist(int rollNo) {
        return this.studentRepository.isExist(rollNo);
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
        return null;
    }

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo) {
        return admissionRepository.addStudentAdmission(admission, studentRollNo);
    }

    @Override
    public Admission getStudentAdmission(int rollNo) {
        return admissionRepository.getStudentAdmission(rollNo);
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) {
        return admissionRepository.deleteStudentAdmission(rollNo);
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) {
        return admissionRepository.updateStudentAdmission(admission, studentRollNo);
    }

    @Override
    public Student getCompleteStudentData(int studentRollNo) {
        return studentRepository.getStudentDataWithAssociations(studentRollNo);
    }
}
