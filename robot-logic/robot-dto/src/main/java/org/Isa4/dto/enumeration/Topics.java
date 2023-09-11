package org.Isa4.dto.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@ToString
public enum Topics {
    TOPIC_PARAM("paramEx"),
    TOPIC_GETITEM("getItem"),
    TOPIC_TRANSACTION_DTO("getTransactionDto"),
    TOPIC_INFORMATION_TOOL("informationTool"),
    TOPIC_POSITION_INSTRUMENT("positionInstrument"),
    TOPIC_MONEY_INFO("moneyInfo");
    private final String name;

    public static List<String> fromAll() {
        return Arrays.stream(Topics.values()).map(topics -> topics.getName()).collect(Collectors.toList());
    }
}
