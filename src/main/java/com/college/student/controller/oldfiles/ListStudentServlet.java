package com.college.student.controller.oldfiles;

import com.college.student.pojo.Student;
import com.college.student.service.impl.StudentServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ListStudentServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ListStudentServlet.class);
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
        logger.debug("Occurs at line 23 of ListStudentServlet.java");
        //studentService is an (object) so explicitly typecasting to (StudentService)object
       StudentServiceImpl studentServiceImpl = new StudentServiceImpl("db");
 //      StudentService studentService = (StudentService) request.getServletContext().getAttribute("studentService");
        List<Student> studentList = studentServiceImpl.listStudents();
        response.setContentType("application/json");
      //  response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
//        creating a Gson Object to convert java objects into json format
        Gson gson = new Gson();
//        creating printWriter to send the json object to client Web Browser
        PrintWriter out = response.getWriter();
//        converting list of students to json string
        String json = gson.toJson(studentList);
        out.print(json);  //writing the json object into client's web
        out.flush();
        logger.debug("Occurs at line 36 of ListStudentServlet.java");
//        request.getRequestDispatcher("ListStudents.html").forward(request,response);
    }
}
