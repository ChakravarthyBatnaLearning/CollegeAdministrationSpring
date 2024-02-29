package com.college.student.listener.impl;

import com.college.student.event.impl.DeleteStudentEvent;
import com.college.student.listener.IEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class DeleteStudentEventListener implements IEventListener<DeleteStudentEvent> , ApplicationListener<DeleteStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DeleteStudentEventListener.class);


    @Override
    public void onApplicationEvent(DeleteStudentEvent deleteStudentEvent) {
        logger.info("Student with RollNo {}", deleteStudentEvent.getStudent().getRollNo() + " has been Deleted");
        logger.info("Source : {}", deleteStudentEvent.getSource());
    }
}
