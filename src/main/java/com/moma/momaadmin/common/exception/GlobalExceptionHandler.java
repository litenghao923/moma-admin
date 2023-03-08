package com.moma.momaadmin.common.exception;

import com.moma.momaadmin.util.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public RestResult handler(RuntimeException e) {
        if (e instanceof AccessDeniedException){
            return RestResult.error(403,"权限不足");
        }
        return RestResult.error(e.getMessage());

    }
}
