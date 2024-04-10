package com.college.student.repository;

import com.college.student.pojo.Admission;

public interface AdmissionRepository {
    boolean addStudentAdmission(Admission admission, int studentRollNo);

    Admission getStudentAdmission(int rollNo);

    boolean deleteStudentAdmission(int rollNo);

    boolean updateStudentAdmission(Admission admission, int studentRollNo);
}
