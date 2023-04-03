package com.gsheet.student.config;

import com.gsheet.student.service.GoogleSheetsRangeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentPersistenceConfig {

    @Value("${app.student.persistence.range.from}")
    private String from;

    @Value("${app.student.persistence.range.to}")
    private String to;

    @Bean
    public GoogleSheetsRangeProvider studentPersistenceRangeProvider() {
        return new GoogleSheetsRangeProvider(from, to);
    }
}
