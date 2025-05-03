package edu.duan.app.warehouseservice.exception;

public class ItemStockNotFoundException extends RuntimeException {
    public ItemStockNotFoundException(String message) {
        super(message);
    }
}
