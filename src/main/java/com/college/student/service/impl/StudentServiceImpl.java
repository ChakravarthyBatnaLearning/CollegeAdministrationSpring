//StudentService class will call various method of StudentRepository to perform operations;
package com.college.student.service.impl;

import com.college.student.cache.lru_dll.LRUCache;
import com.college.student.constant.StorageType;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.repository.factory.StudentRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StudentServiceImpl implements com.college.student.service.StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private static final LRUCache<Integer, Student> lruCache = new LRUCache<>(5);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StorageType storageType, StudentRepositoryFactory studentRepositoryFactory) {
        this.studentRepository = studentRepositoryFactory.getStudentRepositoryInstance(storageType);
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
}
