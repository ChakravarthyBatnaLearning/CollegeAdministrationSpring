package com.college.student.listener.impl;

import com.college.student.event.impl.GetAllStudentEvent;
import com.college.student.listener.IEventListener;
import com.college.student.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import java.util.List;

public class GetAllStudentEventListener implements IEventListener<GetAllStudentEvent> , ApplicationListener<GetAllStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GetAllStudentEventListener.class);


    @Override
    public void onApplicationEvent(GetAllStudentEvent getAllStudentEvent) {
        List<Student> studentList = getAllStudentEvent.getStudentList();
        logger.info("GetAllStudentEventListener Invoked with Source : {}",getAllStudentEvent.getSource());
        logger.info("Student List is : {}", studentList);
    }
}