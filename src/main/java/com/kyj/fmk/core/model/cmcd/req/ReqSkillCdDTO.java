package com.kyj.fmk.core.model.cmcd.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-10
 * @author 김용준
 * 스킬코드 애플리케이션 로드시점 캐시를 위한 dto
 */
@Getter
@Setter
public class ReqSkillCdDTO {
    private String skillCd;
    private String skillNm;
    private String dtyCd;
    private String skillCdImg;
}
