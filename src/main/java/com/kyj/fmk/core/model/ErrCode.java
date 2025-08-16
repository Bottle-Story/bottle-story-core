package com.kyj.fmk.core.model;


/**
 * 2025-08-09
 * @author 김용준
 * Restful Api에서 사용하는 응답코드를 결정하는 인터페이스
 */
public interface ErrCode {
    String getCode();
    String getMsg();
}
