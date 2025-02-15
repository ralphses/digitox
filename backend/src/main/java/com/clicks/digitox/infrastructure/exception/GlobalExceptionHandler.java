package com.clicks.digitox.infrastructure.exception;

import com.clicks.digitox.domain.community_post.exceptions.CommunityPostNotFoundException;
import com.clicks.digitox.domain.community_post.exceptions.InvalidMilestoneIdException;
import com.clicks.digitox.domain.sleep_info.exceptions.SleepInfoExceptionNotFound;
import com.clicks.digitox.domain.user.exceptions.UnauthorizedUserException;
import com.clicks.digitox.domain.user.exceptions.UserAlreadyExistException;
import com.clicks.digitox.domain.user.exceptions.UserNotFoundException;
import com.clicks.digitox.shared.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistException.class)
    public ApiResponse handleBadRequest(RuntimeException exception) {
        return new ApiResponse(false, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            UserNotFoundException.class,
            SleepInfoExceptionNotFound.class,
            CommunityPostNotFoundException.class,
            InvalidMilestoneIdException.class
    })
    public ApiResponse handleNotFount(RuntimeException exception) {
        return new ApiResponse(false, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnauthorizedUserException.class)
    public ApiResponse handleUnauthorized(RuntimeException exception) {
        return new ApiResponse(false, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidation(MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage)
                );
        return new ApiResponse(false, errors);
    }
}
