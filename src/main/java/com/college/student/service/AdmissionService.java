package com.college.student.service;

import com.college.student.pojo.Admission;

public interface AdmissionService {
    //admission repository method's
    boolean addStudentAdmission(Admission admission, int studentRollNo);

    Admission getStudentAdmission(int rollNo);

    boolean deleteStudentAdmission(int rollNo);

    boolean updateStudentAdmission(Admission admission, int studentRollNo);

}
