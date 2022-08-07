package com.project.cotroller.error;

import com.project.model.api.error.ApiError;
import com.project.model.exception.IdentityNumberException;
import com.project.model.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(NotFoundException ex) {
        logger.error("An error occurred when processing http request", ex);
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdentityNumberException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(IdentityNumberException id) {
        logger.error("An error occurred when processing http request", id);
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, id.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }
}