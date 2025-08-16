package com.kyj.fmk.core.logging.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 로깅 Aop
 */
@Aspect
@Component
@Slf4j
public class LogAdvisor {

    // Controller 패키지 하위 메서드 실행 시
    @Pointcut("execution(* com.kyj..controller..*(..))")
    public void controllerMethods() {}

    // Service 패키지 하위 메서드 실행 시
    @Pointcut("execution(* com.kyj..service..*(..))")
    public void serviceMethods() {}

    // Repository 패키지 하위 메서드 실행 시
    @Pointcut("execution(* com.kyj..repository..*(..))")
    public void repositoryMethods() {}

    // 공통 어드바이스 정의

    /**
     * 로그를 기록하기 위한 메서드
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("controllerMethods() || serviceMethods() || repositoryMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("[ENTER] {}.{}()", className, methodName);

        Object result = null;

        result = joinPoint.proceed();
        long exTime = System.currentTimeMillis() - start;


        log.info("[EXIT] {}.{}() - return: {}  ( excuteTime {}ms)", className, methodName, result, exTime);

        return result;
    }
}
