package org.Isa4.dto;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class InformationAccountRequest {
    @NotNull
    String account;

    String clientCode;

    @NotNull
    String classCode;

    @NotNull
    String firmId;

    @NotNull
    String tagMoney;
}