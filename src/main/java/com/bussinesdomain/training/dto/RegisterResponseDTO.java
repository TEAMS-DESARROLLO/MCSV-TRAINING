package com.bussinesdomain.training.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
	


	
	private Long idRegister;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateAdmission;
	

	private Long idCollaborator;

	private String namesCollaborator;

	private String lastnameCollaborator;
	
	private Long idCommunity;

	private String descriptionCommunity;

	private Long idLeader;

	private String namesLeader;
	
	private Long idRegion;

	private String descriptionRegion;

	private Long idFunctionalLeader;

	private String namesFuncionalLeader;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime createdAt;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime updatedAt;
	
	private String registrationStatus;
	
	private Long idUser;




}
