package com.gsheet.student.service;

import com.gsheet.student.dto.google.ValueRangeUpdateDto;
import com.gsheet.student.resource.ValueRangeResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleSheetService {
    @Value("${google.docs.spreadsheet-id}")
    private String spreadsheetId;

    private final GoogleSheetsClient googleSheetsClient;

    @Qualifier("googleAuthTokenProvider")
    private final AuthTokenProvider googleAuthTokenProvider;

    public ValueRangeResource getValueRange(String range) {
        return googleSheetsClient.getSheetValueRange(this.googleAuthTokenProvider.getAccessToken(), this.spreadsheetId,
                range);
    }

    public void updateValueRange(ValueRangeUpdateDto dto) {
        googleSheetsClient.updateValueRange(this.googleAuthTokenProvider.getAccessToken(), this.spreadsheetId, dto);
    }
}
