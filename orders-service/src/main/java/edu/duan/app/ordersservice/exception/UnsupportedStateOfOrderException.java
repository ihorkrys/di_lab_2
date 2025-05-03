package edu.duan.app.ordersservice.exception;

public class UnsupportedStateOfOrderException extends RuntimeException {
    public UnsupportedStateOfOrderException(String message) {
        super(message);
    }
}
