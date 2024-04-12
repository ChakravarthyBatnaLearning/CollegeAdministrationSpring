package com.college.student.controller;

import com.college.student.pojo.Admission;
import com.college.student.pojo.ErrorResponse;
import com.college.student.service.AdmissionService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class AdmissionController {
    private static final Logger logger = LoggerFactory.getLogger(AdmissionController.class);

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping("/{studentRollNo}/admission")
    @ResponseBody
    public boolean addStudentAdmission(@RequestBody Admission admission, @PathVariable int studentRollNo) throws ErrorResponse {
        try {
            logger.info("Request to Add Student Admission Received");
            boolean result = admissionService.addStudentAdmission(admission, studentRollNo);
            logger.info("Successfully Added Student Admission");
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Adding Student Admission - Exception: {}", e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    // Getting Student Admission
    @GetMapping("/{rollNo}/admission")
    @ResponseBody
    public Admission getStudentAdmission(@PathVariable int rollNo) throws ErrorResponse {
        try {
            logger.info("Request to Get Student Admission Received");
            Admission admission = admissionService.getStudentAdmission(rollNo);
            logger.info("Successfully Retrieved Student Admission for RollNo : {}", rollNo);
            return admission;
        } catch (Exception e) {
            logger.error("Exception Occurred while Retrieving Student Admission for RollNo : {} - Exception: {}", rollNo, e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    // Deleting Student Admission
    @DeleteMapping("/{rollNo}/admission")
    @ResponseBody
    public boolean deleteStudentAdmission(@PathVariable int rollNo) throws ErrorResponse {
        try {
            logger.info("Request to Delete Student Admission Received");
            boolean result = admissionService.deleteStudentAdmission(rollNo);
            logger.info("Successfully Deleted Student Admission for RollNo : {}", rollNo);
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Deleting Student Admission for RollNo : {} - Exception: {}", rollNo, e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    // Updating Student Admission
    @PutMapping("/{studentRollNo}/admission")
    @ResponseBody
    public boolean updateStudentAdmission(@RequestBody Admission admission, @PathVariable String studentRollNo) throws ErrorResponse {
        try {
            logger.info("Request to Update Student Admission Received");
            boolean result = admissionService.updateStudentAdmission(admission, Integer.parseInt(studentRollNo));
            logger.info("Successfully Updated Student Admission");
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Updating Student Admission - Exception: {}", e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

}
