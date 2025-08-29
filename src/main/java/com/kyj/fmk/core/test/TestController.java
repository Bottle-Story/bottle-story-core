package com.kyj.fmk.core.test;

import com.kyj.fmk.core.model.wheather.*;
import com.kyj.fmk.core.service.eai.WheatherApiService;
import com.kyj.fmk.core.service.eai.WheatherApiServiceImpl;
import com.kyj.fmk.core.util.DetermineUiCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {


    private final WheatherApiService wheatherApiService;

    @GetMapping("/test")
    public List<ResWheatherApiDTO> t(ReqWheatherApiDTO reqWheatherApiDTO){

        List<ResWheatherApiDTO> list = wheatherApiService.loadWheather(reqWheatherApiDTO);
        ResSunRiseSetApiDTO resSunRiseSetApiDTO =  wheatherApiService.loadSunRiseSet(reqWheatherApiDTO);
        for(ResWheatherApiDTO resWheatherApiDTO :list){

            ReqDetermineUiCode reqDetermineUiCode = new ReqDetermineUiCode();
            reqDetermineUiCode.setWhtrData(resWheatherApiDTO.getWthData());
            reqDetermineUiCode.setWthrTime(resWheatherApiDTO.getWthrTime());
            reqDetermineUiCode.setSunSetTime(resSunRiseSetApiDTO.getSunSetTime());
            reqDetermineUiCode.setSunRiseTime(resSunRiseSetApiDTO.getSunRiseTime());

            WhtrUiCode whtrUiCode = DetermineUiCode.determineUiCode(reqDetermineUiCode);

            System.out.println("whtrUiCode.toString() = " + whtrUiCode.getOceanCode());
            System.out.println("whtrUiCode.toString() = " + whtrUiCode.getSkyCode());
            System.out.println("whtrUiCode.toString() = " + whtrUiCode.getParticleCode());
        }
        System.out.println("resSunRiseSetApiDTO.getSunRiseTime() = " + resSunRiseSetApiDTO.getSunRiseTime());
        System.out.println("resSunRiseSetApiDTO.getSunsetTime() = " + resSunRiseSetApiDTO.getSunSetTime());
        return list;
    }

    @GetMapping("/test2")
    public ResSunRiseSetApiDTO tt(ReqWheatherApiDTO reqWheatherApiDTO){

        ResSunRiseSetApiDTO resSunRiseSetApiDTO =  wheatherApiService.loadSunRiseSet(reqWheatherApiDTO);

        return resSunRiseSetApiDTO;
    }
}
