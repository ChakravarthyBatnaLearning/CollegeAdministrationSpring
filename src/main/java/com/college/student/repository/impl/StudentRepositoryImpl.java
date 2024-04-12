package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.exception.*;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.repository.mappers.ListStudentsWithAssociationsExtractor;
import com.college.student.repository.mappers.StudentRowMapper;
import com.college.student.repository.mappers.StudentWithAssociationsRowExtractor;
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

    private static final String LIST_QUERY = "SELECT ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER FROM STUDENT";
    private static final String INSERT_QUERY = "INSERT INTO (ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER) STUDENT VALUES (?,?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM STUDENT WHERE ROLL_NO = ?";
    private static final String UPDATE_QUERY = "UPDATE STUDENT SET NAME = ?, AGE = ?, PHONE_NUMBER = ?, GENDER = ? WHERE ROLL_NO = ?";
    private static final String GET_QUERY = "SELECT ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER  FROM student WHERE ROLL_NO = ?";
    private static final String SELECT_STUDENT_WITH_ASSOCIATIONS = "SELECT s.*, a.COUNTRY, a.STATE, a.CITY, admission.COURSE, admission.SECTION, " +
            "admission.ADMISSION_YEAR FROM STUDENT s  LEFT JOIN ADDRESS a ON s.ROLL_NO = a.ROLL_NO LEFT JOIN " +
            "ADMISSION admission ON s.ROLL_NO = admission.ROLL_NO WHERE s.ROLL_NO = ?";
    private static final String SELECT_STUDENTS_WITH_ASSOCIATIONS = "SELECT s.*, a.COUNTRY, a.STATE, a.CITY, admission.COURSE, admission.SECTION, " +
            "admission.ADMISSION_YEAR FROM STUDENT s  LEFT JOIN ADDRESS a ON s.ROLL_NO = a.ROLL_NO LEFT JOIN " +
            "ADMISSION admission ON s.ROLL_NO = admission.ROLL_NO";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean accept(StorageType storageType) {
        return storageType == StorageType.DB;
    }

    @Override
    public List<Student> listStudents(String flag) {
        try {
            if (flag.equals("true")) {
                return jdbcTemplate.query(SELECT_STUDENTS_WITH_ASSOCIATIONS, new ListStudentsWithAssociationsExtractor());
            } else {
                return jdbcTemplate.query(LIST_QUERY, new StudentRowMapper());
            }
        } catch (ListStudentException e) {
            logger.error("Error While getting the List of Students");
            throw e;
        }
    }


    @Override
    public void addStudent(@org.jetbrains.annotations.NotNull Student student) {
        int studentRowsEffected = 0;
        try {

            studentRowsEffected = jdbcTemplate.update(INSERT_QUERY, student.getRollNo(), student.getName(),
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
            jdbcTemplate.update(DELETE_QUERY, rollNo);
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
            int rowsEffected = jdbcTemplate.update(UPDATE_QUERY, student.getName(), student.getAge(), student.getPhoneNo(), student.getGender(), student.getRollNo());
            if (rowsEffected == 0) {
                logger.error("No Student Found with RollNo : {}", student.getRollNo() + " To Execute Update Query");
                throw new UpdateStudentException("No Student Found with RollNo : " + student.getRollNo(), HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (UpdateStudentException e) {
            logger.error("Error Occurred While Updating the Student {}", student);
            throw new UpdateStudentException("Error updating student with roll number: " + student.getRollNo(), 404);
        }
        return student;
    }

    @Override
    public Student getStudentData(int studentRollNo) {
        try {
            return jdbcTemplate.queryForObject(GET_QUERY, new Object[]{studentRollNo}, new StudentRowMapper());
        } catch (StudentNotFoundException e) {
            logger.error("Error While Getting Student with RollNo : {} exception", studentRollNo);
            throw e;
        }

    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) {
        return jdbcTemplate.query(SELECT_STUDENT_WITH_ASSOCIATIONS, new Object[]{studentRollNo}, new StudentWithAssociationsRowExtractor());
    }

    @Override
    public boolean isExist(int rollNo) {
        return getStudentData(rollNo) != null;
    }
}
