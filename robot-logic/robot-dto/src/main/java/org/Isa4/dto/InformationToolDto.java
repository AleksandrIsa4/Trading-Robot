package org.Isa4.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class InformationToolDto {

    String secCode;

    String classCode;

    Float bid;

    Float biddepth;

    Float biddeptht;

    Float offer;

    Float offerdepth;

    Float offerdeptht;

    Float last;

    Float open;
}
