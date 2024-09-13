package com.bussinesdomain.training.dto;

import java.time.LocalDate;

import com.bussinesdomain.training.constants.ValidationMessage;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedCourseRegistrationDetailRequestDTO {

	private Long idAssignedCourseRegistration;
	
	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String themeOfDay;

	@NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
	private String comment;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate reportDate;
		
	private Integer durationCourseHrs;

	private Integer durationCourseMin;


}
