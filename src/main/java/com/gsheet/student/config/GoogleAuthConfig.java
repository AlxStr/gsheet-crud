package com.gsheet.student.config;

import com.gsheet.student.service.AuthTokenProvider;
import com.gsheet.student.service.GoogleAuthTokenProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "google.auth")
public class GoogleAuthConfig {

    private String credentialFilePath;

    private List<String> scopes = new ArrayList<>();

    @Bean
    public AuthTokenProvider googleAuthTokenProvider() throws IOException {
        Resource credentialsResource = new ClassPathResource(credentialFilePath);

        return GoogleAuthTokenProvider.create(credentialsResource, scopes.toArray(new String[scopes.size()]));
    }

    public List<String> getScopes() {
        return this.scopes;
    }

    public String getCredentialFilePath() {
        return this.credentialFilePath;
    }

    public String setCredentialFilePath(String value) {
        return this.credentialFilePath = value;
    }
}
