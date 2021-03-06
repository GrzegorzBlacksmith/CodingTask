package ubs.application.codingtask.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;
import ubs.application.codingtask.domain.entity.enums.TradeType;

import java.time.LocalDate;

@Value
@NonFinal
@Builder
@AllArgsConstructor
public class InputDTO {
    String customer;
    String ccyPair;
    TradeType type;
    String direction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate tradeDate;
    Double amount1;
    Double amount2;
    Double rate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate valueDate;
    String legalEntity;
    String trader;
    String style;
    String strategy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate deliveryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate expiryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate excerciseStartDate;
    String payCcy;
    Double premium;
    String premiumCcy;
    String premiumType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate premiumDate;
}