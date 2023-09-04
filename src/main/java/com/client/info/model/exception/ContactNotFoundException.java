package com.client.info.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ContactNotFoundException extends ResponseStatusException {

    public ContactNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Contact not not found");
    }
}
