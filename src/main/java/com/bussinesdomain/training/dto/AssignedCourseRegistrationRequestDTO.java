package com.bussinesdomain.training.dto;

import java.time.LocalDateTime;

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
public class AssignedCourseRegistrationRequestDTO {
	
	@EqualsAndHashCode.Include
	private Long idAssignedCourseRegistration;
	
	private Integer advancePercentage;
	
	private Integer durationCourse;
	
	@NotNull(message = ValidationMessage.CAN_T_BE_NULL)
	@Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	private Long idUnitMeasure;
	
	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String nameCourse;
	
	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String observation;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime startDate;
	
	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String urlCourse;
	
	@NotNull(message = ValidationMessage.CAN_T_BE_NULL)
	@Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	private Long idRegisterFollow;
		
	private String registrationStatus;
	
	private Long idUser;
}
