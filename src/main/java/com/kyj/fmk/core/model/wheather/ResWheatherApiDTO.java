package com.kyj.fmk.core.model.wheather;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * 2025-08-25
 * @author 김용준
 * 기상청 날씨정보를 가져오기 위한 Dto
 */
@Getter
@Setter
public class ResWheatherApiDTO {

    private LocalTime wthrBaseTime; //발표시간
    private LocalDate wthrBaseDate; //발표연월일
    private LocalTime wthrTime; //기준 시간
    private LocalDate wthrDate; //기준연월일
    private WhtrData wthData; //날씨데이터
    private LocalDateTime regDateTime; //등록연월일 시간
}
