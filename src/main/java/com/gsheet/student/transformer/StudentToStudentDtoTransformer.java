package com.gsheet.student.transformer;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.entity.Student;
import org.springframework.stereotype.Service;

@Service
final public class StudentToStudentDtoTransformer {
    public StudentDto transform(Student student) {
        return StudentDto.builder()
            .id(student.getId())
            .firstName(student.getFirstName())
            .middleName(student.getMiddleName())
            .lastName(student.getLastName())
            .build();
    }
}
