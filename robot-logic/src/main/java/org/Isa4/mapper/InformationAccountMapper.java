package org.Isa4.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.Isa4.dto.InformationAccountDto;
import org.Isa4.dto.InformationAccountRequest;
import org.Isa4.model.InformationAccount;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InformationAccountMapper {

    public static InformationAccount toEntityRequest(InformationAccountRequest dto) {
        return InformationAccount.builder()
                .account(dto.getAccount())
                .clientCode(dto.getClientCode())
                .classCode(dto.getClassCode())
                .tagMoney(dto.getTagMoney())
                .firmId(dto.getFirmId())
                .build();
    }

    public static InformationAccountDto toDto(InformationAccount informationAccount) {
        return InformationAccountDto.builder()
                .clientCode(informationAccount.getClientCode())
                .account(informationAccount.getAccount())
                .tagMoney(informationAccount.getTagMoney())
                .firmId(informationAccount.getFirmId())
                .classCode(informationAccount.getClassCode())
                .build();
    }
}
