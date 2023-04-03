package com.gsheet.student.service;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.dto.google.ValueRangeUpdateDto;
import com.gsheet.student.entity.Student;
import com.gsheet.student.exception.StudentNotFoundException;
import com.gsheet.student.resource.ValueRangeResource;
import com.gsheet.student.transformer.ValueRangeResourceToStudentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final GoogleSheetService googleSheetService;

    @Qualifier("studentPersistenceRangeProvider")
    private final GoogleSheetsRangeProvider studentSheetsRangeProvider;

    public List<Student> getStudents() {
        ValueRangeResource valueRange = this.googleSheetService.getValueRange(studentSheetsRangeProvider.toString());

        var values = valueRange.getValues();
        return IntStream.range(0, values.size())
                .mapToObj(i -> ValueRangeResourceToStudentTransformer.transform(i+1, values.get(i)))
                .collect(Collectors.toList());
    }

    public Student getStudent(int id) {
        ValueRangeResource valueRange = googleSheetService.getValueRange(studentSheetsRangeProvider.toString(id));

        if (valueRange.getValues() == null || valueRange.getValues()
            .isEmpty()) {
            throw new StudentNotFoundException();
        }

        Student student = ValueRangeResourceToStudentTransformer.transform(id, valueRange.getValues()
            .get(0));
        student.setId(id);

        return student;
    }

    public Student updateStudent(int id, StudentDto input) {
        var resource = new ValueRangeResource();
        resource.setRange(studentSheetsRangeProvider.toString(id));

        List<List<String>> data = new ArrayList<>();
        data.add(Arrays.stream(new String[]{input.getFirstName(), input.getMiddleName(), input.getLastName()})
            .toList());
        resource.setValues(data);

        ValueRangeUpdateDto valueRange = ValueRangeUpdateDto.builder()
            .data(List.of(resource))
            .build();

        googleSheetService.updateValueRange(valueRange);

        return getStudent(id);
    }

    public Student createStudent(StudentDto input) {
        int id = getNextStudentId();
        return updateStudent(id, input);
    }

    public void deleteStudent(int id) {
        var resource = new ValueRangeResource();
        resource.setRange(studentSheetsRangeProvider.toString(id));

        List<List<String>> data = new ArrayList<>();
        resource.setValues(data);

        data.add(Arrays.stream(new String[]{"", "", ""})
            .toList());

        ValueRangeUpdateDto dto = ValueRangeUpdateDto.builder()
            .data(List.of(resource))
            .build();

        googleSheetService.updateValueRange(dto);
    }

    private int getNextStudentId() {
        List<Student> values = this.getStudents();

        if (values.size() == 0) {
            return 1;
        }

        Optional<Student> currentStudent = values.stream().filter(
                (Student s) -> Objects.isNull(s.getFirstName()) && Objects.isNull(s.getMiddleName()) && Objects.isNull(s.getLastName())
        ).findFirst();

        return currentStudent
                .map(student -> student.getId() + 1)
                .orElseGet(() -> values.get(values.size() - 1).getId() + 1);
    }
}
