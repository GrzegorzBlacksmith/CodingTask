package ubs.application.codingtask.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ubs.application.codingtask.domain.entity.enums.TradeType;

import java.time.LocalDate;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public class TradeEntity {
    String customer;
    String ccyPair;
    TradeType type;
    String direction;
    LocalDate tradeDate;
    Double amount1;
    Double amount2;
    Double rate;
    String legalEntity;
    String trader;
    LocalDate valueDate;
}
