package com.pay.exception;

import com.pay.util.error.ErrorTypeEnum;
import com.pay.vo.ErrorResponseVO;
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
    public ErrorResponseVO unknownException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_9999);
    }

    @ExceptionHandler(value = {AlreadyReceivedMoneyException.class})
    public ErrorResponseVO alreadyReceivedMoneyException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0001);
    }

    @ExceptionHandler(value = {NotParticipateRoomException.class})
    public ErrorResponseVO notParticipateRoomException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0002);
    }

    @ExceptionHandler(value = {ReceiveMoneyTimeOutException.class})
    public ErrorResponseVO receiveMoneyTimeOutException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0003);
    }

    @ExceptionHandler(value = {SelfReceivedMoneyException.class})
    public ErrorResponseVO selfReceivedMoneyException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0004);
    }

    @ExceptionHandler(value = {SpreadHistoryTimeOutException.class})
    public ErrorResponseVO spreadHistoryTimeOutException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0005);
    }

    @ExceptionHandler(value = {UnAuthorizedException.class})
    public ErrorResponseVO unAuthorizedException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0006);
    }

    @ExceptionHandler(value = {UnRegisteredRoomException.class})
    public ErrorResponseVO unRegisteredRoomException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0007);
    }

    @ExceptionHandler(value = {UnRegisteredUserException.class})
    public ErrorResponseVO unRegisteredUserException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0008);
    }

    @ExceptionHandler(value = {SpreadHistorySearchException.class})
    public ErrorResponseVO spreadHistorySearchException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0009);
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ErrorResponseVO invalidTokenException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0010);
    }

    private ErrorResponseVO getErrorResponse(ErrorTypeEnum errorTypeEnum) {
        return ErrorResponseVO.builder()
                .errorCode(errorTypeEnum.getErrorCode())
                .errorMessage(errorTypeEnum.getErrorMessage())
                .build();
    }
}