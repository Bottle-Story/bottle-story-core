package com.kyj.fmk.core.exception.custom;

import com.kyj.fmk.core.model.ErrCode;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 비즈니스 Exception
 */
public class KyjAccessException extends KyjBaseException{
    /**
     *
     * @param apiErrCode
     */
    public KyjAccessException(ErrCode apiErrCode){
        super(apiErrCode);
        this.setCode(apiErrCode.getCode());
    }

    /**
     *
     * @param errCode
     * @param msg
     */
    public KyjAccessException(ErrCode errCode, String msg){
        super(errCode,msg);
        this.setMsg(msg);
        this.setCode(errCode.getCode());
    }

}
