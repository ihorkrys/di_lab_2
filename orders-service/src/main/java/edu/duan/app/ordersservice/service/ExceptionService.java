package edu.duan.app.ordersservice.service;

import edu.duan.app.api.CheckedExceptionType;
import edu.duan.app.api.UnCheckedExceptionType;
import jakarta.el.MethodNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@Service
public class ExceptionService {
    public void getCheckedException(CheckedExceptionType exceptionType) throws Exception {
        String message = getMessage(exceptionType.getStatusCode(), exceptionType.getResponseCode());
        switch (exceptionType) {
            case IO_EXCEPTION -> throw new IOException(message);
            case SQL_EXCEPTION -> throw new SQLException(message);
            case PARSE_EXCEPTION -> throw new ParseException(message, -1);
            case CLASS_NOT_FOUND_EXCEPTION -> throw new ClassNotFoundException(message);
            case METHOD_NOT_FOUND_EXCEPTION -> throw new MethodNotFoundException(message);
            default -> throw new Exception(message);
        }
    }

    public void getUnCheckedException(UnCheckedExceptionType exceptionType) {
        String message = getMessage(exceptionType.getStatusCode(), exceptionType.getResponseCode());
        switch (exceptionType) {
            case NPE_EXCEPTION -> throw new NullPointerException(message);
            case ARITHMETIC_EXCEPTION -> throw new ArithmeticException(message);
            case ARRAY_INDEX_EXCEPTION -> throw new ArrayIndexOutOfBoundsException(message);
            case CLASS_CAST_EXCEPTION -> throw new ClassCastException(message);
            case ILLEGAL_ARGUMENT_EXCEPTION -> throw new IllegalArgumentException(message);
            default -> throw new RuntimeException(message);
        }
    }

    private String getMessage(int code, String message) {
        return code + ": " + message;
    }
}
