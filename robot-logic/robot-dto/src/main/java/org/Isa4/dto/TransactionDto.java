package org.Isa4.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.Isa4.dto.enumeration.Status;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class TransactionDto {

    Long transId;

    Long transNumber;

    int orderGetItem;

    Long orderNumber;

    int transGetItem;

    String classCode;

    String action;

    String secCode;

    String clientCode;

    Status status;

    String operation;

    String Type;

    float price;

    int quantity;

    int quantityComplete;

    String account;
}
