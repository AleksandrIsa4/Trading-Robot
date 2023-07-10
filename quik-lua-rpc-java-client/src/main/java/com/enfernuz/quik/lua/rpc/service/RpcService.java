package com.enfernuz.quik.lua.rpc.service;

import com.enfernuz.quik.lua.rpc.api.messages.GetParamEx;
import com.enfernuz.quik.lua.rpc.api.messages.Message;
import com.enfernuz.quik.lua.rpc.api.zmq.ZmqTcpQluaRpcClient;
import com.enfernuz.quik.lua.rpc.dto.InformationAccountResponse;
import com.enfernuz.quik.lua.rpc.model.InformationTool;
import com.enfernuz.quik.lua.rpc.model.ParamExAll;
import com.enfernuz.quik.lua.rpc.model.PositionInstrument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@EnableKafka
@RequiredArgsConstructor
@Service
public class RpcService {


    private final ZmqTcpQluaRpcClient rpcClient;

    @Value("${class.code}")
    private String CLASS_CODE;

    public void info() {
        final Integer result = rpcClient.qlua_message("Hello, world!", Message.IconType.WARNING);
        if (result == null) {
            log.error("Удалённая процедура 'message' выполнилась с ошибкой.");
        } else {
            log.info("Результат выполнения удалённой процедуры 'message': {}.", result);
        }
    }

    public InformationTool infoParamEx(ParamExAll param) {
        return InformationTool.builder()
                .lotsize((long) Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "LOTSIZE")).getParamValue()))
                .bid(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "BID")).getParamValue()))
                .biddepth(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "BIDDEPTH")).getParamValue()))
                .biddeptht(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), " BIDDEPTHT")).getParamValue()))
                .offer(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OFFER")).getParamValue()))
                .offerdepth(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OFFERDEPTH")).getParamValue()))
                .offerdeptht(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OFFERDEPTHT")).getParamValue()))
                .last(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "LAST")).getParamValue()))
                .open(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OPEN")).getParamValue()))
                .secPriceStep(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "SEC_PRICE_STEP")).getParamValue()))
                .build();
    }

    public List<PositionInstrument> infoPositionInstrument(InformationAccountResponse informationAccountResponse) {
        List<PositionInstrument> fullPositionInstruments = new ArrayList<>();
        Map<String, String> instruments = new HashMap<>();
        List<Map<String, String>> fullInstrument = new ArrayList<>();
        long lineDepoLimits = rpcClient.qlua_getNumberOf("depo_limits");
        for (int i = 0; i < lineDepoLimits; i++) {
            instruments = rpcClient.qlua_getItem("depo_limits", i);
            if (instruments.get("client_code").equals(informationAccountResponse.getClientCode()) && instruments.get("limit_kind").equals("0") && instruments.get("trdaccid").equals(informationAccountResponse.getAccount())) {
                fullInstrument.add(instruments);
            }
        }
        for (Map<String, String> element : fullInstrument) {
            PositionInstrument positionInstrument = PositionInstrument.builder()
                    .classCode(CLASS_CODE)
                    .secCode(element.get("sec_code"))
                    .quantity((long)Float.parseFloat(element.get("currentbal")))
                    .averagePrice(Float.parseFloat(element.get("awg_position_price")))
                    .build();
            fullPositionInstruments.add(positionInstrument);
        }
        return fullPositionInstruments;
    }
}