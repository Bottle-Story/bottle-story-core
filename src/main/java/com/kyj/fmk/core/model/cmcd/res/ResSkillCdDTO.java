package com.kyj.fmk.core.model.cmcd.res;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-10
 * @author 김용준
 * 스킬코드 조회를 위한 응답 dto
 */
@Getter
@Setter
public class ResSkillCdDTO {
    private String skillCd;
    private String skillNm;
    private String skillCdImg;
}
