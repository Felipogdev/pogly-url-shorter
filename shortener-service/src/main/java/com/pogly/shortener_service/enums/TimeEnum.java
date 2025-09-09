package com.pogly.shortener_service.enums;

public enum TimeEnum {
    FIVE_YEARS(157784630L);

    private Long time;

    TimeEnum(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return this.time;
    }
}
