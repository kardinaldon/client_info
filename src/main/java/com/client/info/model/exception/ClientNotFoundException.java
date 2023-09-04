package com.client.info.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientNotFoundException extends ResponseStatusException {

    public ClientNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Client not not found");
    }

}
