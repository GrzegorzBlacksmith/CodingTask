package ubs.application.codingtask.application.mapper;

import org.mapstruct.Mapper;
import ubs.application.codingtask.application.dto.*;
import ubs.application.codingtask.domain.entity.ForwardEntity;
import ubs.application.codingtask.domain.entity.OptionEntity;
import ubs.application.codingtask.domain.entity.SpotEntity;
import ubs.application.codingtask.domain.validation.ValidationResult;

@Mapper(componentModel = "spring")
public interface DTOMapper {

    SpotEntity mapToSpotEntity(InputDTO inputDto);

    ForwardEntity mapToForwardEntity(InputDTO inputDto);

    OptionEntity mapToOptionEntity(InputDTO inputDTO);

    SpotDTO mapSpotToDto(SpotEntity entity);

    ForwardDTO mapForwardToDto(ForwardEntity entity);

    OptionDTO mapOptionToDto(OptionEntity entity);

    ValidationDTO mapToValidationDto(ValidationResult result);
}
