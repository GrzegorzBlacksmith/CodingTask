package ubs.application.codingtask.domain.validation;

import org.springframework.stereotype.Component;
import ubs.application.codingtask.domain.entity.TradeEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Component
public class CommonValidation implements TradeValidation<TradeEntity>{

    static final List<String> SUPPORTED_CUSTOMERS = Arrays.asList("YODA1", "YODA2");

    @Override
    public List<String> validate(TradeEntity entity) {

        List<String> response = new LinkedList<>(validateValueDate(entity));
        validateIsCounterPartyInSupportedCustomerList(entity).ifPresent(response::add);
        validateIsCurrencyPairValidISOCodes(entity).ifPresent(response::add);

        return response;
    }

    List<String> validateValueDate(TradeEntity entity) {
        List<String> messages = new LinkedList<>();
        if (Objects.isNull(entity.getValueDate())) {
            return messages;
        }

        LocalDate tradeDate = entity.getTradeDate();
        if (Objects.nonNull(tradeDate) && entity.getValueDate().isBefore(tradeDate)) {
            messages.add(ValidationMessage.VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE);
        }

        if (isValueDateOnWeekend(entity)) {
            messages.add(ValidationMessage.VALUE_DATE_CANNOT_BE_ON_WEEKEND);
        }
        return messages;
    }

    private boolean isValueDateOnWeekend(TradeEntity entity) {
        DayOfWeek valueDateDay = entity.getValueDate().getDayOfWeek();
        return valueDateDay == DayOfWeek.SATURDAY || valueDateDay == DayOfWeek.SUNDAY;
    }

    Optional<String> validateIsCounterPartyInSupportedCustomerList(TradeEntity entity) {
        Optional<String> message = Optional.empty();
        if (!SUPPORTED_CUSTOMERS.contains(entity.getCustomer())) {
            message = Optional.of(ValidationMessage.CUSTOMER_NOT_SUPPORTED);
        }
        return message;
    }

    Optional<String> validateIsCurrencyPairValidISOCodes(TradeEntity entity) {
        String ccyPair = entity.getCcyPair();
        if (Objects.isNull(ccyPair) || ccyPair.length()!=6) {
            return Optional.of(ValidationMessage.CURRENCY_PAIR_NOT_VALID_ISO_CODE);
        }

        Optional<String> message = Optional.empty();
        String firstCurrency = ccyPair.substring(0,3);
        String secondCurrency = ccyPair.substring(3,6);
        try {
            Currency.getInstance(firstCurrency);
            Currency.getInstance(secondCurrency);
        } catch (IllegalArgumentException e) {
            message = Optional.of(ValidationMessage.CURRENCY_PAIR_NOT_VALID_ISO_CODE);
        }
        return message;
    }
}
