package edu.duan.app.ordersservice.exception;

import edu.duan.app.api.AppErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//https://stackoverflow.com/questions/79274106/how-to-use-both-restcontrolleradvice-and-swagger-ui-in-spring-boothttps://stackoverflow.com/questions/79274106/how-to-use-both-restcontrolleradvice-and-swagger-ui-in-spring-boot
@Hidden
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody AppErrorResponse handleOrderNotFoundException(OrderNotFoundException e) {
        return new AppErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(UnsupportedStateOfOrderException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody AppErrorResponse handleUnsupportedStateOfOrderException(UnsupportedStateOfOrderException e) {
        return new AppErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), e.getMessage());
    }


    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody AppErrorResponse handleItemNotFoundException(ItemNotFoundException e) {
        return new AppErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    //lab 4 errors
    /*
            case IO_EXCEPTION -> throw new IOException(responseCode);
            case SQL_EXCEPTION -> throw new SQLException(responseCode);
            case PARSE_EXCEPTION -> throw new ParseException(responseCode, -1);
            case CLASS_NOT_FOUND_EXCEPTION -> throw new ClassNotFoundException(responseCode);
            case METHOD_NOT_FOUND_EXCEPTION -> throw new MethodNotFoundException(responseCode);
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody AppErrorResponse handleItemNotFoundException(Exception e) {
        return new AppErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /*
            case NPE_EXCEPTION -> throw new NullPointerException(responseCode);
            case ARITHMETIC_EXCEPTION -> throw new ArithmeticException(responseCode);
            case ARRAY_INDEX_EXCEPTION -> throw new ArrayIndexOutOfBoundsException(responseCode);
            case CLASS_CAST_EXCEPTION -> throw new ClassCastException(responseCode);
            case ILLEGAL_ARGUMENT_EXCEPTION -> throw new IllegalArgumentException(responseCode);
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody AppErrorResponse handleItemNotFoundException(RuntimeException e) {
        return new AppErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
