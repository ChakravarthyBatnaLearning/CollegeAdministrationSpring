package com.college.student.controller.oldfiles;

import com.college.student.service.impl.StudentServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SetStorageTypeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String storageType = request.getParameter("storageType");  //storageType
        StudentServiceImpl studentServiceImpl = new StudentServiceImpl(storageType);  //send storageType in StudentService class
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("studentService", studentServiceImpl); //make studentService object as global attribute
        try {
            response.sendRedirect("StudentChoices.html");  //now ask client to select
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
