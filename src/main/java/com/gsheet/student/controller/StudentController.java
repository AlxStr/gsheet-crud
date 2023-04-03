package com.gsheet.student.controller;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.entity.Student;
import com.gsheet.student.service.StudentService;
import com.gsheet.student.transformer.StudentToStudentDtoTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> all() {
        return studentService.getStudents()
            .stream()
            .map(StudentToStudentDtoTransformer::transform)
            .toList();
    }

    @GetMapping("/{id}")
    public StudentDto get(@PathVariable int id) {
        Student student = studentService.getStudent(id);

        return StudentToStudentDtoTransformer.transform(student);
    }

    @PostMapping
    public StudentDto create(@RequestBody StudentDto input) {
        Student student = studentService.createStudent(input);

        return StudentToStudentDtoTransformer.transform(student);
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable int id, @RequestBody StudentDto input) {
        Student student = studentService.updateStudent(id, input);

        return StudentToStudentDtoTransformer.transform(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        studentService.deleteStudent(id);
    }
}
