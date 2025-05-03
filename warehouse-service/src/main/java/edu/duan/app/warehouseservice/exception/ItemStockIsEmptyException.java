package edu.duan.app.warehouseservice.exception;

public class ItemStockIsEmptyException extends RuntimeException {
    public ItemStockIsEmptyException(String message) {
        super(message);
    }
}
