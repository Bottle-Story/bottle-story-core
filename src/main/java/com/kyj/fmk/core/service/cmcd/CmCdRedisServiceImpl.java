package com.kyj.fmk.core.service.cmcd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.CmCdConst;
import com.kyj.fmk.core.model.cmcd.req.ReqCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqSkillCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResSkillCdDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;
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
public class CmCdRedisServiceImpl implements CmCdRedisService{

    private final RedisTemplate<String,String> redisTemplate;

    private final ObjectMapper objectMapper;
    /**
     * 공통코드를 레디스에서 조회하는 서비스
     * @param reqCommonCdDTO
     * @return
     */
    @Override
    public  Map<String, String> selectRedisCmCdMap(ReqCommonCdDTO reqCommonCdDTO) {

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        Map<String, String> grpStCdMap =  null;



        if(reqCommonCdDTO.getCmCd().equals(CmCdConst.TEAM_STY_CD)){
            grpStCdMap = hashOps.entries(RedisKey.CM_TEAM_STY_CD);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.MT_STY_CD)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_MT_STY_CD);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.RECRUIT_ST_CD)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_RECRUIT_ST_CD);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.GRP_ST_CD)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_GRP_ST_CD);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.CMC_TONE_CD)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_CMC_TONE_CD);


        } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.APY_ST_CD)) {
            grpStCdMap = hashOps.entries(RedisKey.CM_APY_ST_CD);


        }

        return grpStCdMap;
    }


    /**
     * 사용기술 을 레디스에서 전체 조회하는 서비스
     * @param
     * @return
     */
    @Override
    public Map<String, ResSkillCdDTO> selectRedisSkillAllMap() {

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        Map<String, String> skillMap =   hashOps.entries(RedisKey.SKILL_CD_KEY_ALL);
        Map<String, ResSkillCdDTO> dtoMap = new HashMap<>();

        for (Map.Entry<String, String> entry : skillMap.entrySet()) {
            String key = entry.getKey();
            String jsonValue = entry.getValue();

            try {
                ResSkillCdDTO dto = objectMapper.readValue(jsonValue, ResSkillCdDTO.class);
                dtoMap.put(key, dto);
            } catch (JsonProcessingException e) {
                throw new KyjSysException(CmErrCode.CM016);
            }


        }


        return dtoMap;
    }

    /**
     *  사용기술 을 레디스에서 하나만 조회하는 서비스
     * @param reqSkillCdDTO
     * @return
     */
    @Override
    public  ResSkillCdDTO selectRedisSkillEachMap(ReqSkillCdDTO reqSkillCdDTO) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        Map<String, String> skillMap =  null;
        ResSkillCdDTO resSkillCdDTO = new ResSkillCdDTO();

        skillMap = hashOps.entries(RedisKey.SKILL_CD_KEY+reqSkillCdDTO.getSkillCd());
        String skillNm =skillMap.get(RedisKey.SUFFIX_SKILL_NM_KEY);
        String skillCdImg =skillMap.get(RedisKey.SUFFIX_SKILL_CD_IMG_KEY);

        resSkillCdDTO.setSkillCd(reqSkillCdDTO.getSkillCd());
        resSkillCdDTO.setSkillNm(skillNm);
        resSkillCdDTO.setSkillCdImg(skillCdImg);

        return resSkillCdDTO;
    }



}
