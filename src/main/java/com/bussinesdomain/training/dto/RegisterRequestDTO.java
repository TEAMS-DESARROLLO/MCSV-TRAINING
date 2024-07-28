package com.bussinesdomain.training.dto;

import java.time.LocalDateTime;

import com.bussinesdomain.training.constants.ValidationMessage;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegisterRequestDTO {

	@EqualsAndHashCode.Include
	private Long idRegister;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime dateAdmission;
	
	@NotNull(message = ValidationMessage.CAN_T_BE_NULL)
	@Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	private Long idCollaborator;
	
	@NotNull(message = ValidationMessage.CAN_T_BE_NULL)
	@Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	private Long idLeader;
	
	@NotNull(message = ValidationMessage.CAN_T_BE_NULL)
	@Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	private Long idLeaderRegion;
	
	private String registrationStatus;
	
	private Long idUser;
}
