package com.aop.logging.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Aspect
@Component
@Profile("dev") // Ensure this aspect is active only in the 'dev' profile
public class RestControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public Object logAroundRestControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Entering method: {} with arguments {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());

        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Exception in method: {} with message {}", joinPoint.getSignature().toShortString(), throwable.getMessage());
            throw throwable;
        }

        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Exiting method: {} after {} ms", joinPoint.getSignature().toShortString(), timeTaken);

        return result;
    }
    
    @AfterThrowing(pointcut = "restControllerMethods()", throwing = "exception")
    public void logExceptionInRestControllerMethods(Throwable exception) {
        logger.error("Exception caught in @RestController method: {}", exception.getMessage());
    }
}
