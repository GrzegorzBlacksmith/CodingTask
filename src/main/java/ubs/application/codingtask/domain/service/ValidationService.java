package ubs.application.codingtask.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ubs.application.codingtask.domain.entity.ForwardEntity;
import ubs.application.codingtask.domain.entity.OptionEntity;
import ubs.application.codingtask.domain.entity.SpotEntity;
import ubs.application.codingtask.domain.entity.TradeEntity;
import ubs.application.codingtask.domain.entity.enums.TradeType;
import ubs.application.codingtask.domain.validation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final CommonValidation basicValidation;
    private final SpotValidation spotValidation;
    private final ForwardValidation forwardValidation;
    private final OptionValidation optionValidation;

    public Map<TradeEntity, ValidationResult> validateTrades(HashMap<TradeType, List<? extends TradeEntity>> trades) {

        Map<TradeEntity, ValidationResult> validationsMap = new HashMap<>();

        trades.get(TradeType.SPOT)
                .forEach(trade -> validationsMap.put(trade, validateSpotEntity(trade)));
        trades.get(TradeType.FORWARD)
                .forEach(trade -> validationsMap.put(trade, validateForwardEntity(trade)));
        trades.get(TradeType.VANILLA_OPTION)
                .forEach(trade -> validationsMap.put(trade, validateOptionEntity(trade)));

        return validationsMap;
    }

    private ValidationResult validateSpotEntity(TradeEntity trade) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addMassages(basicValidation.validate(trade));
        validationResult.addMassages(spotValidation.validate((SpotEntity) trade));
        return validationResult;
    }

    private ValidationResult validateForwardEntity(TradeEntity trade) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addMassages(basicValidation.validate(trade));
        validationResult.addMassages(forwardValidation.validate((ForwardEntity) trade));
        return validationResult;
    }

    private ValidationResult validateOptionEntity(TradeEntity trade) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addMassages(basicValidation.validate(trade));
        validationResult.addMassages(optionValidation.validate((OptionEntity) trade));
        return validationResult;
    }
}
