package com.college.student.controller;

import com.college.student.event.handler.EventHandler;
import com.college.student.event.impl.*;
import com.college.student.pojo.Student;
import com.college.student.service.StudentService;
import com.college.student.utils.HttpUtil;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8084")
// ctrl + alt + O to remove unused imports
@Controller
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    @ResponseBody
    public Student addStudentData(@RequestBody Student student, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession userSession = request.getSession(false);
        String cookieValue = HttpUtil.getCookieByName("my_auth_cookie", request);
        if (userSession.getAttribute(cookieValue) != null) {
            logger.info("User Found to be an Admin");
            try {
                logger.info("Request Received to Add Student");

                logger.info("Student Object Received : {}", student);
                studentService.addStudent(student);
                EventHandler.getInstance(true).publishEvent(new AddStudentEvent(this.getClass(), student));  // publish the event
                logger.info("Added Student to DB");
            } catch (Exception e) {
                logger.error("Exception Occurred while Added Student : ", e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        return student;
    }

    @GetMapping("/{rollNo}")
    @ResponseBody
    public Student getStudentData(HttpServletRequest request, HttpServletResponse response, @PathVariable String rollNo) throws IOException {
        Student student = null;
        logger.info("Request Received to Get the Student Details");
        logger.info("rollNo received {}", rollNo);
        logger.info("User name : {}", request.getSession(false).getAttribute("username"));
        try {
            student = studentService.getStudentByRollNo(Integer.parseInt(rollNo));
            logger.info("Student Details Received : {}", student);
            EventHandler.getInstance(false).publishEvent(new GetStudentEvent(this.getClass(), student));
        } catch (Exception e) {
            logger.error("Exception Occurred while Requested to Get Student data : ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;

    }

    @GetMapping()
    @ResponseBody
    public List<Student> getStudentList(HttpServletResponse response) {
        logger.info("Request Received to Get the Student Details");
        List<Student> studentList = null;
        logger.info("Request Received to List All Students");
        try {
            studentList = studentService.listStudents();
            logger.info("Student List Received : {}", studentList);
            EventHandler.getInstance(true).publishEvent(new GetAllStudentEvent(this.getClass(), studentList));
            logger.info("Student List  : {}", studentList);
        } catch (Exception e) {
            logger.error("Exception Occurred while Requesting the to List Student Data : ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return studentList;
    }

    @PutMapping()
    @ResponseBody
    public Student updateStudentData(@RequestBody Student student, HttpServletResponse response) {
        logger.info("Request Received to Update the Student Data");

        try {
            logger.info("Request to Update the Student : {}", student);
            student = studentService.updateStudentDetailsByRollNo(student);
            logger.info("Request Successfully Completed for Update for Student {}", student);
            EventHandler.getInstance(false).publishEvent(new UpdateStudentEvent(this.getClass(), student));
        } catch (Exception e) {
            logger.error("Exception Occurred while Updating the StudentByRollNo : ", e);
            response.setStatus(500);
        }
        logger.info("Request for Update Student Successfully Completed");
        return student;
    }

    @DeleteMapping("/{rollNo}")
    @ResponseBody
    public String deleteStudentData(@PathVariable String rollNo, HttpServletResponse response) throws JsonSyntaxException {
        logger.info("Request to Delete Student Received");
        try {
            logger.info("Successfully Received Student RollNo : {}", Integer.parseInt(rollNo));
            Student student = studentService.deleteStudentByRollNo(Integer.parseInt(rollNo));
            logger.info("Successfully Deleted the Student : {}", student);
            EventHandler.getInstance(false).publishEvent(new DeleteStudentEvent(this.getClass(), student));
        } catch (Exception e) {
            logger.info("Exception Occurred while Deleting a Student having rollNo : {} and Exception : ", Integer.parseInt(rollNo), e);
            response.setStatus(500);

        }
        return rollNo;
    }
}
