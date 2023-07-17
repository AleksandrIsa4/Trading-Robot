package com.enfernuz.quik.lua.rpc.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class InformationTool {

    String secCode;

    String classCode;

    Long lotSize;

    Float bid;

    Float biddepth;

    Float biddeptht;

    Float offer;

    Float offerdepth;

    Float offerdeptht;

    Float last;

    Float open;

    Float secPriceStep;
}
