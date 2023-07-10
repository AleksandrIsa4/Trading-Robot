package org.Isa4.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.exceptions.model.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collections;

@RestControllerAdvice
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionsHandler {

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final MethodArgumentNotValidException validException) {
        return badRequest(validException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final MethodArgumentTypeMismatchException mismatchException) {
        return badRequest(mismatchException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final MissingServletRequestParameterException missingParameterException) {
        return badRequest(missingParameterException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final DataIntegrityViolationException violationException) {
        return conflict(violationException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final DataNotFoundException notFoundException) {
        log.warn("404 {}", notFoundException.getMessage());
        notFoundException.printStackTrace(pw);
        return ApiError.builder()
                .errors(Collections.singletonList(sw.toString()))
                .status(HttpStatus.NOT_FOUND)
                .reason("The required object was not found.")
                .message(notFoundException.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleNotFound(final ConflictException conflictException) {
        log.warn("409 {}", conflictException.getMessage());
        conflictException.printStackTrace(pw);
        return ApiError.builder()
                .errors(Collections.singletonList(sw.toString()))
                .status(HttpStatus.CONFLICT)
                .reason("Integrity constraint has been violated.")
                .message(conflictException.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNotFound(final BadRequestException badRequestException) {
        log.warn("400 {}", badRequestException.getMessage());
        badRequestException.printStackTrace(pw);
        return ApiError.builder()
                .errors(Collections.singletonList(sw.toString()))
                .status(HttpStatus.BAD_REQUEST)
                .reason("Incorrectly made request.")
                .message(badRequestException.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerError(final Throwable throwable) {
        log.error("500 {}", throwable.getMessage(), throwable);
        throwable.printStackTrace(pw);
        return ApiError.builder()
                .errors(Collections.singletonList(sw.toString()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .reason("Integrity constraint has been violated.")
                .message(throwable.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }


    private ApiError badRequest(final Exception e) {
        log.error("400 {}", e.getMessage());
        e.printStackTrace(pw);
        return ApiError.builder()
                .errors(Collections.singletonList(sw.toString()))
                .status(HttpStatus.BAD_REQUEST)
                .reason("Incorrectly made request.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    private ApiError conflict(final Exception e) {
        log.error("409 {}", e.getMessage());
        e.printStackTrace(pw);
        return ApiError.builder()
                .errors(Collections.singletonList(sw.toString()))
                .status(HttpStatus.CONFLICT)
                .reason("Integrity constraint has been violated.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
