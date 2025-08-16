package com.kyj.fmk.core.async.err;

import java.lang.reflect.Method;

/**
 * 2025-05-29
 * @author 김용준
 * 비동기 Task 에서 발생한 에러를 핸들링 하기위한 인터페이스다.
 */
public interface AsyncHandleErr {
    public void handleAsncErr(Throwable ex, Method method);
}
