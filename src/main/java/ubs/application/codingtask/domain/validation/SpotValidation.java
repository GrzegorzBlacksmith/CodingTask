package ubs.application.codingtask.domain.validation;

import org.springframework.stereotype.Component;
import ubs.application.codingtask.domain.entity.SpotEntity;

import java.util.LinkedList;
import java.util.List;

@Component
public class SpotValidation implements TradeValidation<SpotEntity>{

    @Override
    public List<String> validate(SpotEntity entity) {

        //additional validations for spot trades
        return new LinkedList<>();
    }
}
