package com.gsheet.student.service;

import com.gsheet.student.resource.ValueRangeResource;
import com.gsheet.student.dto.google.ValueRangeUpdateDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "googleSheets", url = "https://sheets.googleapis.com/v4/spreadsheets")
@Headers({"Content-Type: application/json", "Authorization: Bearer {access_token}"})
public interface GoogleSheetsClient {
    @GetMapping("/{id}/values/{range}")
    ValueRangeResource getSheetValueRange(@RequestParam("access_token") String token,
            @PathVariable(value = "id") String id, @PathVariable(value = "range") String range);

    @PostMapping("/{id}/values/{range}:batchUpdate")
    void updateValueRange(@RequestParam("access_token") String token, @PathVariable(value = "id") String id,
            @RequestBody() ValueRangeUpdateDto dto);
}
