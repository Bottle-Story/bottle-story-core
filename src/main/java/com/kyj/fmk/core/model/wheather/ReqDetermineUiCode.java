package com.kyj.fmk.core.model.wheather;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ReqDetermineUiCode {

    private LocalTime wthrTime;
    private LocalTime sunRiseTime;
    private LocalTime sunSetTime;
    private WhtrData whtrData;
}
