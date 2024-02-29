package com.college.student.listener.impl;

import com.college.student.event.impl.AddStudentEvent;
import com.college.student.listener.IEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class AddStudentEventListener implements IEventListener<AddStudentEvent>, ApplicationListener<AddStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AddStudentEventListener.class);


    @Override
    public void onApplicationEvent(AddStudentEvent addStudentEvent) {
        logger.info("New Student Added");
        logger.info("Student Details : {}", addStudentEvent.getStudent());
        logger.info("Source : {}", addStudentEvent.getSource());
    }
}
