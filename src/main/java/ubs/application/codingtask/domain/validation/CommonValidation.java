package ubs.application.codingtask.domain.validation;

import org.springframework.stereotype.Component;
import ubs.application.codingtask.domain.entity.TradeEntity;

import java.util.*;

@Component
public class CommonValidation implements TradeValidation<TradeEntity>{

    private static final List<String> supportedCustomers = Arrays.asList("YODA1", "YODA2");

    @Override
    public List<String> validate(TradeEntity entity) {
        List<String> response = new LinkedList<>();

        validateValueDate(entity, response);

        if (!isCounterPartyInSupportedCustomerList(entity)) {
            response.add("Customer not supported");
        }

        if (isCurrencyPairValidISOCodes(entity)) {
            response.add("Currency Pair not valid ISO code");
        }

        return response;
    }

    private void validateValueDate(TradeEntity entity, List<String> response) {
        if (Objects.isNull(entity.getValueDate())) {
            return;
        }

        if (entity.getValueDate().before(entity.getTradeDate())) {
            response.add("Value Date cannot be before trade date");
        }

        if (isValueDateOnWeekend(entity)) {
            response.add("Value date cannot be on weekend");
        }
    }

    private boolean isValueDateOnWeekend(TradeEntity entity) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(entity.getValueDate());
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    private boolean isCounterPartyInSupportedCustomerList(TradeEntity entity) {
        return supportedCustomers.contains(entity.getCustomer());
    }

    private boolean isCurrencyPairValidISOCodes(TradeEntity entity) {
        String firstCurrency = entity.getCcyPair().substring(0,3);
        String secondCurrency = entity.getCcyPair().substring(3,4);
        try {
            Currency.getInstance(firstCurrency);
            Currency.getInstance(secondCurrency);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
