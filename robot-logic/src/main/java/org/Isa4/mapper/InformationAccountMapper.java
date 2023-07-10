package org.Isa4.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.Isa4.dto.InformationAccountRequest;
import org.Isa4.dto.InformationAccountResponse;
import org.Isa4.model.InformationAccount;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InformationAccountMapper {

    public static InformationAccount toEntity(InformationAccountRequest dto) {
        return InformationAccount.builder()
                .account(dto.getAccount())
                .clientCode(dto.getClientCode())
                .classCode(dto.getClassCode())
                .build();
    }

    public static InformationAccountResponse toDto(InformationAccount informationAccount) {
        return InformationAccountResponse.builder()
                .clientCode(informationAccount.getClientCode())
                .account(informationAccount.getAccount())
                .build();
    }
}
