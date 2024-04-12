package com.college.student.controller;

import com.college.student.event.*;
import com.college.student.exception.*;
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
    public Student addStudentData(@RequestBody Student student, HttpServletRequest request) throws AddStudentException {
        HttpSession userSession = request.getSession(false);
        String cookieValue = HttpUtil.getCookieByName("my_auth_cookie", request);
        if (userSession.getAttribute(cookieValue) != null) {
            logger.info("User Found to be an Admin");
            try {
                logger.info("Request Received to Add Student : {}", student);
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
    public Student getStudentData(HttpServletRequest request, @PathVariable String rollNo) throws ListStudentException {
        Student student = null;
        logger.info("Request Received to Get the Student Details with RollNo : {} and UserName : {}", rollNo, (String) request.getSession(false).getAttribute("username"));
        try {
            student = studentService.getCompleteStudentData(Integer.parseInt(rollNo));
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

    @GetMapping("/list")
    @ResponseBody
    public List<Student> getStudentList(@RequestParam(value = "flag", required = false, defaultValue = "false") String flag) throws ListStudentException {
        List<Student> studentList = null;
        logger.info("Request Received to List All Students");
        try {
            studentList = studentService.listStudents(flag);
            if (studentList == null) {
                logger.info("No Student Data in Database, Returning EmptyList");
                return studentList;
            }
            logger.info("Student List Received : {}", studentList);
            applicationEventPublisher.publishEvent(new GetAllStudentEvent(this.getClass(), studentList));
            logger.info("Student List  : {}", studentList);
        } catch (ListStudentException e) {
            logger.error("Exception Occurred while Requesting to List Student Data : ", e);
        }
        assert studentList != null;
        studentList.sort(new StudentAgeAndGenderComparator());
        return studentList;
    }

    @PutMapping()
    @ResponseBody
    public Student updateStudentData(@RequestBody Student student) throws UpdateStudentException {
        try {
            logger.info("Request to Update the Student : {}", student);
            student = studentService.updateStudentDetailsByRollNo(student);
            if (student == null)
                throw new UpdateStudentException("Error While Updating the Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.info("Request Successfully Completed for Update for Student {}", student);
            applicationEventPublisher.publishEvent(new UpdateStudentEvent(this.getClass(), student));
            //        EventHandler.getInstance(false).publishEvent(new UpdateStudentEvent(this.getClass(), student));
        } catch (UpdateStudentException e) {
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
}
