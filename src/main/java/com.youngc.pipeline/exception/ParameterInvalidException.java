package com.youngc.pipeline.exception;


import com.youngc.pipeline.result.ResultCode;

public class ParameterInvalidException extends ServiceException {

    public ParameterInvalidException() {
        super(ResultCode.PARA_ERROR, null);
    }

    public ParameterInvalidException(Throwable t) {
        super(ResultCode.PARA_ERROR, t);
    }

}
