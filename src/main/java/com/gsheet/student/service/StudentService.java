package com.gsheet.student.service;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.dto.google.ValueRangeUpdateDto;
import com.gsheet.student.entity.Student;
import com.gsheet.student.exception.StudentNotFoundException;
import com.gsheet.student.repository.GoogleSheetRepository;
import com.gsheet.student.resource.ValueRangeResource;
import com.gsheet.student.transformer.ValueRangeResourceToStudentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private final GoogleSheetRepository sheetRepository;

    @Autowired
    private ValueRangeResourceToStudentTransformer sheetValueRangeDtoToStudentDtoTransformer;

    public List<Student> getStudents() {
        ValueRangeResource valueRange = this.sheetRepository.getValueRange("A:C");

        List<Student> students = new ArrayList<>();
        int index = 0;
        for (List<String> values : valueRange.getValues()) {
            int id = ++index;
            students.add(sheetValueRangeDtoToStudentDtoTransformer.transform(id, values));
        }

        return students;
    }

    public Student getStudent(int id) {
        String range = String.format("A%1$s:C%1$s", id);

        ValueRangeResource valueRange = sheetRepository.getValueRange(range);

        if (valueRange.getValues() == null || valueRange.getValues()
            .isEmpty()) {
            throw new StudentNotFoundException();
        }

        Student student = sheetValueRangeDtoToStudentDtoTransformer.transform(id, valueRange.getValues()
            .get(0));
        student.setId(id);

        return student;
    }

    public Student updateStudent(int id, StudentDto input) {
        var resource = new ValueRangeResource();
        String range = String.format("A%1$s:C%1$s", id);
        resource.setRange(range);

        List<List<String>> data = new ArrayList<>();
        data.add(Arrays.stream(new String[]{input.getFirstName(), input.getMiddleName(), input.getLastName()})
            .toList());
        resource.setValues(data);

        ValueRangeUpdateDto valueRange = ValueRangeUpdateDto.builder()
            .data(List.of(resource))
            .build();

        sheetRepository.updateValueRange(valueRange);

        return getStudent(id);
    }

    public Student createStudent(StudentDto input) {
        int id = getNextStudentId();
        return updateStudent(id, input);
    }

    public void deleteStudent(int id) {
        var resource = new ValueRangeResource();
        String range = String.format("A%1$s:C%1$s", id);
        resource.setRange(range);

        List<List<String>> data = new ArrayList<>();
        resource.setValues(data);

        data.add(Arrays.stream(new String[]{"", "", ""})
            .toList());

        ValueRangeUpdateDto dto = ValueRangeUpdateDto.builder()
            .data(List.of(resource))
            .build();

        sheetRepository.updateValueRange(dto);
    }

    private int getNextStudentId() {
        List<Student> values = this.getStudents();

        if (values.size() == 0) {
            return 1;
        }

        int currentPosition = 1;
        for (Student value : values) {
            if (Optional.ofNullable(value.getFirstName())
                .isEmpty()
                    && Optional.ofNullable(value.getMiddleName())
                        .isEmpty()
                    && Optional.ofNullable(value.getLastName())
                        .isEmpty()) {
                return currentPosition;
            }
            ++currentPosition;
        }

        return currentPosition;
    }
}
