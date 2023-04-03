package com.gsheet.student.resource;

import lombok.Data;

import java.util.List;

@Data
public class ValueRangeResource {
    private String range;

    private List<List<String>> values;
}
