package ubs.application.codingtask.domain.validation;

import org.springframework.stereotype.Component;
import ubs.application.codingtask.domain.entity.OptionEntity;
import ubs.application.codingtask.domain.entity.enums.Style;

import java.util.*;

@Component
public class OptionValidation implements TradeValidation<OptionEntity>{

    @Override
    public List<String> validate(OptionEntity entity) {
        List<String> response = new LinkedList<>();

        if (isInvalidISOCode(entity.getPayCcy())) {
            response.add("Pay Currency not valid ISO code");
        }
        if (isInvalidISOCode(entity.getPremiumCcy())) {
            response.add("Premium Currency not valid ISO code");
        }

        if (!isSupportedOptionStyle(entity)) {
            response.add("Style can be either American or European");
        }

        if (Style.AMERICAN.equals(Style.valueOf(entity.getStyle()))) {
            if (!isExerciseStartDateBetweenExpiryAndTradeDate(entity)) {
                response.add("For American option style exercise start date must be between trade date and expiry date");
            }
        }

        if (!entity.getExpiryDate().before(entity.getDeliveryDate())) {
            response.add("Expiry date shall be before delivery date");
        }

        if (!entity.getPremiumDate().before(entity.getDeliveryDate())) {
            response.add("Premium date shall be before delivery date");
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
        Date exerciseStartDate = entity.getExcerciseStartDate();
        if (Objects.isNull(exerciseStartDate)) {
            return false;
        }
        return exerciseStartDate.after(entity.getTradeDate()) &&
                exerciseStartDate.before(entity.getExpiryDate());
    }
}
