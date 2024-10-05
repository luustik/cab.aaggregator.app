package cab.aggregator.app.driverservice.controller.exception;

import cab.aggregator.app.driverservice.dto.exception.ExceptionDto;
import cab.aggregator.app.driverservice.dto.exception.MultiExceptionDto;
import cab.aggregator.app.driverservice.exception.EntityNotFoundException;
import cab.aggregator.app.driverservice.exception.ResourceAlreadyExistsException;
import cab.aggregator.app.driverservice.utility.ExceptionMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleEntityNotFound(RuntimeException e){
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto handleResourceAlreadyExists(RuntimeException e){
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleIllegalState(RuntimeException e){
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultiExceptionDto handleMethodArgumentNotValid(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return MultiExceptionDto.builder()
                .message(ExceptionMessage.VALIDATION_FAILED_MESSAGE)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultiExceptionDto handleConstraintViolation(ConstraintViolationException e){
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return MultiExceptionDto.builder()
                .message(ExceptionMessage.VALIDATION_FAILED_MESSAGE)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleException(Exception e){
        return ExceptionDto.builder()
                .message(ExceptionMessage.INTERNAL_ERROR_MESSAGE + e.getMessage())
                .build();
    }
}

