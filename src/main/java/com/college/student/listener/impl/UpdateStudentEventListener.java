package com.college.student.listener.impl;

import com.college.student.event.impl.UpdateStudentEvent;
import com.college.student.listener.IEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class UpdateStudentEventListener implements IEventListener<UpdateStudentEvent> , ApplicationListener<UpdateStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateStudentEventListener.class);


    @Override
    public void onApplicationEvent(UpdateStudentEvent updateStudentEvent) {
        logger.info("Student With rollNo : {}", updateStudentEvent.getStudent().getRollNo() + " has been Updated");
        logger.info("Source : {}", updateStudentEvent.getSource());
    }
}
