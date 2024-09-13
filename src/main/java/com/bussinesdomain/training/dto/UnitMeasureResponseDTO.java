package com.bussinesdomain.training.dto;

import com.bussinesdomain.training.constants.ValidationMessage;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UnitMeasureResponseDTO {

	@EqualsAndHashCode.Include
	@NotNull(message = ValidationMessage.CAN_T_BE_NULL)
	private Long idUnitMeasure;

	private String name;

}
