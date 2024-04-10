package com.college.student.repository.impl.indb;

import com.college.student.constant.StorageType;
import com.college.student.exception.*;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.utils.StudentRowMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRepositoryImpl implements StudentRepository {
    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryImpl.class);
    private static final String listQuery = "SELECT * FROM student";
    private static final String insertQuery = "INSERT INTO student VALUES (?,?,?,?,?)";
    private static final String deleteQuery = "DELETE FROM student WHERE ROLL_NO = ?";
    private static final String updateQuery = "UPDATE student SET NAME = ?, AGE = ?, PHONE_NUMBER = ?, GENDER = ? WHERE ROLL_NO = ?";
    private static final String getQuery = "SELECT * FROM student WHERE ROLL_NO = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean accept(StorageType storageType) {
        return storageType == StorageType.DB;
    }

    @Override
    public List<Student> listStudents() {
        try {
            return jdbcTemplate.query(listQuery, new StudentRowMapper());
        } catch (StudentListNotFoundException e) {
            logger.error("Error While getting the List of Students");
            throw e;
        }
    }


    @Override
    public void addStudent(@org.jetbrains.annotations.NotNull Student student) {
        int studentRowsEffected = 0;
        try {

            studentRowsEffected = jdbcTemplate.update(insertQuery, student.getRollNo(), student.getName(),
                    student.getAge(), student.getPhoneNo(), student.getGender());

            if (studentRowsEffected == 0) {
                logger.error("Exception While Adding the Student {}", student);
                throw new AddStudentException("Error Occured While Adding the Student " + student, 500);
            }

        } catch (AddStudentException e) {
            logger.error("Error While Adding the Student : {}", student);
            throw e;
        }
    }

    @Override
    public Student deleteStudent(int rollNo) {
        Student student;
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
        try {
            int rowsEffected = jdbcTemplate.update(updateQuery, student.getName(), student.getAge(), student.getPhoneNo(), student.getGender(), student.getRollNo());
            if (rowsEffected == 0) {
                logger.error("No Student Found with RollNo : {}", student.getRollNo() + " To Execute Update Query");
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
        try {
            return jdbcTemplate.queryForObject(getQuery, new Object[]{studentRollNo}, new StudentRowMapper());
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
