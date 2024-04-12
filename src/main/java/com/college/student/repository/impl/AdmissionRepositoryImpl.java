package com.college.student.repository.impl;

import com.college.student.pojo.Admission;
import com.college.student.repository.AdmissionRepository;
import com.college.student.repository.mappers.AdmissionRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmissionRepositoryImpl implements AdmissionRepository {
    private static final Logger logger = LoggerFactory.getLogger(AdmissionRepositoryImpl.class);

    private static final String INSERT_QUERY = "INSERT INTO (COURSE, SECTION, ADMISSION_YEAR, ROLL_NO) ADMISSION VALUES (?,?,?,?);";
    private static final String GET_QUERY = "SELECT COURSE, SECTION, ADMISSION_YEAR, ROLL_NO FROM ADMISSION WHERE ROLL_NO = ?";
    private static final String DELETE_QUERY = "DELETE FROM ADMISSION WHERE ROLL_NO = ?";
    private static final String UPDATE_QUERY = "UPDATE ADMISSION SET COURSE = ?, SECTION = ?, ADMISSION_YEAR = ? WHERE ROLL_NO = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo) {
        try {
            jdbcTemplate.update(INSERT_QUERY, admission.getCourse(), admission.getSection(), admission.getAdmissionYear(), studentRollNo);
            return true;
        } catch (Exception e) {
            logger.error("Error adding student admission: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Admission getStudentAdmission(int rollNo) {
        try {
            return jdbcTemplate.queryForObject(GET_QUERY, new Object[]{rollNo}, new AdmissionRowMapper());
        } catch (Exception e) {
            logger.error("Error getting student admission: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) {
        try {
            jdbcTemplate.update(DELETE_QUERY, rollNo);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting student admission: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) {
        try {
            jdbcTemplate.update(UPDATE_QUERY, admission.getCourse(), admission.getSection(), admission.getAdmissionYear(), studentRollNo);
            return true;
        } catch (Exception e) {
            logger.error("Error updating student admission: {}", e.getMessage());
            return false;
        }
    }
}
