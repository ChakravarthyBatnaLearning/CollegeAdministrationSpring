package com.college.student.listener;

import com.college.student.event.handler.EventHandler;
import com.college.student.event.impl.*;
import com.college.student.listener.impl.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterEventListeners implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(RegisterEventListeners.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Context Initialized");
        EventHandler.registerListener(AddStudentEvent.class, new AddStudentEventListener());
        EventHandler.registerListener(DeleteStudentEvent.class, new DeleteStudentEventListener());
        EventHandler.registerListener(GetAllStudentEvent.class, new GetAllStudentEventListener());
        EventHandler.registerListener(GetStudentEvent.class, new GetStudentEventListener());
        EventHandler.registerListener(UpdateStudentEvent.class, new UpdateStudentEventListener());
        logger.info("Listeners Added to Specific Events");

        new Thread(() -> {
            logger.info("New Thread Started in contextInitialized()");
            EventHandler.invokeListeners();
        }).start();

    }
}
