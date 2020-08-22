package com.pay.vo;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseVO {

    private String errorCode;

    private String errorMessage;
}