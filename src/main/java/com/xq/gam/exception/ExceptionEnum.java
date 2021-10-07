package com.xq.gam.exception;


import org.springframework.http.HttpStatus;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 20:38
 */
public enum ExceptionEnum implements ExceptionSpecification {
    PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "E10002", "parameter error."),

    RESOURCE_NOT_FIND(HttpStatus.BAD_REQUEST, "E10003", "resource not find."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
