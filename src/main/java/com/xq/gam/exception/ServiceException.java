package com.xq.gam.exception;

import org.springframework.http.HttpStatus;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * 2021/10/4 20:50
 */
public class ServiceException extends RuntimeException implements ExceptionSpecification{
    private static final long serialVersionUID = 7453222428056339259L;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ServiceException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.httpStatus = exceptionEnum.getHttpStatus();
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public ServiceException(ExceptionSpecification exceptionSpecification) {
        super(exceptionSpecification.getMessage());
        this.httpStatus = exceptionSpecification.getHttpStatus();
        this.code = exceptionSpecification.getCode();
        this.message = exceptionSpecification.getMessage();
    }

    public ServiceException(ExceptionSpecification exceptionSpecification, String expandMessage) {
        super(exceptionSpecification.getMessage() + expandMessage);
        this.httpStatus = exceptionSpecification.getHttpStatus();
        this.code = exceptionSpecification.getCode();
        this.message = exceptionSpecification.getMessage() + expandMessage;
    }

    public ServiceException(HttpStatus httpStatus, String code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
