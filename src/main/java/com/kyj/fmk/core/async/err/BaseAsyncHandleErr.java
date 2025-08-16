package com.kyj.fmk.core.async.err;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 2025-05-29
 * @author 김용준
 * 비동기 Task 에서 발생한 에러를 핸들링 하기위한 기본 핸들러이다.
 */
@Component
public class BaseAsyncHandleErr implements AsyncHandleErr{
    @Override
    public void handleAsncErr(Throwable ex, Method method) {
        System.out.println("ex = " + ex);
        System.out.println("method.getName() = " + method.getName());
    }
}
