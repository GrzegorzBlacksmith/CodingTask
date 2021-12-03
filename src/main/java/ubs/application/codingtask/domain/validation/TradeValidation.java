package ubs.application.codingtask.domain.validation;

import java.util.List;

public interface TradeValidation<T> {

    List<String> validate (T trade);
}
