package com.kyj.fmk.core.tx.lock;


import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.enm.CmErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
/**
 * 2025-06-18
 * @author 김용준
 * Restful Api에서 사용하는 분산락 서비스
 */
@Component
@RequiredArgsConstructor
public class RedisDtbLock implements DtbLock {

    private final RedisTemplate<String,Object> redisTemplate;
    private final long LOCK_TIMEOUT = 30;
    private final long LOCK_EXPIRED = 30;



    @Override
    public boolean  acquired(String service) {

        String lockVal = String.valueOf(System.currentTimeMillis() + 30000);
        boolean acquired = false;

        long startTime = System.currentTimeMillis();


        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(LOCK_TIMEOUT)) {
            acquired = setLock(service, lockVal, LOCK_EXPIRED);

            if (Boolean.TRUE.equals(acquired)) {
                acquired = true;
                break;  // 락 획득 성공
            } else {
                try {
                    Thread.sleep(200);  // 0.2초 대기 후 재시도
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  // 인터럽트 처리
                }
            }
        }

        if(!acquired){
            throw new KyjSysException(CmErrCode.CM012);
        }
        return acquired;

    }

    @Override
    public void deleteLock(String service) {
        redisTemplate.delete(service);
    }

    @Override
    public boolean isNotTimeOut(String service) {
        Object obj =  redisTemplate.opsForValue().get(service);
        if(obj!=null){
            return true;
        }
        return false;
    }

    private boolean setLock(String service, String value, long expired){
        TimeUnit timeUnit = TimeUnit.SECONDS;

        return redisTemplate.opsForValue().setIfAbsent(service,value,expired,timeUnit);
    }

}
