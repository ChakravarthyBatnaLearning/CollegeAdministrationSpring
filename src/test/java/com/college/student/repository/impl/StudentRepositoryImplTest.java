package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.pojo.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
@DataJpaTest
class StudentRepositoryImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentRepositoryImpl studentRepository;
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
//        // Set up the database with initial data
//        jdbcTemplate.execute("CREATE TABLE STUDENT (ROLL_NO INT PRIMARY KEY, NAME VARCHAR(255), AGE INT, PHONE_NUMBER VARCHAR(255), GENDER CHAR(1))");
//        jdbcTemplate.execute("INSERT INTO STUDENT (ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER) VALUES (1, 'John Doe', 20, '1234567890', 'M')");
//        jdbcTemplate.execute("INSERT INTO STUDENT (ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER) VALUES (2, 'Jane Doe', 22, '0987654321', 'F')");
        Student student = new Student();
        student.setRollNo(10);
        student.setName("chakri");
        student.setAge((byte) 24);
        testEntityManager.persist(student);
    }


    @Test
    void accept() {
        StorageType expectedStorageType = StorageType.DB;
        assertTrue(studentRepository.accept(expectedStorageType));
    }

    @Test
    void testListStudentsWithoutAssociations() throws Exception {
        Student student = studentRepository.getStudentData(10);
        assertNotNull(student);
//        List<Student> students = studentRepository.listStudents(false);
//        assertNotNull(students);
//        assertEquals(2, students.size());
//        assertEquals("John Doe", students.get(0).getName());
//        assertEquals("Jane Doe", students.get(1).getName());
    }

}