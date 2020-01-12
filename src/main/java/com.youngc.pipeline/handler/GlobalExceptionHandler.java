package com.youngc.pipeline.handler;

import com.youngc.pipeline.exception.ParameterInvalidException;
import com.youngc.pipeline.exception.ServiceException;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result commonExceptionHandler(Exception e) {
        log.error(e.getMessage(), e.getCause());
        return ResultGenerator.generate(ResultCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(ParameterInvalidException.class)
    @ResponseBody
    public Result parameterInvalidExceptionHandler(ParameterInvalidException e) {
        return ResultGenerator.generate(e.getResultCode());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceExceptionHandler(ServiceException e) {
        log.error(e.getMessage(), e.getCause());
        return ResultGenerator.generate(e.getResultCode());
    }
}
