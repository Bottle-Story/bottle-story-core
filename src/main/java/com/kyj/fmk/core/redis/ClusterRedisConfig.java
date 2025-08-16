package com.kyj.fmk.core.redis;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
/**
 * 2025-06-18
 * @author 김용준
 * Restful Api에서 사용하는 다중노드 Redis 클러스터 설정
 * RedisConfig를 상속받는다.
 */
import java.util.Arrays;
@Profile(value = "prd")
public class ClusterRedisConfig extends RedisConfig{

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;  // AWS Elasticache에서 제공하는 클러스터 엔드포인트

    /**
     *
     * RedisConnectionFactory에 대한 설정을 한다 .
     * @param
     * @return RedisConnectionFactory
     */
    @Override
    public RedisConnectionFactory redisConnectionFactory() {
        // 클러스터 노드들에 대한 엔드포인트를 콤마로 구분된 문자열로 받는다.
        String[] nodes = clusterNodes.split(",");
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(Arrays.asList(nodes));

        // Lettuce 클라이언트 설정
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)  // 읽기 요청을 슬레이브에서 처리하도록 설정
                .build();

        return new LettuceConnectionFactory(clusterConfig, clientConfig);
    }





}
