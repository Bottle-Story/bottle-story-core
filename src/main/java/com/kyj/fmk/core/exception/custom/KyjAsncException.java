package com.kyj.fmk.core.exception.custom;

import com.kyj.fmk.core.model.ErrCode;
import com.kyj.fmk.core.model.enm.CmErrCode;
/**
 * 2025-05-29
 * @author 김용준
 * 비동기 Task에서 발생한 Exception
 */
public class KyjAsncException extends RuntimeException{
    public KyjAsncException(ErrCode apiErrCode) {
        super(apiErrCode.getCode());
        // 상수 에러코드
    }
}
