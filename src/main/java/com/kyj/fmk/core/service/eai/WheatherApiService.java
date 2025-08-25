package com.kyj.fmk.core.service.eai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyj.fmk.core.model.wheather.ReqWheatherApiDTO;
import com.kyj.fmk.core.model.wheather.ResWheatherApiDTO;

import java.util.List;

/**
 * 2025-08-25
 * @author 김용준
 * 기상청에서 날씨를 가져오는 api
 */
public interface WheatherApiService {

    public List<ResWheatherApiDTO> loadWheather(ReqWheatherApiDTO reqWheatherApiDTO) throws JsonProcessingException;
}
