package com.kyj.fmk.core.service.eai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.model.wheather.*;
import com.kyj.fmk.core.util.DetermineWhtr;
import com.kyj.fmk.core.util.KmaGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2025-08-25
 * @author 김용준
 *  날씨 관련  api
 */
@RequiredArgsConstructor
@Service
public class WheatherApiServiceImpl implements WheatherApiService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();


    /**
     * 기상청에서 날씨정보를 가져오는 API
     *
     * @param reqWheatherApiDTO
     * @return
     */
    @Override
    public List<ResWheatherApiDTO> loadWheather(ReqWheatherApiDTO reqWheatherApiDTO) {

        if (reqWheatherApiDTO.getLat() == null || reqWheatherApiDTO.getLot() == null) {
            throw new KyjBizException(CmErrCode.CM018);
        }

        //오늘날짜+시간
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime exchangeNow = now;

        LocalDateTime baseDateTime;
        String baseTime;
        if (now.getMinute() < 30) {
            // 0~29분 → 이전 시각의 30분
            baseDateTime = now.minusHours(1);
            baseTime = String.format("%02d30", baseDateTime.getHour());
        } else {
            // 30~59분 → 현재 시각의 30분
            baseDateTime = now;
            baseTime = String.format("%02d30", baseDateTime.getHour());
        }

// baseDate 계산 (baseTime 기준 날짜)
        String baseDate = baseDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));


        // (KMA 격자 정수)
        KmaEntity kmaEntity = KmaGrid.getKmaEntity(reqWheatherApiDTO.getLat(), reqWheatherApiDTO.getLot());

        String nx = kmaEntity.getNx();
        String ny = kmaEntity.getNy();

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

            String strWthrDate = list.get(0).path("fcstDate").asText();
            String strWthrTime = list.get(0).path("fcstTime").asText();
            String strWthrBaseDate = list.get(0).path("baseDate").asText();
            String strWthrBaseTime = list.get(0).path("baseTime").asText();


            LocalTime wthrTime = LocalTime.parse(strWthrTime, DateTimeFormatter.ofPattern("HHmm"));
            LocalDate wthrDate = LocalDate.parse(strWthrDate, DateTimeFormatter.ofPattern("yyyyMMdd"));

            LocalTime wthrBaseTime = LocalTime.parse(strWthrBaseTime, DateTimeFormatter.ofPattern("HHmm"));
            LocalDate wthrBaseDate = LocalDate.parse(strWthrBaseDate, DateTimeFormatter.ofPattern("yyyyMMdd"));

            dto.setWthrDate(wthrDate);
            dto.setWthrTime(wthrTime);
            dto.setWthrBaseTime(wthrBaseTime);
            dto.setWthrBaseDate(wthrBaseDate);
            dto.setRegDateTime(exchangeNow);

            WhtrData wthData = new WhtrData();

            for (JsonNode item : list) {
                String category = item.path("category").asText();
                String value = item.path("fcstValue").asText();

                switch (category) {
                    case "SKY":
                        wthData.setSky(Integer.parseInt(value));
                        wthData.setSkyNm(DetermineWhtr.determineSkyNm(value));
                        break;
                    case "PTY":
                        wthData.setPty(Integer.parseInt(value));
                        wthData.setPtyNm(DetermineWhtr.determinePtyNm(value));
                        break;
                    case "LGT":
                        wthData.setLgt(Integer.parseInt(value));
                        wthData.setLgtNm(DetermineWhtr.determineLgtNm(value));
                        break;
                    case "WSD":
                        wthData.setWsd(Integer.parseInt(value));
                        wthData.setWsdNm(DetermineWhtr.determineWsdNm(value));
                        break;
                    case "T1H":
                        wthData.setT1h(value);
                        break;
                }
            }
            dto.setWthData(wthData);

            result.add(dto);
        });


        return result;
    }

    /**
     * 일몰과 일출시간을 가져오는 API
     *
     * @param reqWheatherApiDTO
     * @return
     */
    @Override
    public ResSunRiseSetApiDTO loadSunRiseSet(ReqWheatherApiDTO reqWheatherApiDTO) {

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDate date = now.toLocalDate();
        String url = "https://api.sunrise-sunset.org/json"
                + "?lat=" + reqWheatherApiDTO.getLat()
                + "&lng=" + reqWheatherApiDTO.getLot()
                + "&date=" + date
                + "&formatted=0"
                + "&tzid=Asia/Seoul";
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(url, String.class);
        } catch (RestClientException e) {
            throw new KyjSysException(CmErrCode.CM017);
        }

        String json = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KyjSysException(CmErrCode.CM017);
        }


        String body = response.getBody();

        if (body == null || body.isEmpty()) {
            throw new KyjSysException(CmErrCode.CM017);
        }

        JsonNode root;

        try {
            root = objectMapper.readTree(body); // JSON 파싱만 try
        } catch (Exception e) {
            throw new KyjSysException(CmErrCode.CM016); // JSON 파싱 실패
        }

        // JSON 파싱 성공 후 별도로 상태 체크
        String status = root.path("status").asText();
        if (!"OK".equals(status)) {
            throw new KyjSysException(CmErrCode.CM017); // OK가 아닐 때 예외
        }


        JsonNode results = root.path("results");
        String sunriseStr = results.path("sunrise").asText();
        String sunsetStr = results.path("sunset").asText();

        //  12시간 형식 문자열 → LocalTime
        OffsetDateTime sunriseOdt = OffsetDateTime.parse(sunriseStr);
        OffsetDateTime sunSetOdt = OffsetDateTime.parse(sunsetStr);

        LocalTime sunrise = sunriseOdt.toLocalTime();
        LocalTime sunset = sunSetOdt.toLocalTime();


        ResSunRiseSetApiDTO resSunRiseSetApiDTO = new ResSunRiseSetApiDTO();
        resSunRiseSetApiDTO.setSunRiseTime(sunrise);
        resSunRiseSetApiDTO.setSunSetTime(sunset);

        return resSunRiseSetApiDTO;
    }


}