package ubs.application.codingtask.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ubs.application.codingtask.domain.entity.enums.TradeType;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class TradeDTO {
    String customer;
    String ccyPair;
    TradeType type;
    String direction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date tradeDate;
    Double amount1;
    Double amount2;
    Double rate;
    String legalEntity;
    String trader;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date valueDate;
}
