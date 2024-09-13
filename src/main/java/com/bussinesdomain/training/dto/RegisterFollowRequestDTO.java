package com.bussinesdomain.training.dto;

import java.time.LocalDate;


import com.bussinesdomain.training.constants.ValidationMessage;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegisterFollowRequestDTO {
	
	@EqualsAndHashCode.Include
	private Long idRegisterFollow;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateStartFollow;
		
	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String observation;
	
	@NotNull(message = ValidationMessage.CAN_T_BE_NULL)
	@Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	private Long idRegister;
	
	private String registrationStatus;
	
	private Long idUser;
	
}
