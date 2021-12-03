package ubs.application.codingtask.domain.validation;

import org.springframework.stereotype.Component;
import ubs.application.codingtask.domain.entity.ForwardEntity;

import java.util.LinkedList;
import java.util.List;

@Component
public class ForwardValidation implements TradeValidation<ForwardEntity>{

    @Override
    public List<String> validate(ForwardEntity entity) {
        //additional validation for forward trades
        return new LinkedList<>();
    }

}
