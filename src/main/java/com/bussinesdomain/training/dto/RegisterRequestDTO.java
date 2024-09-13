package com.bussinesdomain.training.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {


	private Long idCollaborator;

	private String namesCollaborator;
	private String lastnameCollaborator;
	
	private Long idLeader;
	private String namesLeader;
	
	private Long idRegion;
	private String descriptionRegion;

	private Long idCommunity;
	private String descriptionCommunity;

	private Long idFunctionalLeader;
	private String namesFunctionalLeader;
	
	//@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateAdmission;
		
	

}
