package com.enfernuz.quik.lua.rpc.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.Isa4.dto.TransactionDto;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionRPCMapper {

    private static long transId=System.currentTimeMillis()/1000;

    public static  Map<String, String> toMap(TransactionDto dto) {
        Map<String, String> transaction=new HashMap<>();
        transaction.put("TRANS_ID",dto.getTransId().toString());
        transaction.put("ACTION", dto.getAction());  // «NEW_ORDER» – новая заявка,«KILL_ORDER» – снять заявку
        transaction.put("CLASSCODE",dto.getClassCode());
        if (dto.getClientCode()!=null){
            transaction.put("CLIENT_CODE", dto.getClientCode());
        }
        transaction.put("SECCODE",dto.getSecCode());
        transaction.put("OPERATION",dto.getOperation()); // Операция ("B" - buy, или "S" - sell)
        transaction.put("TYPE",dto.getType());  // Лимитная заявка 'L', по рынку 'M'
        transaction.put("QUANTITY",Long.toString(dto.getQuantity()));
        transaction.put("ACCOUNT",dto.getAccount());
        if(dto.getType().equals("L")){
            transaction.put("PRICE",Float.toString(dto.getPrice()));
        } else {
            transaction.put("PRICE","0");
        }
        transaction.put("COMMENT","ZAJAVKA");
        return transaction;
    }
}
