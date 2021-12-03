package ubs.application.codingtask.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OptionEntity extends TradeEntity{

    String style;
    String strategy;
    Date deliveryDate;
    Date expiryDate;
    Date excerciseStartDate;
    String payCcy;
    Double premium;
    String premiumCcy;
    String premiumType;
    Date premiumDate;
}
