package com.pay;

import com.pay.errorEnum.ErrorCodeEnum;
import com.pay.exception.NotFoundRoomException;
import com.pay.exception.NotFoundUserException;
import com.pay.exception.NotParticipateRoomException;
import com.pay.exception.ReceiveMoneyTimeOutException;
import com.pay.exception.TokenCreateException;
import com.pay.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO unknownException() {
        return getErrorResponse(ErrorCodeEnum.ERROR_9999);
    }

    @ExceptionHandler(value = {UnAuthorizedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO unAuthorizedException() {
        return getErrorResponse(ErrorCodeEnum.ERROR_0001);
    }

    @ExceptionHandler(value = {TokenCreateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO tokenCreateException() {
        return getErrorResponse(ErrorCodeEnum.ERROR_0002);
    }

    @ExceptionHandler(value = {NotFoundUserException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO notFoundUserException() {
        return getErrorResponse(ErrorCodeEnum.ERROR_0003);
    }

    @ExceptionHandler(value = {NotFoundRoomException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO notFoundRoomException() {
        return getErrorResponse(ErrorCodeEnum.ERROR_0004);
    }

    @ExceptionHandler(value = {NotParticipateRoomException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO notParticipateRoomException() {
        return getErrorResponse(ErrorCodeEnum.ERROR_0005);
    }

    @ExceptionHandler(value = {ReceiveMoneyTimeOutException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO receiveMoneyTimeOutException() {
        return getErrorResponse(ErrorCodeEnum.ERROR_0006);
    }

    private ErrorResponseVO getErrorResponse(ErrorCodeEnum errorCodeEnum) {
        return ErrorResponseVO.builder()
                .errorCode(errorCodeEnum.getErrorCode())
                .errorMessage(errorCodeEnum.getErrorMessage())
                .build();
    }
}