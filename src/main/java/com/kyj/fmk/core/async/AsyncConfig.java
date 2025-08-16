package com.kyj.fmk.core.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 비동기 설정
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 비동기사용을 위한 Excutor를 설정한다.
     * @return
     */
    @Override
    @Bean
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);           // 기본 쓰레드 수
        executor.setMaxPoolSize(10);           // 최대 쓰레드 수
        executor.setQueueCapacity(100);        // 큐에 대기 가능한 작업 수
        executor.setThreadNamePrefix("Async-"); // 쓰레드 이름 접두사
        executor.initialize();
        return executor;
    }
}
