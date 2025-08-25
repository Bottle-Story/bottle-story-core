package com.kyj.fmk.core.model.enm;

import com.kyj.fmk.core.model.ErrCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 에러응답 공통 Enum
 */
@Getter
public enum CmErrCode implements ErrCode {

    CM001("CM001","기본응답메시지 {0}"),
    CM002("CM002","서버내부 오류입니다."),
    CM003("CM003","쿠키추출 오류입니다."),
    CM004("CM004","UTIL 클래스 오류입니다."),
    CM005("CM005","메일전송중 오류가 발생했습니다."),
    CM006("CM006","비동기 서비스수행중 오류가 발생했습니다."),
    CM007("CM007","지원되지 않는 파일확장자입니다."),
    CM008("CM008","업로드 최대용량을 초과하였습니다."),
    CM009("CM009","파일저장중 알수없는 오류가 발생하였습니다."),
    CM010("CM010","파일삭제중 알수없는 오류가 발생하였습니다."),
    CM011("CM011","파일다운로드중 알수없는 오류가 발생하였습니다."),
    CM012("CM012","분산락획득에 실패하였습니다."),
    CM013("CM013","MethodNotArgumentEx"),
    CM014("CM014","응답메시지가 존재하지 않습니다."),
    CM015("CM015","조회할 내용이 없습니다."),
    CM016("CM016","JSON 직렬/역직렬화에서 오류가 발생하였습니다."),
    CM017("CM017","기상청 API호출에 실패 하였습니다."),



    SEC002("SEC002","잘못된 토큰형식입니다."),
    SEC007("SEC007","세션이 만료되었습니다."),
    SEC010("SEC010","인증되지 않은 요청입니다."),
    SEC011("SEC011","허가되지 않은 요청입니다.");




    private final String code;
    private final String msg;

    /**
     * 에러코드 생성자
     * @param code
     * @param msg
     */
    CmErrCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }




}
