package com.college.student.controller;

import com.college.student.pojo.Admission;
import com.college.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class AdmissionController {
    private static final Logger logger = LoggerFactory.getLogger(AdmissionController.class);

    @Autowired
    private final StudentService studentService;

    public AdmissionController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/{studentRollNo}/admission")
    @ResponseBody
    public boolean addStudentAdmission(@RequestBody Admission admission, @PathVariable int studentRollNo) {
        logger.info("Request to Add Student Admission Received");
        try {
            boolean result = studentService.addStudentAdmission(admission, studentRollNo);
            logger.info("Successfully Added Student Admission");
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Adding Student Admission - Exception: {}", e.getMessage());
            throw e;
        }
    }

    // Getting Student Admission
    @GetMapping("/{rollNo}/admission")
    @ResponseBody
    public Admission getStudentAdmission(@PathVariable int rollNo) {
        logger.info("Request to Get Student Admission Received");
        try {
            Admission admission = studentService.getStudentAdmission(rollNo);
            logger.info("Successfully Retrieved Student Admission for RollNo : {}", rollNo);
            return admission;
        } catch (Exception e) {
            logger.error("Exception Occurred while Retrieving Student Admission for RollNo : {} - Exception: {}", rollNo, e.getMessage());
            throw e;
        }
    }

    // Deleting Student Admission
    @DeleteMapping("/{rollNo}/admission")
    @ResponseBody
    public boolean deleteStudentAdmission(@PathVariable int rollNo) {
        logger.info("Request to Delete Student Admission Received");
        try {
            boolean result = studentService.deleteStudentAdmission(rollNo);
            logger.info("Successfully Deleted Student Admission for RollNo : {}", rollNo);
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Deleting Student Admission for RollNo : {} - Exception: {}", rollNo, e.getMessage());
            throw e;
        }
    }

    // Updating Student Admission
    @PutMapping("/{studentRollNo}/admission")
    @ResponseBody
    public boolean updateStudentAdmission(@RequestBody Admission admission, @PathVariable String studentRollNo) {
        logger.info("Request to Update Student Admission Received");
        try {
            boolean result = studentService.updateStudentAdmission(admission, Integer.parseInt(studentRollNo));
            logger.info("Successfully Updated Student Admission");
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Updating Student Admission - Exception: {}", e.getMessage());
            throw e;
        }
    }

}
