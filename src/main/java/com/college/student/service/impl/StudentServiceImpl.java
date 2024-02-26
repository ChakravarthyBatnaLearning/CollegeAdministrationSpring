//StudentService class will call various method of StudentRepository to perform operations;
package com.college.student.service.impl;

import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.repository.factory.StudentRepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class StudentServiceImpl implements com.college.student.service.StudentService {

    private final StudentRepository studentRepository;

    private StudentRepositoryFactory studentRepositoryFactory;
    public StudentServiceImpl(String storageType,StudentRepositoryFactory studentRepositoryFactory) {
        this.studentRepositoryFactory = studentRepositoryFactory;
        this.studentRepository = this.studentRepositoryFactory.getStudentRepositoryInstance(storageType);
    }

    public void addStudent(Student student) {
        this.studentRepository.addStudent(student);
    }
    public List<Student> listStudents() {
        return this.studentRepository.listStudents();
    }

    public Student deleteStudentByRollNo(int rollNo) {
        return this.studentRepository.deleteStudent(rollNo);
    }

    public Student updateStudentDetailsByRollNo(Student updateStudent) {
       return this.studentRepository.updateStudentByRollNo(updateStudent);
    }

    public Student getStudentByRollNo(int studentRollNo) {
        return  this.studentRepository.getStudentData(studentRollNo);
    }
    public boolean isStudentExist(int rollNo) {
        return this.studentRepository.isExist(rollNo);
    }
}
