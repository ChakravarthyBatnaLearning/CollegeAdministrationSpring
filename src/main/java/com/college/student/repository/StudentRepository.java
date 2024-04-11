package com.college.student.repository;

import com.college.student.pojo.Student;
import com.college.student.constant.StorageType;

import java.util.List;
//this is product interface
public interface StudentRepository {

    List<Student> listStudents();   //display all student details
    void  addStudent(Student student);  //adding student in list;
    Student deleteStudent(int rollNo);   //deleting specific student from list;
    Student updateStudentByRollNo(Student student);
    //update specific student by rollNo from list
    Student getStudentData(int studentRollNo);  //to get specific student data by rollNo;
    Student getCompleteStudentData(int studentRollNo);
    boolean isExist(int rollNo);
    public boolean accept(StorageType storageType);
}
