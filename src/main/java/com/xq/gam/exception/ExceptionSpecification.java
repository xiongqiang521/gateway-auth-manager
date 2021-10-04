package com.xq.gam.exception;

import org.springframework.http.HttpStatus;

/**
 * Description: 自定义异常接口规范
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 20:46
 */
public interface ExceptionSpecification {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
