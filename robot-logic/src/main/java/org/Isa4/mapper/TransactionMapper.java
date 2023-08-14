package org.Isa4.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.Isa4.dto.TransactionDto;
import org.Isa4.model.InformationAccount;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionMapper {

    private static long transId = System.currentTimeMillis() / 1000;

    public static Map<String, String> toMap(TransactionDto dto) {
        Map<String, String> transaction = new HashMap<>();
        transaction.put("ACTION", dto.getAction());  // «NEW_ORDER» – новая заявка,«KILL_ORDER» – снять заявку
        transaction.put("CLASSCODE", dto.getClassCode());
        if (dto.getClientCode() != null) {
            transaction.put("CLIENT_CODE", dto.getClientCode());
        }
        transaction.put("SECCODE", dto.getSecCode());
        transaction.put("OPERATION", dto.getOperation()); // Операция ("B" - buy, или "S" - sell)
        transaction.put("TYPE", dto.getType());  // Лимитная заявка 'L', по рынку 'M'
        transaction.put("QUANTITY", Long.toString(dto.getQuantity()));
        transaction.put("ACCOUNT", dto.getAccount());
        if (dto.getType().equals("L")) {
            transaction.put("PRICE", Float.toString(dto.getPrice()));
        } else {
            transaction.put("PRICE", "0");
        }
        transaction.put("COMMENT", "ZAJAVKA");
        return transaction;
    }

    public static TransactionDto toAdditionAccount(TransactionDto dto, InformationAccount informationAccount) {
        dto.setTransId(transId);
        transId++;
        dto.setAccount(informationAccount.getAccount());
        dto.setClientCode(informationAccount.getClientCode());
        return dto;
    }
}
