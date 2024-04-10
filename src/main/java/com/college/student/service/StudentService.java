package com.college.student.service;

import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;

import java.util.List;

public interface StudentService {
    public void addStudent(Student student);

    public List<Student> listStudents();

    public Student deleteStudentByRollNo(int rollNo);

    public Student updateStudentDetailsByRollNo(Student updateStudent);

    public Student getStudentByRollNo(int studentRollNo);

    public boolean isStudentExist(int rollNo);

    // address repository methods
    boolean addStudentAddress(Address studentAddress, int studentRollNo);

    Address updateStudentPermanetAddress(Address studentAddress, int studentRollNo);

    Address updateStudentTemporaryAddress(Address studentAddress, int studentRollNo);

    boolean deleteAllStudentAddresses(int studentRoll);

    boolean isStudentHaveAddress(int studentRollNo);

    List<Address> getStudentAddresses(int studentRollNo);

    Address getStudentPermanentAddress(int studentRollNo);

    Address getStudentTemporaryAddress(int studentRollNo);

    //admission repository method's
    boolean addStudentAdmission(Admission admission, int studentRollNo);

    Admission getStudentAdmission(int rollNo);

    boolean deleteStudentAdmission(int rollNo);

    boolean updateStudentAdmission(Admission admission, int studentRollNo);
}
