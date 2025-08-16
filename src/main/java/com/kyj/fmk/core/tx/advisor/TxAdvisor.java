package com.kyj.fmk.core.tx.advisor;

//import com.kyj.fmk.core.tx.dataSource.DataSourceContextHolder;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.tx.lock.DtbLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * 2025-06-18
 * @author 김용준
 * Restful Api에서 사용하는 트랜잭션 어드바이저(데이터소스, 트랜잭션)
 */

@Slf4j
public class TxAdvisor {

        /**
         * 동적 데이터소스에 대한 어드바이저 static 클래스
         */
//        @Order(2)
//        @Component
//        @Aspect
//       public static class DataSourceAspect {
//        @Before("@annotation(ReadOnlyDB)")
//        public void useReadDataSource() {
//            DataSourceContextHolder.setDataSourceType("READ");
//        }
//
//        @After("@annotation(ReadOnlyDB)")
//        public void clearDataSource() {
//            DataSourceContextHolder.clearDataSourceType();
//        }
//        @Before("@annotation(WriteDB)")
//        public void useWriteDataSource() {
//            DataSourceContextHolder.setDataSourceType("WRITE");
//        }
//        @After("@annotation(WriteDB)")
//        public void clearWriteDataSource() {
//            DataSourceContextHolder.clearDataSourceType();
//        }
//
//
//
//
//    }
        /**
         * 트랜잭션매니저 어드바이저 static 클래스 별다른 기능은 없고 트랜잭션시점을 커스텀 데이터소스 주입이후로 미루기위함이다.
         */
    @Order(3)
    @Component
    @Aspect // 트랜잭션 처리는 데이터소스 설정 후
    public static class TxAspect{
        @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
        public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
            return joinPoint.proceed();
        }
    }


    /**
     * 분산락을 제어하기위한 aop
     */
    @Order(1)
    @Component
    @Aspect
    @RequiredArgsConstructor
    public static class DtbLockAspect{

        private  final DtbLock dtbLock;

        @Around("@annotation(com.kyj.fmk.core.tx.annotation.DtbLock)")
        public Object manageDtbLock(ProceedingJoinPoint joinPoint) throws Throwable {
            Object result;
        boolean acquired= dtbLock.acquired(joinPoint.getSignature().getName());
        boolean isNotTimeOut = true;
        if(acquired){
            try {
              result = joinPoint.proceed();
              isNotTimeOut = dtbLock.isNotTimeOut(joinPoint.getSignature().getName());
            } catch (Exception e) {
                throw new KyjSysException(CmErrCode.CM012);
            }
            finally {
                if(isNotTimeOut){
                    dtbLock.deleteLock(joinPoint.getSignature().getName());
                }else{
                    throw new KyjSysException(CmErrCode.CM002);
                }
            }
        }else{
            throw new KyjSysException(CmErrCode.CM012);
        }



            return result;

        }
    }
}
