package ubs.application.codingtask.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OptionEntity extends TradeEntity{

    String style;
    String strategy;
    LocalDate deliveryDate;
    LocalDate expiryDate;
    LocalDate excerciseStartDate;
    String payCcy;
    Double premium;
    String premiumCcy;
    String premiumType;
    LocalDate premiumDate;
}
