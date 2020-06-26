package com.pay.errorEnum;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    ERROR_0001("0001", "사용자 식별자 및 대화방의 식별자 정보가 존재하지 않습니다."),
    ERROR_0002("0002", "토큰 생성에 실패했습니다."),
    ERROR_0003("0003", "사용자 정보가 존재하지 않습니다."),
    ERROR_0004("0004", "대화방 정보가 존재하지 않습니다."),
    ERROR_0005("0005", "참여하지 않은 대화방입니다."),
    ERROR_0006("0006", "10분이 지나 받기를 할 수 없습니다."),
    ERROR_9999("9999", "알 수 없는 에러, 관리자에게 문의하세요.");

    private String errorCode;

    private String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}