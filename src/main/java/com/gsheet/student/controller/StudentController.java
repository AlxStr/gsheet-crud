package com.gsheet.student.controller;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> all() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public StudentDto get(@PathVariable UUID id) {
        Optional<StudentDto> student = Optional.ofNullable(studentService.getStudent(id));

        return student.orElse(null);
    }

    @GetMapping("/{id}/test")
    public StudentDto tryGetStudent(@PathVariable UUID id, @RequestParam String param) {
        Optional<StudentDto> student = Optional.ofNullable(studentService.tryGetStudent(id, param));

        return student.orElse(null);
    }
}
