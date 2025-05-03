package edu.duan.app.warehouseservice.exception;

public class UnsupportedStateOfOrderException extends RuntimeException {
    public UnsupportedStateOfOrderException(String message) {
        super(message);
    }
}
