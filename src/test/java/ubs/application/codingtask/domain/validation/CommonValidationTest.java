package ubs.application.codingtask.domain.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ubs.application.codingtask.CodingTaskApplication;
import ubs.application.codingtask.domain.entity.TradeEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CodingTaskApplication.class)
public class CommonValidationTest {

    @Autowired
    private CommonValidation commonValidation;

    @Test
    void testCorrectValueDate() {
        //given
        TradeEntity tradeEntity = TradeEntity.builder()
                .valueDate(LocalDate.parse("2021-02-12"))
                .tradeDate(LocalDate.parse("2021-02-10"))
                .build();
        //when
        List<String> validationResponses = commonValidation.validateValueDate(tradeEntity);
        //then
        assertThat(validationResponses).isEmpty();
    }

    @Test
    void testValueDateBeforeTradeDate() {
        //given
        TradeEntity tradeEntity = TradeEntity.builder()
                .valueDate(LocalDate.parse("2021-02-12"))
                .tradeDate(LocalDate.parse("2021-04-10"))
                .build();

        //when
        List<String> validationResponses = commonValidation.validateValueDate(tradeEntity);

        //then
        assertThat(validationResponses).contains(ValidationMessage.VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE);
    }

    @Test
    void testValueDateOnWeekend() {
        //given
        TradeEntity tradeEntity = TradeEntity.builder()
                .valueDate(LocalDate.parse("2021-02-14"))
                .build();

        //when
        List<String> validationResponses = commonValidation.validateValueDate(tradeEntity);

        //then
        assertThat(validationResponses).contains(ValidationMessage.VALUE_DATE_CANNOT_BE_ON_WEEKEND);
    }

    @Test
    void testCounterPartyInSupportedCustomerList() {
        //given
        String customer = CommonValidation.SUPPORTED_CUSTOMERS.stream().findAny().orElse("UNSUPPORTED");
        TradeEntity tradeEntity = TradeEntity.builder()
                .customer(customer)
                .build();

        //when
        Optional<String> validationResponses = commonValidation.validateIsCounterPartyInSupportedCustomerList(tradeEntity);

        //then
        assertThat(validationResponses).isEmpty();
    }

    @Test
    void testCounterPartyNOTInSupportedCustomerList() {
        //given
        TradeEntity tradeEntity = TradeEntity.builder()
                .customer("NOTEXISTING")
                .build();

        //when
        Optional<String> validationResponses = commonValidation.validateIsCounterPartyInSupportedCustomerList(tradeEntity);

        //then
        assertThat(validationResponses).isNotEmpty();
        assertThat(validationResponses.get()).isEqualTo(ValidationMessage.CUSTOMER_NOT_SUPPORTED);
    }

    @Test
    void testCurrencyValidISOCode() {
        //given
        TradeEntity tradeEntity = TradeEntity.builder()
                .ccyPair("EURUSD")
                .build();

        //when
        Optional<String> validationResponses = commonValidation.validateIsCurrencyPairValidISOCodes(tradeEntity);

        //then
        assertThat(validationResponses).isEmpty();
    }

    @Test
    void testCurrencyNotValidISOCode() {
        //given
        TradeEntity tradeEntity = TradeEntity.builder()
                .ccyPair("ALTURE")
                .build();

        //when
        Optional<String> validationResponses = commonValidation.validateIsCurrencyPairValidISOCodes(tradeEntity);

        //then
        assertThat(validationResponses).isNotEmpty();
        assertThat(validationResponses.get()).isEqualTo(ValidationMessage.CURRENCY_PAIR_NOT_VALID_ISO_CODE);
    }

    @Test
    void testCurrencyPairToLong() {
        //given
        TradeEntity tradeEntity = TradeEntity.builder()
                .ccyPair("ALTURETT")
                .build();

        //when
        Optional<String> validationResponses = commonValidation.validateIsCurrencyPairValidISOCodes(tradeEntity);

        //then
        assertThat(validationResponses).isNotEmpty();
        assertThat(validationResponses.get()).isEqualTo(ValidationMessage.CURRENCY_PAIR_NOT_VALID_ISO_CODE);
    }
}
