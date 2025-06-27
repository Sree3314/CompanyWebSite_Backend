package com.example.MainProject.Exception; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;
 
@ControllerAdvice // This annotation makes it a global exception handler
public class GlobalExceptionHandler {
 
    /**
     * Handles MethodArgumentNotValidException (thrown when @Valid validation fails).
     * Returns a 400 Bad Request with a map of field errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.err.println("GlobalExceptionHandler: Caught MethodArgumentNotValidException. Returning 400. Errors: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
 
    /**
     * Optional: Generic exception handler for any other unhandled exceptions.
     * Catches any remaining Exception and returns a 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {
        System.err.println("GlobalExceptionHandler: Caught unexpected Exception. Returning 500. Message: " + ex.getMessage());
        ex.printStackTrace(); // Log the full stack trace for debugging
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
 
 