package org.Isa4.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.Isa4.dto.PositionInstrumentDto;
import org.Isa4.model.BaseInfoToolKey;
import org.Isa4.model.PositionInstrument;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionInstrumentMapper {

    public static PositionInstrument toEntity(PositionInstrumentDto dto) {
        return PositionInstrument.builder()
                .pk(new BaseInfoToolKey(dto.getClassCode(), dto.getSecCode()))
                .quantity(dto.getQuantity())
                .averagePrice(dto.getAveragePrice())
                .lotSize(dto.getLotSize())
                .secPriceStep(dto.getSecPriceStep())
                .build();
    }

    public static PositionInstrumentDto toDto(PositionInstrument positionInstrument) {
        return PositionInstrumentDto.builder()
                .classCode(positionInstrument.getPk().getClassCode())
                .secCode(positionInstrument.getPk().getSecCode())
                .quantity(positionInstrument.getQuantity())
                .averagePrice(positionInstrument.getAveragePrice())
                .lotSize(positionInstrument.getLotSize())
                .secPriceStep(positionInstrument.getSecPriceStep())
                .build();
    }
}
