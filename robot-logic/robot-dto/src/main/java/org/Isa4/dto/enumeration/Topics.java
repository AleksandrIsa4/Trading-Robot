package org.Isa4.dto.enumeration;

import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@ToString
public enum Topics {

    TOPIC_PARAM(Constants.TOPIC_PARAM),
    TOPIC_GETITEM(Constants.TOPIC_GETITEM),
    TOPIC_TRANSACTION_DTO(Constants.TOPIC_TRANSACTION_DTO),
    TOPIC_INFORMATION_TOOL(Constants.TOPIC_INFORMATION_TOOL),
    TOPIC_POSITION_INSTRUMENT(Constants.TOPIC_POSITION_INSTRUMENT),
    TOPIC_MONEY_INFO(Constants.TOPIC_MONEY_INFO),
    TOPIC_LOGIC_RUN(Constants.TOPIC_LOGIC_RUN),
    TOPIC_RPC_RUN(Constants.TOPIC_RPC_RUN),
    TOPIC_TRANSACTION_LOGIC(Constants.TOPIC_TRANSACTION_LOGIC);

    private final String name;

    public static class Constants {
        public static final String TOPIC_PARAM = "paramEx";
        public static final String TOPIC_GETITEM = "getItem";
        public static final String TOPIC_TRANSACTION_DTO = "getTransactionDto";
        public static final String TOPIC_INFORMATION_TOOL = "informationTool";
        public static final String TOPIC_POSITION_INSTRUMENT = "positionInstrumentKafka";
        public static final String TOPIC_MONEY_INFO = "moneyInfo";
        public static final String TOPIC_TRANSACTION_LOGIC = "TransactionLogic";
        public static final String TOPIC_LOGIC_RUN = "logicRun";
        public static final String TOPIC_RPC_RUN = "RpcRun";
    }

    public static List<String> fromAll() {
        return Arrays.stream(Topics.values()).map(topics -> topics.getName()).collect(Collectors.toList());
    }
}
