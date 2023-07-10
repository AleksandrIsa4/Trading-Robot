package com.enfernuz.quik.lua.rpc.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class PositionInstrument {

    String classCode;

    String secCode;

    Long quantity;

    Float averagePrice;
}
