package com.kyj.fmk.core.exception.handler;

import org.aspectj.lang.JoinPoint;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 익셉션핸들러에 대한 후처리 Aop에서 사용하는 서비스
 */
public interface ExceptionPostProcess {

    public void proceed(JoinPoint joinPoint);
}
