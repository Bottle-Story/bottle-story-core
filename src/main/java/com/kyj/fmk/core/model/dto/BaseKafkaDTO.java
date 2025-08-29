package com.kyj.fmk.core.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-29
 * @author 김용준
 * Kafka통신에서 사용되는 BASE DTO
 */
@Setter
@Getter
public class BaseKafkaDTO {
    private String topic;
    private String From;
}
