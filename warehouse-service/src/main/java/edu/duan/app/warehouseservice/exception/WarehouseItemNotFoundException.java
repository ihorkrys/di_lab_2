package edu.duan.app.warehouseservice.exception;

public class WarehouseItemNotFoundException extends RuntimeException {
    public WarehouseItemNotFoundException(String message) {
        super(message);
    }
}
