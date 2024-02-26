package com.college.student.repository.factory;

import com.college.student.repository.StudentRepository;
import com.college.student.repository.impl.InCSVFileStudentRepositoryImpl;
import com.college.student.repository.impl.InDBRepositoryImplementation;
import com.college.student.repository.impl.InFileStudentRepositoryImpl;
import com.college.student.repository.impl.InMemoryStudentRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

//this is factory
public class StudentRepositoryFactory {
    private static StudentRepository studentRepository;
    private static final Map<String,StudentRepository> map = new HashMap<>();
    public static StudentRepository getStudentRepositoryInstance(String storageType) {
        if(storageType == null) return null;
        if (!map.isEmpty()) {

        }
        if(storageType.equals("InMemory") || storageType.equals("inmemory")) {
            studentRepository = new InMemoryStudentRepositoryImpl();
            map.put(storageType,studentRepository);
        } else if(storageType.equals("FileMemory") || storageType.equals("filememory")){
            studentRepository = new InFileStudentRepositoryImpl();
            map.put(storageType,studentRepository);
        } else if(storageType.equals("csv") || storageType.equals("CSV")) {
            studentRepository = new InCSVFileStudentRepositoryImpl();
            map.put(storageType,studentRepository);
        } else if (storageType.equals("db") || storageType.equals("indb")) {
            studentRepository = new InDBRepositoryImplementation();
            map.put(storageType,studentRepository);
        }
//        return studentRepository;
        return map.get(storageType);
    }
}
