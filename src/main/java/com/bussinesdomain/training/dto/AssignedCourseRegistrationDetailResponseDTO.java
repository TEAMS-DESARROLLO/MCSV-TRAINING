package com.bussinesdomain.training.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AssignedCourseRegistrationDetailResponseDTO {
	
	private Long idAssignedCourseRegistrationDetail;
	
	@JsonFormat(pattern = "dd-MM-yyyy")	
	private LocalDate reportDate;
	
	private String themeDescription;
	
	private Integer durationCourseHrs;

	private Integer durationCourseMin;
	
	private String themeOfDay;
	
	private Long idAssignedCourseRegistration;
	
	private String registrationStatus;
	

	private String comment;

}
