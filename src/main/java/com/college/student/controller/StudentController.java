package com.college.student.controller;

import com.college.student.event.*;
import com.college.student.exception.*;
import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.service.StudentService;
import com.college.student.sort.StudentAgeAndGenderComparator;
import com.college.student.utils.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8084")
// ctrl + alt + O to remove unused imports
@Controller
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    @ResponseBody
    public Student addStudentData(@RequestBody Student student, HttpServletRequest request) throws AddStudentException{
        HttpSession userSession = request.getSession(false);
        String cookieValue = HttpUtil.getCookieByName("my_auth_cookie", request);
        if (userSession.getAttribute(cookieValue) != null) {
            logger.info("User Found to be an Admin");
            try {
                logger.info("Request Received to Add Student");
                logger.info("Student Object Received : {}", student);
                studentService.addStudent(student);
                applicationEventPublisher.publishEvent(new AddStudentEvent(this.getClass(), student));
                logger.info("Added Student to DB");
            } catch (AddStudentException e) {
                logger.error("Exception Occurred while Added Student : ", e);
                throw e;
            }
        }
        return student;
    }

    @GetMapping("/{rollNo}")
    @ResponseBody
    public Student getStudentData(HttpServletRequest request, @PathVariable String rollNo) throws StudentListNotFoundException {
        Student student = null;
        logger.info("Request Received to Get the Student Details");
        logger.info("rollNo received {}", rollNo);
        logger.info("User name : {}", request.getSession(false).getAttribute("username"));
        try {
            student = studentService.getStudentByRollNo(Integer.parseInt(rollNo));
            if (student == null) {
                logger.error("Exception Occurred  while Requested to Get Student data : ");
                throw new StudentNotFoundException("Student with RollNo : " + rollNo + " Not Found", HttpServletResponse.SC_NOT_FOUND);
            }
            logger.info("Student Details Received : {}", student);
            applicationEventPublisher.publishEvent(new GetStudentEvent(this.getClass(), student));
        } catch (StudentNotFoundException e) {
            logger.error("Exception Occurred while Requested to Get Student data : ", e);
            throw e;
        }
        return student;

    }

    @GetMapping()
    @ResponseBody
    public List<Student> getStudentList() throws StudentListNotFoundException {
        logger.info("Request Received to Get the Student Details");
        List<Student> studentList = null;
        logger.info("Request Received to List All Students");
        try {
            studentList = studentService.listStudents();
            if (studentList == null)
                throw new StudentListNotFoundException("No Students Are Found", HttpServletResponse.SC_NOT_FOUND);
            logger.info("Student List Received : {}", studentList);
            applicationEventPublisher.publishEvent(new GetAllStudentEvent(this.getClass(), studentList));
            logger.info("Student List  : {}", studentList);
        } catch (StudentListNotFoundException e) {
            logger.error("Exception Occurred while Requesting the to List Student Data : ", e);
            throw e;
        }
        Collections.sort(studentList, new StudentAgeAndGenderComparator());
        return studentList;
    }

    @PutMapping()
    @ResponseBody
    public Student updateStudentData(@RequestBody Student student) throws StudentUpdateException {
        logger.info("Request Received to Update the Student Data");

        try {
            logger.info("Request to Update the Student : {}", student);
            student = studentService.updateStudentDetailsByRollNo(student);
            if (student == null)
                throw new StudentUpdateException("Error While Updating the Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.info("Request Successfully Completed for Update for Student {}", student);
            applicationEventPublisher.publishEvent(new UpdateStudentEvent(this.getClass(), student));
            //        EventHandler.getInstance(false).publishEvent(new UpdateStudentEvent(this.getClass(), student));
        } catch (StudentUpdateException e) {
            logger.error("Exception Occurred while Updating the StudentByRollNo : ", e);
            throw e;
        }
        logger.info("Request for Update Student Successfully Completed");
        return student;
    }

    @DeleteMapping("/{rollNo}")
    @ResponseBody
    public String deleteStudentData(@PathVariable String rollNo) throws DeleteStudentException {
        logger.info("Request to Delete Student Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", Integer.parseInt(rollNo));
            Student student = studentService.deleteStudentByRollNo(Integer.parseInt(rollNo));
            if (student == null) throw new DeleteStudentException("Error While Deleting Student", 500);
            logger.info("Successfully Deleted the Student : {}", student);
            applicationEventPublisher.publishEvent(new DeleteStudentEvent(this.getClass(), student));
        } catch (DeleteStudentException e) {
            logger.info("Exception Occurred while Deleting a Student having rollNo : {} and Exception : ", Integer.parseInt(rollNo), e);
            throw e;
        }
        return rollNo;
    }

    // Adding Student Address
    @PostMapping("/address/{studentRollNo}")
    @ResponseBody
    public boolean addStudentAddress(@RequestBody Address studentAddress, @PathVariable int studentRollNo) {
        logger.info("Request to Add Student Address Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            boolean result = studentService.addStudentAddress(studentAddress, studentRollNo);
            logger.info("Successfully Added Address for Student RollNo : {}", studentRollNo);
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Adding Address for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw e;
        }
    }

    // Updating Student Address
    @PutMapping("/address/{studentRollNo}")
    @ResponseBody
    public Address updateStudentAddress(@RequestBody Address studentAddress, @PathVariable int studentRollNo) {
        logger.info("Request to Update Student Address Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            Address updatedAddress = studentService.updateStudentTemporaryAddress(studentAddress, studentRollNo);
            logger.info("Successfully Updated Address for Student RollNo : {}", studentRollNo);
            return updatedAddress;
        } catch (Exception e) {
            logger.error("Exception Occurred while Updating Address for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw e;
        }
    }

    // Deleting All Student Addresses
    @DeleteMapping("/address/{studentRollNo}")
    @ResponseBody
    public boolean deleteAllStudentAddresses(@PathVariable int studentRollNo) {
        logger.info("Request to Delete All Student Addresses Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            boolean result = studentService.deleteAllStudentAddresses(studentRollNo);
            logger.info("Successfully Deleted All Addresses for Student RollNo : {}", studentRollNo);
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Deleting All Addresses for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw e;
        }
    }

    // Getting Student Addresses
    @GetMapping("/address/{studentRollNo}")
    @ResponseBody
    public List<Address> getStudentAddresses(@PathVariable int studentRollNo) {
        logger.info("Request to Get Student Addresses Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            List<Address> addresses = studentService.getStudentAddresses(studentRollNo);
            logger.info("Successfully Retrieved Addresses for Student RollNo : {}", studentRollNo);
            return addresses;
        } catch (Exception e) {
            logger.error("Exception Occurred while Retrieving Addresses for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw e;
        }
    }

    // Getting Student Permanent Address
    @GetMapping("/address/{studentRollNo}/permanent")
    @ResponseBody
    public Address getStudentPermanentAddress(@PathVariable int studentRollNo) {
        logger.info("Request to Get Student Permanent Address Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            Address permanentAddress = studentService.getStudentPermanentAddress(studentRollNo);
            logger.info("Successfully Retrieved Permanent Address for Student RollNo : {}", studentRollNo);
            return permanentAddress;
        } catch (Exception e) {
            logger.error("Exception Occurred while Retrieving Permanent Address for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw e;
        }
    }

    // Getting Student Temporary Address
    @GetMapping("/address/{studentRollNo}/temporary")
    @ResponseBody
    public Address getStudentTemporaryAddress(@PathVariable int studentRollNo) {
        logger.info("Request to Get Student Temporary Address Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            Address temporaryAddress = studentService.getStudentTemporaryAddress(studentRollNo);
            logger.info("Successfully Retrieved Temporary Address for Student RollNo : {}", studentRollNo);
            return temporaryAddress;
        } catch (Exception e) {
            logger.error("Exception Occurred while Retrieving Temporary Address for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw e;
        }
    }

    // Adding Student Admission
    @PostMapping("/admission/{studentRollNo}")
    @ResponseBody
    public boolean addStudentAdmission(@RequestBody Admission admission,@PathVariable int studentRollNo) {
        logger.info("Request to Add Student Admission Received");
        try {
            boolean result = studentService.addStudentAdmission(admission,studentRollNo);
            logger.info("Successfully Added Student Admission");
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Adding Student Admission - Exception: {}", e.getMessage());
            throw e;
        }
    }

    // Getting Student Admission
    @GetMapping("/admission/{rollNo}")
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
    @DeleteMapping("/admission/{rollNo}")
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
    @PutMapping("/admission/{studentRollNo}")
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
