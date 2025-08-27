package com.kyj.fmk.core.util;
/**
 * 2025-08-27
 * @author 김용준
 * 날씨에 대한 코드값명 산출
 */
public class DetermineWhtr {

    public static String determineSkyNm(String skyCode){

        int sky = Integer.parseInt(skyCode);

        String skyNm = switch (sky) {
            case 1 -> "맑음";
            case 3 -> "구름많음";
            case 4 -> "흐림";
            default -> "없음";
        };

        return  skyNm;
    }

    public static String determinePtyNm(String ptyCode){
        int pty = Integer.parseInt(ptyCode);

        String ptyNm = switch (pty) {
            case 0 -> "없음";
            case 1 -> "비";
            case 2 -> "비/눈";
            case 3 -> "눈";
            case 5 -> "빗방울";
            case 6 -> "빗방울+눈날림";
            case 7 -> "눈날림";
            default -> "없음";
        };

        return ptyNm;
    }

    public static String determineLgtNm(String lgtCode){
        int lgt = Integer.parseInt(lgtCode);
        String lgtNm= "없음";
        if(lgt == 0){
            lgtNm = "없음";
        }else if(lgt >=1 && lgt <=50){
            lgtNm = "약함";
        }else if(lgt >=51 && lgt <=200){
            lgtNm = "보통";
        }else if(lgt >=201 && lgt <=500){
            lgtNm = "강함";
        }else{
            lgtNm = "매우강함";
        }
        return lgtNm;
    }

    public static String determineWsdNm(String wsdCode){
        double wsd = Double.parseDouble(wsdCode);

        String wsdNm= "없음";

        if(wsd == 0){
            wsdNm = "없음";
        }else if(wsd >0 && wsd <=3.99){
            wsdNm = "약함";
        }else if(wsd >=4 && wsd <=8.99){
            wsdNm = "보통";
        }else if(wsd >=9  && wsd <=13.99){
            wsdNm = "강함";
        }else{
            wsdNm = "매우강함";
        }

        return wsdNm;
    }

}
