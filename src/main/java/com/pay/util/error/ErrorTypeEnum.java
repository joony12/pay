package com.pay.util.error;

import lombok.Getter;

@Getter
public enum ErrorTypeEnum {

    ERROR_0001("error_0001", "뿌리기 당 한 사용자는 한번만 받을 수 있습니다."),
    ERROR_0002("error_0002", "같은 방에 속하지 않은 사용자 입니다."),
    ERROR_0003("error_0003", "뿌린지 10분이 지나 받을 수 없습니다."),
    ERROR_0004("error_0004", "자신이 뿌리기한 건은 자신이 받을 수 없습니다."),
    ERROR_0005("error_0005", "뿌린 건에 대한 조회는 7일 동안 할 수 있습니다."),
    ERROR_0006("error_0006", "인증되지 않은 사용자입니다."),
    ERROR_0007("error_0007", "등록되지 않은 사용자입니다."),
    ERROR_0008("error_0008", "등록되지 않은 채팅방입니다."),
    ERROR_0009("error_0009", "뿌린 사람 자신만 조회를 할 수 있습니다."),
    ERROR_0010("error_0010", "유효하지 않은 토큰 정보입니다."),
    ERROR_9999("error_9999", "알 수 없는 에러, 관리자에게 문의하세요.");

    private final String errorCode;

    private final String errorMessage;

    ErrorTypeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}