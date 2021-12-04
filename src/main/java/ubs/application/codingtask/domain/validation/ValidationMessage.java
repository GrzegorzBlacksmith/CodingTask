package ubs.application.codingtask.domain.validation;

public class ValidationMessage {

    public static final String CUSTOMER_NOT_SUPPORTED = "Customer not supported.";
    public static final String CURRENCY_PAIR_NOT_VALID_ISO_CODE = "Currency Pair not valid ISO code.";
    public static final String VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE = "Value date cannot be before trade date.";
    public static final String VALUE_DATE_CANNOT_BE_ON_WEEKEND = "Value cannot be on weekend.";
    public static final String PAY_CURRENCY_NOT_VALID_ISO_CODE = "Pay Currency not valid ISO code.";
    public static final String PREMIUM_CURRENCY_NOT_VALID_ISO_CODE = "Premium Currency not valid ISO code.";
    public static final String SUPPORTED_AMERICAN_OR_EUROPEAN_STYLE = "Style can be either American or European.";
    public static final String EXCERCISE_DATE_BETWEEN_TRADE_AND_EXPIRY_DATE = "For American option style excercise start date must be between trade date and expiry date.";
    public static final String EXPIRY_DATE_BEFORE_DELIVERY_DATE = "Expiry date shall be before delivery date.";
    public static final String PREMIUM_DATE_BEFORE_DELIVERY_DATE = "Premium date shall be before delivery date.";

}
