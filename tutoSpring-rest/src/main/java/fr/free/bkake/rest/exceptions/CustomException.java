package fr.free.bkake.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by bkake on 13/02/2017.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Request")
public class CustomException extends RuntimeException {


    /**
     * Unique ID for Serialized object
     */
    private static final long serialVersionUID = 4657491283614755649L;

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(String msg, Throwable t) {
        super(msg, t);
    }
}
