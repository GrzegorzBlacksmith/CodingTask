package ubs.application.codingtask.application.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubs.application.codingtask.application.dto.ResponseElementDto;
import ubs.application.codingtask.application.dto.InputDTO;
import ubs.application.codingtask.application.dto.TradeDTO;
import ubs.application.codingtask.application.dto.ValidationDTO;
import ubs.application.codingtask.application.mapper.DTOMapper;
import ubs.application.codingtask.domain.entity.ForwardEntity;
import ubs.application.codingtask.domain.entity.OptionEntity;
import ubs.application.codingtask.domain.entity.SpotEntity;
import ubs.application.codingtask.domain.entity.TradeEntity;
import ubs.application.codingtask.domain.entity.enums.TradeType;
import ubs.application.codingtask.domain.service.ValidationService;
import ubs.application.codingtask.domain.validation.ValidationResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "trade", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TradesResource {

    private final DTOMapper mapper;
    private final ValidationService validationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseElementDto>> validateTrade(@RequestBody List<InputDTO> inputDtos) {

        HashMap<TradeType, List<? extends TradeEntity>> entitiesByType = new HashMap<>();

        List<SpotEntity> spotEntities = inputDtos.stream()
                .filter(trade -> TradeType.SPOT.equals(trade.getType()))
                .map(mapper::mapToSpotEntity)
                .collect(Collectors.toList());
        entitiesByType.put(TradeType.SPOT, spotEntities);

        List<ForwardEntity> forwardEntities = inputDtos.stream()
                .filter(trade -> TradeType.FORWARD.equals(trade.getType()))
                .map(mapper::mapToForwardEntity)
                .collect(Collectors.toList());
        entitiesByType.put(TradeType.FORWARD, forwardEntities);

        List<OptionEntity> optionEntities = inputDtos.stream()
                .filter(trade -> TradeType.VANILLA_OPTION.equals(trade.getType()))
                .map(mapper::mapToOptionEntity)
                .collect(Collectors.toList());
        entitiesByType.put(TradeType.VANILLA_OPTION, optionEntities);

        Map<TradeEntity, ValidationResult> validationsMap = validationService.validateTrades(entitiesByType);

        Map<TradeType, Function<TradeEntity, TradeDTO>> mappersMap = new HashMap<>();
        mappersMap.put(TradeType.SPOT, (trade) -> mapper.mapSpotToDto((SpotEntity) trade));
        mappersMap.put(TradeType.FORWARD, (trade) -> mapper.mapForwardToDto((ForwardEntity) trade));
        mappersMap.put(TradeType.VANILLA_OPTION, (trade) -> mapper.mapOptionToDto((OptionEntity) trade));

        List<ResponseElementDto> responseBody = validationsMap.entrySet().stream()
                .map(entry -> createResponseElement(mappersMap, entry))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseBody);
    }

    private ResponseElementDto createResponseElement(Map<TradeType, Function<TradeEntity, TradeDTO>> mappersMap, Map.Entry<TradeEntity, ValidationResult> entry) {
        TradeEntity tradeEntity = entry.getKey();
        TradeDTO tradeDto = mappersMap.get(tradeEntity.getType()).apply(tradeEntity);
        ValidationDTO validationDto = mapper.mapToValidationDto(entry.getValue());
        return new ResponseElementDto(tradeDto, validationDto);
    }
}
