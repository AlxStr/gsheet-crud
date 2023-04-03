package com.gsheet.student.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
}
