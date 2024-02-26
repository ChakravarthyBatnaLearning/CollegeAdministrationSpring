package com.college.student.service;

import com.college.student.pojo.Student;

import java.util.List;

public interface StudentService {
    public void addStudent(Student student);
    public List<Student> listStudents();
    public Student deleteStudentByRollNo(int rollNo);
    public Student updateStudentDetailsByRollNo(Student updateStudent);
    public Student getStudentByRollNo(int studentRollNo);
    public boolean isStudentExist(int rollNo);
}
