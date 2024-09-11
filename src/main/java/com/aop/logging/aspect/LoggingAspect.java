package com.aop.logging.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("local")
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.aop.logging..*(..))")
    public void allMethodsInLoggingPackages() {}

//    @Before("execution(* com.example..*(..))")
    @Before("allMethodsInLoggingPackages()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className=joinPoint.getTarget().getClass().toString();
        Object[] methodArgs = joinPoint.getArgs();
        logger.info("@Before Entering {}.{} with arguments: {}",className, methodName, methodArgs);
    }

    @AfterReturning(pointcut = "allMethodsInLoggingPackages()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("@AfterReturning Method: {} executed successfully with result: {}", methodName, result);
    }

//    @AfterThrowing(pointcut = "execution(* com.example..*(..))", throwing = "exception")
    @AfterThrowing(pointcut = "allMethodsInLoggingPackages()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("@AfterThrowing Method: {} threw an exception: {}", methodName, exception.getMessage(), exception);
    }

    @Around("allMethodsInLoggingPackages()")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        logger.info("@Around Starting execution of method: {}", methodName);

        Object result;
        try {
            result = joinPoint.proceed(); // Continue with method execution
        } catch (Throwable e) {
            logger.error("@Around caught Exception in method: {}", methodName, e);
            throw e;
        }

        long executionTime = System.currentTimeMillis() - startTime;
        logger.info("@Around calculated Method: {} executed in {} ms", methodName, executionTime);
        return result;
    }
}
