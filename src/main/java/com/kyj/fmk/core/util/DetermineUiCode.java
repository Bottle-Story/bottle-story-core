package com.kyj.fmk.core.util;

import com.kyj.fmk.core.model.TimeConst;
import com.kyj.fmk.core.model.wheather.ResSunRiseSetApiDTO;
import com.kyj.fmk.core.model.wheather.WhtrData;
import com.kyj.fmk.core.model.wheather.WhtrUiCode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * 2025-08-27
 * @author 김용준
 * 현재 UI컴포넌트의 공통코드 산출
 */
public class DetermineUiCode {

    /**
     * UI컴포넌트 결정
     * @param whtrData
     * @param resSunRiseSetApiDTO
     * @return
     */
    public static WhtrUiCode determineUiCode(WhtrData whtrData, ResSunRiseSetApiDTO resSunRiseSetApiDTO){
        String timeCode = DetermineTimeCode.determineTimeCode(resSunRiseSetApiDTO);

        WhtrUiCode whtrUiCode = new WhtrUiCode();

        String oceanCode = "NORMAL_OCEAN";
        String skyCode = "DAY_CLEAR";
        String particleCode = "PARTICLE_NONE";

        switch (timeCode){
            case TimeConst.PRE_DAWN:
                oceanCode = "DAWN_OCEAN";//바다코드
                    switch (whtrData.getSky()){
                        //하늘코드
                        case 1: skyCode = "DAWN_MOON_CLEAR";
                            break; //맑음
                        case 3: skyCode = "DAWN_MOON_CLOUDY";
                            break; //구름많음
                        case 4: skyCode = "DAWN_MOON_OVERCAST";
                            break; //흐림
                    }
                break;
            case TimeConst.SUN_RISE:
                oceanCode = "SUN_RISE_SET_OCEAN";
                    switch (whtrData.getSky()){
                        //하늘코드
                        case 1: skyCode = "SUNRISE_PROGRESS_CLEAR";
                            break; //맑음
                        case 3: skyCode = "SUNRISE_PROGRESS_CLOUDY";
                            break; //구름많음
                        case 4: skyCode = "SUNRISE_PROGRESS_OVERCAST";
                            break; //흐림
                    }
                break;
            case TimeConst.DAY_TIME:
                oceanCode = "DAY_OCEAN";
                    switch (whtrData.getSky()){
                        //하늘코드
                        case 1: skyCode = "DAY_CLEAR";
                            break; //맑음
                        case 3: skyCode = "DAY_CLOUDY";
                            break; //구름많음
                        case 4: skyCode = "DAY_OVERCAST";
                            break; //흐림
                    }
                break;
            case TimeConst.SUN_SET:
                oceanCode = "SUN_RISE_SET_OCEAN";
                    switch (whtrData.getSky()){
                        //하늘코드
                        case 1: skyCode = "SUNSET_CLEAR";
                            break; //맑음
                        case 3: skyCode = "SUNSET_CLOUDY";
                            break; //구름많음
                        case 4: skyCode = "SUNSET_OVERCAST";
                            break; //흐림
                    }
                break;
            case TimeConst.NIGHT:
                oceanCode = "NIGHT_OCEAN";
                    switch (whtrData.getSky()){
                        //하늘코드
                        case 1: skyCode = "NIGHT_MOON_CLEAR";
                            break; //맑음
                        case 3: skyCode = "NIGHT_MOON_CLOUDY";
                            break; //구름많음
                        case 4: skyCode = "NIGHT_MOON_OVERCAST";
                            break; //흐림
                    }
                break;
        }



        switch (whtrData.getPty()){
            //하늘코드
            case 0: particleCode = "PARTICLE_NONE";
                break; //없음
            case 1: particleCode = "PARTICLE_RAINY";
                break; //비
            case 2: particleCode = "PARTICLE_RAINY_SNOW";
                break; //비/눈
            case 3: particleCode = "PARTICLE_SNOW";
                break; //눈
            case 5: particleCode = "PARTICLE_RAIN_DROP";
                break; //빗방울
            case 6: particleCode = "PARTICLE_RAIN_DROP_SNOW_STORM";
                break; //빗방울+눈날림
            case 7: particleCode = "PARTICLE_SNOW_STORM";
                break; //눈날림
        }

        whtrUiCode.setOceanCode(oceanCode);
        whtrUiCode.setSkyCode(skyCode);
        whtrUiCode.setParticleCode(particleCode);


        return whtrUiCode;

    }
}
