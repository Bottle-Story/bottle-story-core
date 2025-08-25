package com.kyj.fmk.core.model.wheather;

import lombok.Getter;
import lombok.Setter;


/**
 * 2025-08-25
 * @author 김용준
 * 기상청 날씨정보를 가져오기 위한 Dto
 */
@Getter
@Setter
public class ReqWheatherApiDTO {


    private  Double lat;
    private  Double lot;
}
