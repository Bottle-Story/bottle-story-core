package com.kyj.fmk.core.mail;

import java.util.Map;
/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 메일전송 유틸
 */
public class MailUtil {

    private static final Map<String,String> subjectMap=
            Map.of("subject1","<h1>sub</h1>"
                    ,"2","<h2>sub</h2>"
            );




    public static String getTemplate (String templateNm,String val1){
        StringBuilder stringBuilder = new StringBuilder();

        if (templateNm.equals("1")){
            stringBuilder.append("<h1>asdasd</h1>");
            stringBuilder.append(val1);

        }

        return stringBuilder.toString();
    }

    public static String getSubject(String key){
        return subjectMap.get(key);
    }



}
