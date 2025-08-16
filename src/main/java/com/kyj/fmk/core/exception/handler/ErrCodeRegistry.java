package com.kyj.fmk.core.exception.handler;

import com.kyj.fmk.core.model.ErrCode;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 에러코드를 ErrCodeRegistry로 전부로드한다.
 */
@Component
public class ErrCodeRegistry {

    private static final Map<String, ErrCode> ERR_CODE_MAP = new HashMap<>();

    @Autowired
    public ErrCodeRegistry(ApplicationContext context) {
        // Bean으로 등록된 ErrCode 구현체 전부 로드
        context.getBeansOfType(ErrCode.class).values()
                .forEach(errCode -> ERR_CODE_MAP.put(errCode.getCode(), errCode));

        // enum 타입까지 포함하려면 Reflections 추가
        Reflections reflections = new Reflections("com.kyj");
        Set<Class<? extends ErrCode>> enums = reflections.getSubTypesOf(ErrCode.class);
        for (Class<? extends ErrCode> clazz : enums) {
            if (clazz.isEnum()) {
                for (ErrCode code : clazz.getEnumConstants()) {
                    ERR_CODE_MAP.put(code.getCode(), code);
                }
            }
        }
    }

    public static Optional<ErrCode> get(String code) {
        return Optional.ofNullable(ERR_CODE_MAP.get(code));
    }
}
