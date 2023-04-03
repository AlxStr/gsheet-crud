package com.gsheet.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GoogleCredentialRefreshException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Google token update error";
    }
}
