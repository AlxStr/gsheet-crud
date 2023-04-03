package com.gsheet.student.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
public class GoogleSheetsRangeProvider {

    private final String from;

    private final String to;

    public String toString() {
        return String.format("%s:%s", from, to);
    }

    public String toString(int row) {
        return String.format("%s%s:%s%s", from, row, to, row);
    }

    public String toString(int x, int y) {
        return String.format("%s%s:%s%s", from, x, to, y);
    }
}
