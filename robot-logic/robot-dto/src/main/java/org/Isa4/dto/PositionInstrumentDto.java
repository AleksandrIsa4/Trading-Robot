package org.Isa4.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class PositionInstrumentDto {

    String classCode;

    String secCode;

    Long quantity;

    Float averagePrice;

    Long lotSize;

    Float secPriceStep;
}