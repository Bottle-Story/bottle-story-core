package com.kyj.fmk.core.async.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *  * 2025-05-29
 *  * @author 김용준
 *  * 비동기 Task를 수행할수 있게하는 AOP 어노테이션이다.
 *  */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsyncVoidInvoke {
}
