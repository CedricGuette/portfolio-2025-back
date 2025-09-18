package com.cedric_guette.portfolio.exceptions;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
    private final HttpHeadersCORS headersCORS = new HttpHeadersCORS();
    private final String headers = String.valueOf(headersCORS.headers());

    @ExceptionHandler(AdminAlreadyExistException.class)
    public ResponseEntity<ApiError> handleAdminAlreadyExistException(AdminAlreadyExistException e) {
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException e) {
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UserNotFoundException e) {
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiError> handleItemNotFoundException (ItemNotFoundException e) {
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(DatabaseAlreadyFilledException.class)
    public ResponseEntity<ApiError> handleDatabaseAlreadyFilledException(DatabaseAlreadyFilledException e) {
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ApiError> handleImageNotFoundException(ImageNotFoundException e){
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiError> handleFileNotFoundException(FileNotFoundException e){
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.NOT_EXTENDED.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(ErrorWhileSendingMailException.class)
    public ResponseEntity<ApiError> handleErrorWhileSendingMailException(ErrorWhileSendingMailException e){
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(UsernameAndPasswordDoNotMatchException.class)
    public ResponseEntity<ApiError> handleUsernameAndPasswordDoNotMatchException(UsernameAndPasswordDoNotMatchException e){
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(headers)
                .body(apiError);
    }

    @ExceptionHandler(CannotUpdateIndexException.class)
    public ResponseEntity<ApiError> handleCannotUpdateIndexException(CannotUpdateIndexException e){
        ApiError apiError = new ApiError();
        apiError.setError(e.getMessage());
        apiError.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiError.setTimestamp(LocalDateTime.now());

        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(headers)
                .body(apiError);
    }
}
