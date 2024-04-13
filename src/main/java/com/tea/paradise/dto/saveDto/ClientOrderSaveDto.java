package com.tea.paradise.dto.saveDto;

import com.tea.paradise.dto.packages.ShortPackageDto;
import com.tea.paradise.dto.recipient.RecipientDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientOrderSaveDto {
    private RecipientDto recipientDto;
    private Long addressId;
    private List<ShortPackageDto> shortPackageDtos;
    private Boolean isPayWithBonuses;
}
