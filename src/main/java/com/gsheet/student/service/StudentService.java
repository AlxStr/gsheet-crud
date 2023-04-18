package com.gsheet.student.service;

import com.gsheet.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentServiceClient studentServiceClient;

    public List<StudentDto> getStudents() {
        return studentServiceClient.getStudents();
    }

    public StudentDto getStudent(UUID id) {
        return studentServiceClient.getStudent(id);
    }

    // Test case for learning purposes
    public StudentDto tryGetStudent(UUID id, String param) {
        StudentDto student = studentServiceClient.tryGetStudent(id, param);

        return student;
    }
}
