package org.Isa4.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.Isa4.dto.InformationToolDto;
import org.Isa4.model.InformationTool;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InformationToolMapper {

    public static InformationTool toEntity(InformationToolDto dto) {
        return InformationTool.builder()
                .classCode(dto.getClassCode())
                .secCode(dto.getSecCode())
                .bid(dto.getBid())
                .biddepth(dto.getBiddepth())
                .biddeptht(dto.getBiddeptht())
                .offer(dto.getOffer())
                .offerdepth(dto.getOfferdepth())
                .offerdeptht(dto.getOfferdeptht())
                .last(dto.getLast())
                .open(dto.getOpen())
                .createdTime(dto.getCreatedTime())
                .build();
    }
}
