package com.enfernuz.quik.lua.rpc.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class InformationAccountResponse {

    String clientCode;

    String account;
}
