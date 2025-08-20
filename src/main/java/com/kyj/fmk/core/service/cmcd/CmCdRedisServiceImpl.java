package com.kyj.fmk.core.service.cmcd;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.kyj.fmk.core.model.CmCdConst;
import com.kyj.fmk.core.model.cmcd.req.ReqCommonCdDTO;

import com.kyj.fmk.core.redis.RedisKey;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2025-08-111
 * @author 김용준
 * Restful Api에서 사용하는 공통코드에 대한 레디스조회를 위한 서비스다
 *
 */
@Service
@RequiredArgsConstructor
public class CmCdRedisServiceImpl implements CmCdRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 공통코드를 레디스에서 조회하는 서비스
     *
     * @param reqCommonCdDTO
     * @return
     */
    @Override
    public Map<String, String> selectRedisCmCdMap(ReqCommonCdDTO reqCommonCdDTO) {

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        Map<String, String> grpStCdMap = null;


        if (reqCommonCdDTO.getCmCd().equals(CmCdConst.BTL_STATUS_CODE)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_BTL_STATUS_CODE);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.EVENT_STATUS_CODE)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_EVENT_STATUS_CODE);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.OCEAN_CODE)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_OCEAN_CODE);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.SKY_CODE)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_SKY_CODE);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.PARTICLE_CODE)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_PARTICLE_CODE);


            return grpStCdMap;
        }

        return null;

    }

}
