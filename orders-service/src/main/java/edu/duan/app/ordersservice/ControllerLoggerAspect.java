package edu.duan.app.ordersservice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ControllerLoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggerAspect.class);

    @Before("execution(* edu.duan.app.ordersservice.controller.*Controller.*(..))")
    public void logRequest(JoinPoint joinPoint) {
        logger.info("Request {} Input {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* edu.duan.app.ordersservice.controller.*Controller.*(..))", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        logger.info("Response {} body {}", joinPoint.getSignature(), result);
    }
}