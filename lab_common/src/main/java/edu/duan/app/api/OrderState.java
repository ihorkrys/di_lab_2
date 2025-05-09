package edu.duan.app.api;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderState {
    NEW,
    PROCESSING,
    FULFILLED,
    CANCELLED,
    REFUNDED,
    COMPLETED,
    DECLINED
}
