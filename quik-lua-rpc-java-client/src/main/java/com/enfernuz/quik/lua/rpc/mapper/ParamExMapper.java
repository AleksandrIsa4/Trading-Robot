package com.enfernuz.quik.lua.rpc.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.Isa4.dto.ParamExAll;

import java.util.Map;

// TODO: Не используется
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParamExMapper {

    public static ParamExAll toEntity(Map<String, Object> dto) {
        return ParamExAll.builder()
                .classCode(dto.get("classCode").toString())
                .secCode(dto.get("secCode").toString())
                .build();
    }
}
