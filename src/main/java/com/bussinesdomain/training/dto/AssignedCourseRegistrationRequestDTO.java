package com.bussinesdomain.training.dto;

import java.time.LocalDate;

import com.bussinesdomain.training.constants.ValidationMessage;
import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssignedCourseRegistrationRequestDTO {
	
	
	private Integer advancePercentage;
	
	
	@Min( value = 1, message = ValidationMessage.GREATER_THAN_CERO )
	private Integer durationCourse;
	
	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String nameCourse;
	
	// @NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    // @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String observation;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	
	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String urlCourse;
	

	private Long idRegisterFollow;
	

}
