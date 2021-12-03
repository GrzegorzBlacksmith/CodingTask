package ubs.application.codingtask.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
@Builder
@AllArgsConstructor
public class ResponseElementDto {
    TradeDTO tradeDTO;
    ValidationDTO validationDto;
}
