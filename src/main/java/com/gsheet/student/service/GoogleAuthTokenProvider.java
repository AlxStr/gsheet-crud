package com.gsheet.student.service;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.OAuth2Credentials;
import com.gsheet.student.exception.GoogleCredentialRefreshException;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class GoogleAuthTokenProvider implements AuthTokenProvider {

    private static GoogleAuthTokenProvider instance;

    private GoogleCredentials credentials;

    private final Resource credentialsResource;
    private final String[] scopes;

    private GoogleAuthTokenProvider(Resource credentialsResource, String[] scopes) {
        this.credentialsResource = credentialsResource;
        this.scopes = scopes;
    }

    public static GoogleAuthTokenProvider create(Resource credentialsResource, String[] scopes) {
        if (instance == null) {
            instance = new GoogleAuthTokenProvider(credentialsResource, scopes);
        }

        return instance;
    }

    public String getAccessToken() {
        this.initCredentials();

        AccessToken accessToken = credentials.getAccessToken();

        if (accessToken == null) {
            throw new GoogleCredentialRefreshException();
        }

        return accessToken.getTokenValue();
    }

    private void initCredentials() {
        if (this.credentials == null) {
            try {
                this.credentials = GoogleCredentials.fromStream(this.credentialsResource.getInputStream())
                        .createScoped(this.scopes);

                credentials.addChangeListener(OAuth2Credentials::refreshIfExpired);

                credentials.refreshIfExpired();
            } catch (IOException e) {
                throw new GoogleCredentialRefreshException();
            }
        }
    }
}
