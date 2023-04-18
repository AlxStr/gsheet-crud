package com.gsheet.student.service;

import com.gsheet.student.dto.StudentDto;
import lombok.Builder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "studentServiceClient", url = "${student.service.url}")
public interface StudentServiceClient {

    @GetMapping("/")
    List<StudentDto> getStudents();

    @GetMapping("/{id}")
    StudentDto getStudent(@PathVariable(value = "id") UUID id);

    @GetMapping("/{id}?param={param}")
    StudentDto tryGetStudent(@PathVariable(value = "id") UUID id, @PathVariable(value = "param") String param);
}
