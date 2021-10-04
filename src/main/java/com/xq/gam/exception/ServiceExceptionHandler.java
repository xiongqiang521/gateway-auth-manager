package com.xq.gam.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 20:53
 */
@ControllerAdvice
@ResponseBody
public class ServiceExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<ExceptionSpecification> exceptionHandler(ServiceException serviceException) {
        return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionSpecification> exceptionHandler(Exception e) {
        ExceptionEnum error = ExceptionEnum.PARAMETER_ERROR;
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }


}
