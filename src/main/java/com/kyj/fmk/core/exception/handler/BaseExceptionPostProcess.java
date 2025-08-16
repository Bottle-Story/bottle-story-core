package com.kyj.fmk.core.exception.handler;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 익셉션핸들러에 대한 후처리 Aop에서 사용하는 서비스 구현체
 */
@Component
public class BaseExceptionPostProcess implements ExceptionPostProcess{

    @Override
    public void proceed(JoinPoint joinPoint) {
        System.out.println("후처리");
    }
}
