package ubs.application.codingtask.domain.validation;

import org.springframework.stereotype.Component;
import ubs.application.codingtask.domain.entity.OptionEntity;
import ubs.application.codingtask.domain.entity.enums.Style;

import java.time.LocalDate;
import java.util.*;

@Component
public class OptionValidation implements TradeValidation<OptionEntity>{

    @Override
    public List<String> validate(OptionEntity entity) {
        List<String> response = new LinkedList<>();

        if (isInvalidISOCode(entity.getPayCcy())) {
            response.add(ValidationMessage.PAY_CURRENCY_NOT_VALID_ISO_CODE);
        }
        if (isInvalidISOCode(entity.getPremiumCcy())) {
            response.add(ValidationMessage.PREMIUM_CURRENCY_NOT_VALID_ISO_CODE);
        }

        if (!isSupportedOptionStyle(entity)) {
            response.add(ValidationMessage.SUPPORTED_AMERICAN_OR_EUROPEAN_STYLE);
        }

        if (Style.AMERICAN.equals(Style.valueOf(entity.getStyle()))) {
            if (!isExerciseStartDateBetweenExpiryAndTradeDate(entity)) {
                response.add(ValidationMessage.EXCERCISE_DATE_BETWEEN_TRADE_AND_EXPIRY_DATE);
            }
        }

        if (!entity.getExpiryDate().isBefore(entity.getDeliveryDate())) {
            response.add(ValidationMessage.EXPIRY_DATE_BEFORE_DELIVERY_DATE);
        }

        if (!entity.getPremiumDate().isBefore(entity.getDeliveryDate())) {
            response.add(ValidationMessage.PREMIUM_DATE_BEFORE_DELIVERY_DATE);
        }

        return response;
    }

    private boolean isSupportedOptionStyle(OptionEntity entity) {
        return Arrays.asList(Style.values()).contains(Style.valueOf(entity.getStyle()));
    }

    private boolean isInvalidISOCode(String currency) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

    private boolean isExerciseStartDateBetweenExpiryAndTradeDate(OptionEntity entity) {
        LocalDate exerciseStartDate = entity.getExcerciseStartDate();
        if (Objects.isNull(exerciseStartDate)) {
            return false;
        }
        return exerciseStartDate.isAfter(entity.getTradeDate()) &&
                exerciseStartDate.isBefore(entity.getExpiryDate());
    }
}
