package edu.duan.app.ordersservice.controller;

import edu.duan.app.api.UnCheckedExceptionType;
import edu.duan.app.ordersservice.service.ExceptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unchecked_exceptions")
public class UnCheckedExceptionController {
    private ExceptionService exceptionService;

    public UnCheckedExceptionController(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @GetMapping(path = "/{exception_type}")
    public void getChecked(
            @PathVariable("exception_type") UnCheckedExceptionType exceptionType) {
        exceptionService.getUnCheckedException(exceptionType);
    }
}
