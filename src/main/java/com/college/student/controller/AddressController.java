package com.college.student.controller;

import com.college.student.pojo.ErrorResponse;
import com.college.student.repository.constants.AddressType;
import com.college.student.pojo.Address;
import com.college.student.service.AddressService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{studentRollNo}/address")
    @ResponseBody
    public boolean addStudentAddress(@RequestBody Address studentAddress, @PathVariable int studentRollNo) throws ErrorResponse {
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            boolean result = addressService.addStudentAddress(studentAddress, studentRollNo);
            logger.info("Successfully Added Address for Student RollNo : {}", studentRollNo);
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Adding Address for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND,e.getMessage());
        }
    }

    // Updating Student Address
    @PutMapping("/{studentRollNo}/address")
    @ResponseBody
    public Address updateStudentAddress(@RequestBody Address studentAddress, @PathVariable int studentRollNo) throws ErrorResponse {
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            Address updatedAddress = addressService.updateStudentAddressByRollNo(studentRollNo, studentAddress, studentAddress.getAddressType());
            logger.info("Successfully Updated Address for Student RollNo : {}", studentRollNo);
            return updatedAddress;
        } catch (Exception e) {
            logger.error("Exception Occurred while Updating Address for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND,e.getMessage());
        }
    }

    // Deleting All Student Addresses
    @DeleteMapping("/{studentRollNo}/address")
    @ResponseBody
    public boolean deleteAllStudentAddresses(@PathVariable int studentRollNo) throws ErrorResponse {
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            boolean result = addressService.deleteAllStudentAddresses(studentRollNo);
            logger.info("Successfully Deleted All Addresses for Student RollNo : {}", studentRollNo);
            return result;
        } catch (Exception e) {
            logger.error("Exception Occurred while Deleting All Addresses for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND,e.getMessage());
        }
    }

    // Getting Student Addresses
    @GetMapping("/{studentRollNo}/address")
    @ResponseBody
    public List<Address> getStudentAddresses(@PathVariable int studentRollNo) throws ErrorResponse {
        try {
            logger.info("Successfully Received Student RollNo : {}", studentRollNo);
            List<Address> addresses = addressService.getStudentAddresses(studentRollNo);
            logger.info("Successfully Retrieved Addresses for Student RollNo : {}", studentRollNo);
            return addresses;
        } catch (Exception e) {
            logger.error("Exception Occurred while Retrieving Addresses for Student RollNo : {} - Exception: {}", studentRollNo, e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND,e.getMessage());
        }
    }

    // Getting Student  Address
    @GetMapping("/{rollNo}/address/{addressType}")
    @ResponseBody
    public Address getStudentAddressByRollNo(@PathVariable int rollNo, @PathVariable AddressType addressType) throws ErrorResponse {
        try {
            logger.info("Successfully Received Student RollNo : {}", rollNo);
            Address temporaryAddress = addressService.getStudentAddressByRollNo(rollNo, addressType);
            logger.info("Successfully Retrieved Temporary Address for Student RollNo : {}", rollNo);
            return temporaryAddress;
        } catch (Exception e) {
            logger.error("Exception Occurred while Retrieving Temporary Address for Student RollNo : {} - Exception: {}", rollNo, e.getMessage());
            throw new ErrorResponse(HttpServletResponse.SC_NOT_FOUND,e.getMessage());
        }
    }
}
