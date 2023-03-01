package com.moma.momaadmin.common.exception;

import com.moma.momaadmin.util.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public RestResult handler(RuntimeException e) {
        log.error("运行时异常:" + e.getMessage());
        return RestResult.error(e.getMessage());

    }
}
