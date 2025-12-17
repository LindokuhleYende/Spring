package guru.springframework.sfgrestbrewery.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jt on 2019-05-25.
 */
@ControllerAdvice
public class MvcExceptionHandler {

    // Handles validation errors from @Validated constraints
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e){
        // Create a list to store error messages
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());

        // Loop through validation errors and format them as strings
        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });

        // Return list of errors with HTTP 400 Bad Request
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handles binding errors (e.g., invalid request body fields)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List> handleBindException(BindException ex){
        // Return all binding errors with HTTP 400 Bad Request
        return new ResponseEntity(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
