package com.kyj.fmk.core.model.wheather;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ResSunRiseSetApiDTO {


    private LocalTime sunRiseTime;
    private LocalTime sunSetTime;

}
