package com.gsheet.student.config;

import com.gsheet.student.service.AuthTokenProvider;
import com.gsheet.student.service.GoogleAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class GoogleAuthConfig {

    @Value("${google.auth.credential.file-path}")
    private String credentialFilePath;

    @Bean
    public AuthTokenProvider googleAuthTokenProvider() throws IOException {
        String[] scopes = new String[]{"https://www.googleapis.com/auth/spreadsheets",
                "https://www.googleapis.com/auth/drive", "https://www.googleapis.com/auth/drive.file",};

        Resource credentialsResource = new ClassPathResource(credentialFilePath);

        return new GoogleAuthTokenProvider(credentialsResource, scopes);
    }
}
