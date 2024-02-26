package com.college.student.controller.oldfiles;

import com.college.student.service.impl.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;


public class AdmissionControllerServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateStudentServlet.class);
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        logger.debug("AdmissionControllerServlet.java loaded at line 17");
        StudentServiceImpl studentServiceImpl = (StudentServiceImpl) request.getServletContext().getAttribute("studentService");
        String studentChoice = (String) request.getParameter("choice");
        PrintWriter out = response.getWriter();
        switch (studentChoice) {
            case "1" :  //add student
                response.sendRedirect("AddStudentData.html");
                break;
            case "2" : //list student data
                logger.debug("executes at line 50(display call) by administrationServlet");
                response.sendRedirect("ListStudentDataTable.html");
                logger.debug("executes at line 52(display call) by administrationServlet");
                break;
            case "3" : //delete student
                response.sendRedirect("DeleteStudentData.html");
                break;
            case "4" : //update student
                response.sendRedirect("GetRollNoFromClient.html?choice=4");
//
                break;
            case "5" : //get student data
                 response.sendRedirect("GetRollNoFromClient.html?choice=5");
                 break;
            case "0" :
                System.exit(0);
        }
    }
}
