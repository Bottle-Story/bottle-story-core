package com.kyj.fmk.core.service.eai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.model.wheather.KmaEntity;
import com.kyj.fmk.core.model.wheather.ReqWheatherApiDTO;
import com.kyj.fmk.core.model.wheather.ResWheatherApiDTO;
import com.kyj.fmk.core.model.wheather.WthData;
import com.kyj.fmk.core.util.KmaGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2025-08-25
 * @author 김용준
 * 기상청에서 날씨를 가져오는 api
 */
@RequiredArgsConstructor
@Service
public class WheatherApiServiceImpl implements WheatherApiService{

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public List<ResWheatherApiDTO> loadWheather(ReqWheatherApiDTO reqWheatherApiDTO)  {

        if(reqWheatherApiDTO.getLat() == null || reqWheatherApiDTO.getLot()==null){
            throw new KyjBizException(CmErrCode.CM018);
        }

        //오늘날짜+시간
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        // 날짜
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 시간 계산
        int hour = now.getHour();
        int minute = now.getMinute();

        // baseTime 계산
        String baseTime;
        if (minute < 30) {
            // 0~29분 → 이전 시각의 30분
            now = now.minusHours(1);
            baseTime = String.format("%02d30", now.getHour());
        } else {
            // 30~59분 → 현재 시각의 30분
            baseTime = String.format("%02d30", hour);
        }


        // (KMA 격자 정수)
        KmaEntity kmaEntity = KmaGrid.getKmaEntity(reqWheatherApiDTO.getLat(), reqWheatherApiDTO.getLot());

        String nx = kmaEntity.getNx();
        String ny = kmaEntity.getNy();
        System.out.println("ny = " + ny);
        System.out.println("nx = " + nx);
        System.out.println("baseTime = " + baseTime);
        System.out.println("baseDate = " + baseDate);
        // URL (ServiceKey는 URL 인코딩된 값 사용!)
        String serviceKey = "0BqSd/droJ7OAIRlCoc69gIbhE5vRgueUJwCito7CKsh7vse8h1Uwsbx52iMrueAtaRiCevYA/EwUZIXDcnSig==";
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"
                + "?serviceKey=" + serviceKey
                + "&pageNo=1&numOfRows=1000&dataType=JSON"
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime
                + "&nx=" + nx
                + "&ny=" + ny;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String json = response.getBody();

        // 5️⃣ JSON 파싱
        JsonNode root = null;
        try {
            root = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }
        String resultCode = root.path("response").path("header").path("resultCode").asText();
        if (!"00".equals(resultCode)) {
            throw new KyjSysException(CmErrCode.CM017);
        }

        JsonNode itemsArray = root.path("response").path("body").path("items").path("item");

        if (!itemsArray.isArray()) {
            throw new KyjSysException(CmErrCode.CM017);
        }

        // 6️⃣ fcstDate + fcstTime 기준 그룹핑
        Map<String, List<JsonNode>> grouped = new HashMap<>();
        for (JsonNode item : itemsArray) {
            String key = item.path("fcstDate").asText() + "_" + item.path("fcstTime").asText();
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }

        // 7️⃣ DTO 변환
        List<ResWheatherApiDTO> result = new ArrayList<>();
        grouped.forEach((key, list) -> {
            ResWheatherApiDTO dto = new ResWheatherApiDTO();
            dto.setFcstDate(list.get(0).path("fcstDate").asText());
            dto.setFcstTime(list.get(0).path("fcstTime").asText());

            WthData wthData = new WthData();
            for (JsonNode item : list) {
                String category = item.path("category").asText();
                String value = item.path("fcstValue").asText();

                switch (category) {
                    case "SKY":
                        wthData.setSky(Integer.parseInt(value));
                        break;
                    case "PTY":
                        wthData.setPty(Integer.parseInt(value));
                        break;
                    case "LGT":
                        wthData.setLgt(Integer.parseInt(value));
                        break;
                    case "WSD":
                        wthData.setWsd(Integer.parseInt(value));
                        break;
                    case "T1H":
                        wthData.setT1h(value);
                        break;
                    // 필요한 다른 카테고리도 추가 가능
                }
            }
            dto.setWthData(wthData);
            result.add(dto);
        });


        return result;
    }
}
