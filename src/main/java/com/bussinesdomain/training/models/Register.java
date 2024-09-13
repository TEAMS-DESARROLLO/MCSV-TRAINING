package com.bussinesdomain.training.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.constants.ValidationMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "register")
public class Register {

	@Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
	@SequenceGenerator(name = "sequenceRegister", sequenceName = "register_seq", allocationSize = 1)
    @Column( name = "id_register" )
    private Long idRegister;

	
    @Column( name = "date_admission" ,nullable = false)
    private LocalDate dateAdmission;

    @NotNull( message = ValidationMessage.CAN_T_BE_NULL )
    @Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	@Column( name = "id_collaborator", nullable = false )
    private Long idCollaborator;

    @Column( name = "names_collaborator", nullable = false )
    private String namesCollaborator;

    @Column( name = "lastname_collaborator", nullable = false )
    private String lastnameCollaborator;
	
    @NotNull( message = ValidationMessage.CAN_T_BE_NULL )
    @Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
    @Column( name = "id_leader", nullable = false )
    private Long idLeader;

    @Column( name = "names_leader", nullable = false )
    private String namesLeader;
    
    @NotNull( message = ValidationMessage.CAN_T_BE_NULL )
    @Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
    @Column( name = "id_region" )
    private Long idRegion;

    @Column( name = "description_region"  )
    private String descriptionRegion;    

    @Column( name = "id_community"  )
    private Long idCommunity;

    @Column( name = "description_community" )
    private String descriptionCommunity;

    @Column( name = "id_functional_leader" )
    private Long idFunctionalLeader;

    @Column( name = "names_functional_leader" )
    private String namesFunctionalLeader;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;
    

    @Column(name="updated_at",nullable = true)
    private LocalDateTime updatedAt;
    
    @Column(name="registration_status ", nullable=false,length = 1)
    private String registrationStatus;
    
    @NotNull( message = ValidationMessage.CAN_T_BE_NULL )
    @Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
    @Column( name="id_user", nullable = false )
    private Long idUser;
    
    @PrePersist
    public void prePersistence(){
        this.createdAt=LocalDateTime.now();
        this.registrationStatus = RegistrationStatus.ACTIVE;
    }
    
    @PreUpdate
    public void preModify(){
        this.updatedAt = LocalDateTime.now();
    }

}
