package com.college.student.controller.oldfiles;

import com.college.student.pojo.Student;
import com.college.student.service.impl.StudentServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteStudentServlet extends HttpServlet {
    private final StudentServiceImpl studentServiceImpl = new StudentServiceImpl("db");
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();

        // Read JSON data from the request body
        BufferedReader reader = request.getReader();
        String jsonRequestBody = reader.readLine();

        // Convert JSON data to Student object
        Student student = gson.fromJson(jsonRequestBody, Student.class);

        // Delete the student by roll number and get the deleted student
        Student deletedStudent = studentServiceImpl.deleteStudentByRollNo(student.getRollNo());

        // Convert deletedStudent object to JSON
        String jsonResponse = gson.toJson(deletedStudent);

        // Send the JSON response back to the client
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
        out.flush();
    }

}
