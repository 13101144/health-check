package com.github.health.check.exception;

import com.github.health.check.domain.entity.ArgumentInvalidResult;
import com.github.health.check.enums.ErrorCode;
import com.github.health.check.util.R;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public R methodArgumentNotValidHandler(MethodArgumentNotValidException exception) throws Exception {
        //按需重新封装需要返回的错误信息
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }
        return R.failed(invalidArguments, ErrorCode.CLIENT_INVALID_PARAM.getCode(), ErrorCode.CLIENT_INVALID_PARAM.getMsg());
    }

    @ExceptionHandler(value= BusinessException.class)
    public R businessExceptionHandler(BusinessException exception) throws Exception {
        return R.failed(exception.getErrCode(), exception.getErrMsg());
    }

    @ExceptionHandler(value= TokenException.class)
    public R tokenExceptionHandler(BusinessException exception) throws Exception {
        return R.failed(exception.getErrCode(), exception.getErrMsg());
    }

    @ExceptionHandler(value= Exception.class)
    public R exceptionHandler(Exception exception) throws Exception {
        return R.failed(exception.getMessage(),ErrorCode.SERVER_ERROR.getCode(), ErrorCode.SERVER_ERROR.getMsg());
    }
}
