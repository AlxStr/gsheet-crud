package com.gsheet.student.dto.google;

import com.gsheet.student.resource.ValueRangeResource;
import lombok.Builder;

import java.util.List;

@lombok.Data
@Builder
public class ValueRangeUpdateDto {
    private final String valueInputOption = "RAW";

    private List<ValueRangeResource> data;
}
