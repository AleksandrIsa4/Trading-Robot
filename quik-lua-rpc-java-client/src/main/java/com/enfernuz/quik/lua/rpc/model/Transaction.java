package com.enfernuz.quik.lua.rpc.model;

import com.enfernuz.quik.lua.rpc.model.enumeration.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Transaction {

    Long id;

    String classCode;

    String secCode;

    String clientCode;

    Status status;

    String operationType;

    Float price;

    Long quantity;

    Long quantityComplete;
}
