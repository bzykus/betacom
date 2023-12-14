package pl.com.betacom.task.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${betacom.app.exception.notFoundMessage}")
    private String notFoundMessage;
    @Value("${betacom.app.exception.alreadyExistsMessage}")
    private String alreadyExistsMessage;

    @ExceptionHandler(value = ItemNotFoundException.class)
    public ResponseEntity<String> itemNotFoundException(ItemNotFoundException itemNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);
    }

    @ExceptionHandler(value = ItemAlreadyExistsException.class)
    public ResponseEntity<String> itemAlreadyExistsException(ItemAlreadyExistsException itemAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(alreadyExistsMessage);
    }

}