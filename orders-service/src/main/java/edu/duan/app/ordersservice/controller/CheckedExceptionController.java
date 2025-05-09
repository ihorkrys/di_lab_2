package edu.duan.app.ordersservice.controller;

import edu.duan.app.api.CheckedExceptionType;
import edu.duan.app.ordersservice.service.ExceptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptions")
public class CheckedExceptionController {
    private ExceptionService exceptionService;

    public CheckedExceptionController(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @GetMapping(path = "/{exception_type}")
    public void getChecked(
            @PathVariable("exception_type") CheckedExceptionType exceptionType) throws Exception {
        exceptionService.getCheckedException(exceptionType);
    }
}
