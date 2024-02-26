package com.college.student.controller.oldfiles;

import com.college.student.pojo.Student;
import com.college.student.service.impl.StudentServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;


public class GetStudentDataServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateStudentServlet.class);
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");
        response.setContentType("application/json");
        //  response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
    //    StudentService studentService = (StudentService) request.getServletContext().getAttribute("studentService");
        StudentServiceImpl studentServiceImpl = new StudentServiceImpl("db");
        Student student = studentServiceImpl.getStudentByRollNo(Integer.parseInt(request.getParameter("rollNo")));
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String stuJson = gson.toJson(student);
        out.write(stuJson);
        out.flush();
        logger.debug("invokes line 27 of getStudentdataservlet.java");
    }
}
