package com.gsheet.student.repository;

import com.gsheet.student.dto.google.ValueRangeUpdateDto;
import com.gsheet.student.resource.ValueRangeResource;
import com.gsheet.student.service.AuthTokenProvider;
import com.gsheet.student.service.GoogleSheetsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleSheetRepository {
    @Value("${google.docs.spreadsheet-id}")
    private String spreadsheetId;

    @Autowired
    private GoogleSheetsClient googleSheets;

    @Autowired
    @Qualifier("googleAuthTokenProvider")
    private AuthTokenProvider googleAuthTokenProvider;

    public ValueRangeResource getValueRange(String range) {
        return googleSheets.getSheetValueRange(this.googleAuthTokenProvider.getAccessToken(), this.spreadsheetId,
                range);
    }

    public void updateValueRange(ValueRangeUpdateDto dto) {
        googleSheets.updateValueRange(this.googleAuthTokenProvider.getAccessToken(), this.spreadsheetId, dto);
    }
}
