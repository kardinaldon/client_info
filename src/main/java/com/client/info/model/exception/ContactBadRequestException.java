package com.client.info.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ContactBadRequestException extends ResponseStatusException {

    public ContactBadRequestException(String cause) {
        super(HttpStatus.BAD_REQUEST, cause);
    }
}
