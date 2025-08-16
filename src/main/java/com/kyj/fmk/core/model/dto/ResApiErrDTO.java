package com.kyj.fmk.core.model.dto;

import com.kyj.fmk.core.model.enm.CmErrCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 실패응답 wrapper 클래스
 */
@Getter
public class ResApiErrDTO <T> extends BaseResDTO{
    /**
     * 메시지와 스테이터스를 인자로받는 생성자
     * @param msg
     * @param status
     */
    public ResApiErrDTO(String msg, int status, String apiErrCode){
        this.setMsg(msg);
        this.setStatus(status);
        this.setCode(apiErrCode);
    }

    /**
     * 메시지와 데이터를 인자로 받는 생성자
     * @param msg
     * @param data
     */
    public ResApiErrDTO(String msg, T data){
        this.setDefaultVal();
        this.setMsg(msg);
        this.setData(data);
    }

    /**
     * 메시지,스테이터스,데이터를 인자로 받는 생성자
     * @param msg
     * @param status
     * @param data
     */
    public ResApiErrDTO(String msg, int status, T data, String apiErrCode){
        this.setMsg(msg);
        this.setData(data);
        this.setStatus(status);
        this.setCode(apiErrCode);
    }

    /**
     * 에러응답객체에 대한 기본값을 세팅한다.
     */
    private void setDefaultVal(){
        this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        this.setCode(CmErrCode.CM002.getCode());
    }

}
