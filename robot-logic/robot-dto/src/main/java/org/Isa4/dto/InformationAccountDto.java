package org.Isa4.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class InformationAccountDto {

    String account;

    String clientCode;

    String classCode;

    String firmId;

    String tagMoney;

    float money;
}
