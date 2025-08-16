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
     * 스킬코드 조회를 위한 해시키
     */
    public static final String SKILL_CD_KEY="skill_cd:";

    /**
     * 스킬코드 전체조회를 위한 해시키
     */
    public static final String SKILL_CD_KEY_ALL="skill_cd:all";

    /**
     * 직무코드 조회를  위한 해시키
     */
    public static final String DTY_CD_KEY="dty_cd:";


    /**
     * 직무코드 전체조회를  위한 해시키
     */
    public static final String DTY_CD_KEY_ALL="dty_cd:all";
    /**
     * 공통코드(커뮤니케이션톤) 조회를  위한 해시키
     */
    public static final String  CM_CMC_TONE_CD="cm_cmc_tone_cd";

    /**
     * 공통코드(회의스타일) 조회를  위한 해시키
     */
    public static final String  CM_MT_STY_CD="cm_mt_sty_cd";

    /**
     * 공통코드(팀스타일) 조회를  위한 해시키
     */
    public static final String  CM_TEAM_STY_CD="cm_team_sty_cd";


    /**
     * 공통코드(모집상태코드) 조회를  위한 해시키
     */
    public static final String  CM_RECRUIT_ST_CD="cm_recruit_st_cd";

    /**
     * 공통코드(그룹상태코드) 조회를  위한 해시키
     */
    public static final String  CM_GRP_ST_CD="cm_grp_st_cd";

    /**
     * 공통코드(지원상태코드) 조회를  위한 해시키
     */
    public static final String  CM_APY_ST_CD="cm_apy_st_cd";




//-----------------------------------------suffix -----------------------------------------------------------------------------------

    /**
     * 레디스 해시키 조회를 위한 suffix 스킬이름
     */
    public static final  String SUFFIX_SKILL_NM_KEY="skillNm";

    /**
     * 레디스 해시키 조회를 위한 suffix 스킬이름
     */
    public static final  String SUFFIX_SKILL_CD_IMG_KEY="skillCdImg";



    /**
     * 레디스 해시키 조회를 위한 suffix 스킬이름
     */
    public static final  String SUFFIX_DTY_NM_KEY="dtyNm";

    /**
     * 레디스 해시키 조회를 위한 suffix 스킬이름
     */
    public static final  String SUFFIX_DTY_CD_IMG_KEY="dtyCdImg";
}
