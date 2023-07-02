package org.Isa4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class InformationTool {

    Long lotsize;

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
