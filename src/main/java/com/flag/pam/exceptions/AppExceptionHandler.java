package com.flag.pam.exceptions;

import com.flag.pam.common.AppResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rameshalagumalai
 * @Date 26/01/2025
 * */

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private AppResponse handleException(NoResourceFoundException exception)  {
        return new AppResponse(AppResponseCodes.URL_NOT_FOUND, "The requested resource doesn't exist");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    private AppResponse handleException(HttpRequestMethodNotSupportedException exception)  {
        return new AppResponse(AppResponseCodes.HTTP_METHOD_NOT_CONFIGURED, "The http method is not configured for the requested url");
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    private AppResponse handleException(AuthException exception)  {
        return new AppResponse(AppResponseCodes.UNAUTHENTICATED, "Authentication failed");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private AppResponse handleException(HttpMessageNotReadableException exception)  {
        return new AppResponse(AppResponseCodes.INVALID_DATA, "Invalid data provided");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private AppResponse handleException(MethodArgumentNotValidException exception)  {
        List<FieldError> fieldErrorList = exception.getFieldErrors();
        List<String> errorList = new ArrayList<>();
        for(FieldError fieldError : fieldErrorList) {
            errorList.add(fieldError.getDefaultMessage());
        }
        return new AppResponse(AppResponseCodes.INVALID_DATA, errorList);
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private AppResponse handleException(UserException exception) {
        return new AppResponse(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    private AppResponse handleException(Exception exception)  {
        System.out.println(exception);
        return new AppResponse(AppResponseCodes.INTERNAL_ERROR, "Internal error");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private AppResponse handleException(EntityNotFoundException exception)  {
        System.out.println(exception);
        return new AppResponse(AppResponseCodes.ENTITY_NOT_FOUND, exception.getMessage());
    }

}
