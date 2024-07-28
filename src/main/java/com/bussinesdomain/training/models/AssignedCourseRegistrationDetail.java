package com.bussinesdomain.training.models;

import java.time.LocalDateTime;

import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.constants.ValidationMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "assigned_course_registration_detail")
public class AssignedCourseRegistrationDetail {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@SequenceGenerator(name = "sequenceAssignedCourseRegistrationDetail", sequenceName = "assigned_course_registration_detail_seq", allocationSize = 1)
	@Column(name = "id_assigned_course_registration_detail")
	private Long idAssignedCourseRegistrationDetail;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "report_date")
	private LocalDateTime reportDate;
	
	@Column(name="theme_description")
	private String themeDescription;
	
	@Column(name="theme_duration")
	private Integer themeDuration;
	
	@Column(name="ttheme_of_day")
	private String themeOfDay;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_assigned_course_registration")
	private AssignedCourseRegistration idAssignedCourseRegistration;
	
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
