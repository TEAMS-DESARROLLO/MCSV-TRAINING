package com.bussinesdomain.training.models;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

	@Temporal(TemporalType.TIMESTAMP)
    @Column( name = "date_admission" ,nullable = false)
    private LocalDateTime dateAdmission;

    @NotNull( message = ValidationMessage.CAN_T_BE_NULL )
    @Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
	@Column( name = "id_collaborator", nullable = false )
    private Long idCollaborator;
	
    @NotNull( message = ValidationMessage.CAN_T_BE_NULL )
    @Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
    @Column( name = "id_leader", nullable = false )
    private Long idLeader;
    
    @NotNull( message = ValidationMessage.CAN_T_BE_NULL )
    @Min( value = 1, message = ValidationMessage.GREATER_THAN_ONE )
    @Column( name = "id_leader_region", nullable = false )
    private Long idLeaderRegion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
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
