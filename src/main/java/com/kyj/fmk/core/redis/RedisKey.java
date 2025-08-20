package com.kyj.fmk.core.redis;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 레디스키 상수모음
 */
public class RedisKey {
    /**
     * 서비스명:키:키:
     */


    /**
     * ouath2인증 후 추가정보입력(회원가입)에 대한 보안을 높히기 위한 키
     */
    public static final String MEMBER_ADDITIONL_INFO="member:joinJwt:";


    /**
     * 공통코드 조회를  위한 해시키
     */
    public static final String  CM_BTL_STATUS_CODE="CM_BTL_STATUS_CODE";

    public static final String  CM_EVENT_STATUS_CODE="CM_EVENT_STATUS_CODE";

    public static final String  CM_OCEAN_CODE="CM_OCEAN_CODE";

    public static final String  CM_PARTICLE_CODE="CM_PARTICLE_CODE";

    public static final String  CM_SKY_CODE="CM_SKY_CODE";

    public static final String  CM_TIME_CODE="CM_TIME_CODE";




//-----------------------------------------suffix -----------------------------------------------------------------------------------


}
