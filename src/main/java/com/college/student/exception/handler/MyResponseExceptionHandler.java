package com.college.student.exception.handler;


import com.college.student.exception.*;
import com.college.student.pojo.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyResponseExceptionHandler.class);

    @ExceptionHandler(value = {AddStudentException.class})
    public ResponseEntity<Object> handleAddStudentException(AddStudentException ex, WebRequest request) {

        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        logger.error("Error While Adding Student data : ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {StudentNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(StudentNotFoundException ex, WebRequest request) {

        logger.error("Error While Getting Student data : ", ex);
        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {StudentUpdateException.class})
    public ResponseEntity<Object> handleStudentUpdateException(StudentUpdateException ex, WebRequest request) {

        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        logger.error("Error While Updating Student data : ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {StudentListNotFoundException.class})
    public ResponseEntity<Object> handleStudentListNotFoundException(StudentListNotFoundException ex, WebRequest request) {

        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        logger.error("Error While Getting Student List data : ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {DeleteStudentException.class})
    public ResponseEntity<Object> handleDeleteStudentException(DeleteStudentException ex, WebRequest request) {

        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        logger.error("Error While Deleting Student data : ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {

        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        logger.error("Error While Handling URI : {},", request.getDescription(true), ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
