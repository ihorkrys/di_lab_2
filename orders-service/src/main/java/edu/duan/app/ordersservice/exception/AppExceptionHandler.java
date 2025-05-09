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
}
