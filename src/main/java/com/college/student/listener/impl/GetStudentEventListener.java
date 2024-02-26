package com.college.student.listener.impl;

import com.college.student.event.impl.GetStudentEvent;
import com.college.student.listener.IEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetStudentEventListener implements IEventListener<GetStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GetStudentEventListener.class);

    @Override
    public void onEvent(GetStudentEvent getStudentEvent) {
        logger.info("Student Data Received : {}", getStudentEvent.getStudent());
        logger.info("Source : {}", getStudentEvent);
    }
}
