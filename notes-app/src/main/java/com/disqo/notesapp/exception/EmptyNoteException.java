package com.disqo.notesapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Not a valid note")
public class EmptyNoteException extends RuntimeException {
    public EmptyNoteException(String msg) {
        super(msg);
    }
}
