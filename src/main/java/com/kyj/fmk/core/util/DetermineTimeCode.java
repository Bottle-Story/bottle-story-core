package com.kyj.fmk.core.util;

import com.kyj.fmk.core.model.TimeConst;
import com.kyj.fmk.core.model.wheather.ResSunRiseSetApiDTO;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * 2025-08-27
 * @author 김용준
 * 시간에 대한 코드값명 산출
 */
public class DetermineTimeCode {

    public static String determineTimeCode(ResSunRiseSetApiDTO resSunRiseSetApiDTO) {

        LocalTime sunrise = resSunRiseSetApiDTO.getSunRiseTime();
        LocalTime sunset  = resSunRiseSetApiDTO.getSunSetTime();
        LocalTime nowTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toLocalTime();

        LocalTime sunriseEnd = sunrise.plusMinutes(30); // 일출 직전/직후 30분
        LocalTime dayStart = sunriseEnd;
        LocalTime dayEnd = sunset.minusMinutes(30);    // 낮 구간 끝
        LocalTime sunsetStart = dayEnd;                 // 일몰 직전 30분 시작

        if (nowTime.isBefore(sunrise)) {
            return TimeConst.PRE_DAWN; // 새벽
        } else if (!nowTime.isBefore(sunrise) && nowTime.isBefore(sunriseEnd)) {
            return TimeConst.SUN_RISE;  // 일출 직전/직후
        } else if (!nowTime.isBefore(dayStart) && nowTime.isBefore(dayEnd)) {
            return TimeConst.DAY_TIME;  // 낮
        } else if (!nowTime.isBefore(sunsetStart) && nowTime.isBefore(sunset)) {
            return TimeConst.SUN_SET;   // 일몰 직전/직후
        } else {
            return TimeConst.NIGHT;    // 밤
        }
    }
}
