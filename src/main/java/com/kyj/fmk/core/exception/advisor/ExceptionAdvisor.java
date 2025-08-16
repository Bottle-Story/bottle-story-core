package com.kyj.fmk.core.exception.advisor;

import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.exception.handler.ExceptionPostProcess;
import com.kyj.fmk.core.model.enm.CmErrCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 익셉션핸들러에 대한 후처리 aop
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionAdvisor {

    private final ExceptionPostProcess exceptionPostProcess;

    @AfterReturning("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void exceptionAfterReturning(JoinPoint joinPoint) throws Throwable {

        Exception  ex;
        try {
            // 메서드 인자
            Object[] args = joinPoint.getArgs();
            Object arg = args[0];
            ex = (Exception) arg;

        }
        catch (Exception e){
            ex = new KyjSysException(CmErrCode.CM002);
        }

        ex.printStackTrace();

        exceptionPostProcess.proceed(joinPoint);
        
    }
}
