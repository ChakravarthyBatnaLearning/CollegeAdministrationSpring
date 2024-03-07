package com.college.student.repository.impl;

import com.college.student.exception.*;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.constant.StorageType;
import com.college.student.utils.StudentRowMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class InDBRepositoryImplementation implements StudentRepository {
    private static final Logger logger = LoggerFactory.getLogger(InDBRepositoryImplementation.class);
@Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean accept(StorageType storageType) {
        return storageType == StorageType.DB;
    }

    @Override
    public List<Student> listStudents() {
        String query = "select * from student";
        try {
            return jdbcTemplate.query(query, new StudentRowMapper());
        } catch (StudentListNotFoundException e) {
            logger.error("Error While getting the List of Students");
            throw e;
        }
    }


    @Override
    public void addStudent(@org.jetbrains.annotations.NotNull Student student) {
        String query = "insert into student values(?,?,?,?,?)";
        try {
            int rowsEffected = jdbcTemplate.update(query, student.getRollNo(), student.getName(), student.getAge(), student.getPhoneNo(), student.getGender());
            if (rowsEffected == 0)
                throw new AddStudentException("Error Occured While Adding the Student " + student, 500);
            logger.error("Exception While Adding the Student {}", student);
            System.out.println();
        } catch (AddStudentException e) {
            logger.error("Error While Adding the Student : {}", student);
            throw e;
        }
    }

    @Override
    public Student deleteStudent(int rollNo) {
        String deleteQuery = "delete from student where rollNo = ?";
        Student student = new Student();
        try {
            student = getStudentData(rollNo);
            jdbcTemplate.update(deleteQuery, rollNo);
            logger.info("Student Successfully Deleted");
        } catch (DeleteStudentException e) {
            logger.error("Error While Deleting the Student with RollNo : {}", rollNo);
            throw e;
        }
        return student;
    }

    @Override
    public Student updateStudentByRollNo(Student student) {
        String query = "update student set name = ?, age = ?, phoneNumber = ?, GENDER = ? where rollNo = ?";
        try {
            int rowsEffected = jdbcTemplate.update(query, student.getName(), student.getAge(), student.getPhoneNo(), student.getGender(),student.getRollNo());
            if (rowsEffected == 0) {
                logger.error("No Student Found with RollNo : {}", student.getRollNo() + " To Exeucte Update Query");
                throw new StudentUpdateException("No Student Found with RollNo : " + student.getRollNo(), HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (StudentUpdateException e) {
            logger.error("Error Occurred While Updating the Student {}", student);
            throw new StudentUpdateException("Error updating student with roll number: " + student.getRollNo(), 404);
        }
        return student;
    }

    @Override
    public Student getStudentData(int studentRollNo) {
        String selectQuery = "select * from student where rollNo = ?";
        try {
            return jdbcTemplate.queryForObject(selectQuery, new Object[]{studentRollNo}, new StudentRowMapper());
        } catch (StudentNotFoundException e) {
            logger.error("Error While Getting Student with RollNo : {} excption", studentRollNo);
            throw e;
        }

    }

    @Override
    public boolean isExist(int rollNo) {
        return getStudentData(rollNo) != null;
    }
}
