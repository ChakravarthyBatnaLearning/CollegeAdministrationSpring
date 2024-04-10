package com.college.student.utils;

import com.college.student.pojo.Admission;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmissionRowMapper implements RowMapper<Admission> {

    @Override
    public Admission mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        Admission admission = new Admission();
        admission.setCourse(rs.getString("COURSE"));
        admission.setSection(rs.getInt("SECTION"));
        admission.setAdmissionYear(rs.getInt("ADMISSION_YEAR"));
        admission.setRollNo(rs.getInt("ROLL_NO"));
        return admission;
    }
}
