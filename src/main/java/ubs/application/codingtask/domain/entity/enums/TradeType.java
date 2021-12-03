package ubs.application.codingtask.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TradeType {
    @JsonProperty("Spot")
    SPOT,
    @JsonProperty("Forward")
    FORWARD,
    @JsonProperty("VanillaOption")
    VANILLA_OPTION
}
