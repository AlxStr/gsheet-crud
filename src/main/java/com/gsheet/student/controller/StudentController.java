package com.gsheet.student.controller;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.entity.Student;
import com.gsheet.student.service.StudentService;
import com.gsheet.student.transformer.StudentToStudentDtoTransformer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentToStudentDtoTransformer studentToStudentDtoTransformer;

    @SneakyThrows
    @GetMapping
    public List<StudentDto> index() {
        return studentService.getStudents()
            .stream()
            .map(studentToStudentDtoTransformer::transform)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> get(@PathVariable int id) {
        Student student = studentService.getStudent(id);

        return ResponseEntity.ok(studentToStudentDtoTransformer.transform(student));
    }

    @PostMapping()
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto input) {
        Student student = studentService.createStudent(input);

        return ResponseEntity.ok(studentToStudentDtoTransformer.transform(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable int id, @RequestBody StudentDto input) {
        Student student = studentService.updateStudent(id, input);

        return ResponseEntity.ok(studentToStudentDtoTransformer.transform(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent()
            .build();
    }
}
