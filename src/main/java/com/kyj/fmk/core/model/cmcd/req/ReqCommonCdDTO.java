package com.kyj.fmk.core.model.cmcd.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-10
 * @author 김용준
 * 공통코드 애플리케이션 로드시점 캐시를 위한 dto
 */
@Setter
@Getter
public class ReqCommonCdDTO {
    private String cmCd;
    private String cmCdVal;
    private String cmCdValNm;
}
