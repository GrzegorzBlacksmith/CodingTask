package ubs.application.codingtask.domain.validation;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ValidationResult {

    private boolean isValid = true;
    private List<String> messages = new LinkedList<>();

    public void addMassages(List<String> messages) {
        this.messages.addAll(messages);
        if (!messages.isEmpty()) {
            isValid = false;
        }
    }
}
