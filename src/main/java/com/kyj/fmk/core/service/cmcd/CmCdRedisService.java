package com.kyj.fmk.core.service.cmcd;

import com.kyj.fmk.core.model.cmcd.req.ReqCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqSkillCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResSkillCdDTO;

import java.util.List;
import java.util.Map;

/**
 * 2025-08-111
 * @author 김용준
 * Restful Api에서 사용하는 공통코드에 대한 레디스조회를 위한 서비스다
 *
 */
public interface CmCdRedisService {
    /**
     * 공통코드를 레디스에서 조회하는 서비스
     * @param reqCommonCdDTO
     * @return
     */
    public Map<String, String> selectRedisCmCdMap(ReqCommonCdDTO reqCommonCdDTO);


    public Map<String, ResSkillCdDTO> selectRedisSkillAllMap();

    public ResSkillCdDTO selectRedisSkillEachMap(ReqSkillCdDTO reqSkillCdDTO);
}
