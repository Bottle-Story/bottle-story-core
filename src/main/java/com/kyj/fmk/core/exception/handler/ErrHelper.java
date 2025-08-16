package com.kyj.fmk.core.exception.handler;

import com.kyj.fmk.core.exception.custom.*;
import com.kyj.fmk.core.model.ErrCode;
import com.kyj.fmk.core.model.dto.ResApiErrDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * 2025-05-30
 * @author 김용준
 * Restful Api에서 사용하는 에러응답객체에 대한 메시지 혹은 상태값 등을 결정해주는 Helper클래스이다.
 */
@Component
public class ErrHelper {

    private static ErrCodeRegistry errCodeRegistry;


    @Autowired
    public void setErrCodeRegistry(ErrCodeRegistry registry) {
        ErrHelper.errCodeRegistry = registry;
    }
    /**
     * 전체에러응답객체값을 세팅해준다.
     * @param ex
     * @return
     */
    public static ResApiErrDTO<Void> determineErrRes(Exception ex){
        String msg = determineErrMsg(ex);
        HttpStatus status = determineErrStatus(ex);
        String apiErrCode = determineErrCode(ex);
        return new ResApiErrDTO<>(msg, status.value(), apiErrCode);
    }


    /**
     * 에러메시지를 결정해준다
     * @param ex
     * @return String
     */
    public static String determineErrMsg(Exception ex) {
        if (ex instanceof KyjBaseException kyjEx) {
            String code = kyjEx.getCode();

            if (code.equals(CmErrCode.CM001.getCode())) {
                return kyjEx.getMsg();
            } else {
                return errCodeRegistry.get(code)
                        .map(ErrCode::getMsg)
                        .orElse(CmErrCode.CM014.getMsg());
            }
        }
        return "";
    }

    public static String determineErrCode(Exception ex){

        if(ex instanceof KyjBaseException){
            ex = (KyjBaseException)ex;
            return  ((KyjBaseException) ex).getCode();

        }else{
            return CmErrCode.CM001.getCode();
        }
    }


    /**
     * 에러스테이터스를 결정해준다.
     * @param ex
     * @return HttpStatus
     */
    private static HttpStatus determineErrStatus(Exception ex  ){

        if(ex instanceof KyjBaseException){
            if(ex instanceof KyjBizException){
                return HttpStatus.BAD_REQUEST;
            } else if (ex instanceof KyjSysException) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            } else if (ex instanceof KyjAccessException) {
                return HttpStatus.FORBIDDEN;
            } else if (ex instanceof KyjAuthException) {
                return HttpStatus.UNAUTHORIZED;
            } else{
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
