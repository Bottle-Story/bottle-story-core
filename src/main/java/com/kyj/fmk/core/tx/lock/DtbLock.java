package com.kyj.fmk.core.tx.lock;
/**
 * 2025-06-18
 * @author 김용준
 * Restful Api에서 사용하는 분산락 서비스
 */
public interface DtbLock {
    public boolean  acquired(String service);
    public void deleteLock(String service);
    public boolean isNotTimeOut(String service);
}
