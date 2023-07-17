package org.Isa4.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class MoneyInfo {

    float money;

    String firmId;

    String tagMoney;

    String clientCode;
}
