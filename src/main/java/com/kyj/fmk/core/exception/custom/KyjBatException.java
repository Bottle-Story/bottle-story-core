package com.kyj.fmk.core.exception.custom;

import com.kyj.fmk.core.model.ErrCode;
import com.kyj.fmk.core.model.enm.CmErrCode;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 배치 Exception
 */
public class KyjBatException extends KyjBaseException{
    /**
     *
     * @param apiErrCode
     */
    public KyjBatException(ErrCode apiErrCode){
        super(apiErrCode);
        this.setCode(apiErrCode.getCode());

    }

    /**
     *
     * @param errCode
     * @param msg
     */
    public KyjBatException(ErrCode errCode, String msg){
        super(errCode,msg);
        this.setMsg(msg);
        this.setCode(errCode.getCode());
    }
}
