package com.pay.config;

import lombok.Getter;

@Getter
public enum CacheType {

    SPREAD_MONEY_LIST("spreadMoneyList", 20,1000000);

    private String cacheName;
    private int expiredSecondAfterWrite;
    private int maximumSize;

    CacheType(String cacheName, int expiredSecondAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredSecondAfterWrite = expiredSecondAfterWrite;
        this.maximumSize = maximumSize;
    }
}