package com.gsheet.student.service;

import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;

@RequiredArgsConstructor
public class GoogleAuthTokenProvider implements AuthTokenProvider {
    private final Resource credentialsResource;
    private final String[] scopes;

    @SneakyThrows
    public String getAccessToken() {
        GoogleCredentials credential = GoogleCredentials.fromStream(this.credentialsResource.getInputStream())
            .createScoped(this.scopes);

        credential.refreshIfExpired();

        return credential.getAccessToken()
            .getTokenValue();
    }
}
