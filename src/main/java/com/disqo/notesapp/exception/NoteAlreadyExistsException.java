package com.disqo.notesapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Note already exists")
public class NoteAlreadyExistsException extends RuntimeException {
    public NoteAlreadyExistsException(String msg) {
        super(msg);
    }
}
