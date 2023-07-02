package com.enfernuz.quik.lua.rpc.mapper;

import com.enfernuz.quik.lua.rpc.model.ParamExAll;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParamExMapper {

    public static ParamExAll toEntity(Map<String, Object> dto) {
        return ParamExAll.builder()
                .classCode(dto.get("classCode").toString())
                .secCode(dto.get("secCode").toString())
                .build();
    }
}
