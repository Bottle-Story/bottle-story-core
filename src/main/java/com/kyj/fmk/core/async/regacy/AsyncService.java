package com.kyj.fmk.core.async.regacy;

import java.util.concurrent.CompletableFuture;
/**
 *  * 2025-05-29
 *  * @author 김용준
 *  * 비동기 Task를 수행하는 서비스 인터페이스다.
 *  */
public interface AsyncService {

    public CompletableFuture<Object> invokeAsync(Object target, String methodName, Object... args);
}
