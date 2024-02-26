package com.college.student.repository.impl;

import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.utils.DBConnector;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InDBRepositoryImplementation implements StudentRepository {
    @Override
    public List<Student> listStudents() {
        String query = "select * from student";
        List<Student> studentList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery(query);
            while (resultset.next()) {
                Student student = new Student();
                student.setRollNo(resultset.getInt(1));
                student.setName(resultset.getString("name"));
                student.setAge(resultset.getByte(3));
                student.setPhoneNo(resultset.getLong(4));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }


    @Override
    public void addStudent(@org.jetbrains.annotations.NotNull Student student) {
        String query = "insert into student values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(query);
            preparedStatement.setInt(1,student.getRollNo());
            preparedStatement.setString(2,student.getName());
            preparedStatement.setByte(3,student.getAge());
            preparedStatement.setLong(4,student.getPhoneNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student deleteStudent(int rollNo) {
        String selectQuery = "select * from student where rollNo = ?";
        String deleteQuery = "delete from student where rollNo = ?";
        Student student = new Student();
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(selectQuery);
            preparedStatement.setInt(1,rollNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                student.setRollNo(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getByte(3));
                student.setPhoneNo(resultSet.getLong(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(deleteQuery);
            preparedStatement.setInt(1,rollNo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Student updateStudentByRollNo(Student student) {
        String query = "update student set name = ?, age = ?, phoneNumber = ? where rollNo = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(query);
            preparedStatement.setString(1,student.getName());
            preparedStatement.setByte(2,student.getAge());
            preparedStatement.setLong(3,student.getPhoneNo());
            preparedStatement.setInt(4,student.getRollNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Student getStudentData(int studentRollNo) {
        String selectQuery = "select * from student where rollNo = ?";
        Student student = new Student();
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(selectQuery);
            preparedStatement.setInt(1,studentRollNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                student.setRollNo(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getByte(3));
                student.setPhoneNo(resultSet.getLong(4));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isExist(int rollNo) {
        return getStudentData(rollNo) != null;
    }
}
