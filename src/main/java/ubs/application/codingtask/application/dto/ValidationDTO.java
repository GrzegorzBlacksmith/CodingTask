package ubs.application.codingtask.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

@Value
@NonFinal
@Builder
@AllArgsConstructor
public class ValidationDTO {

    boolean isValid;
    List<String> messages;
}
