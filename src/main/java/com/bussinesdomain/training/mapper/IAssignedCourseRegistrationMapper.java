package com.bussinesdomain.training.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.bussinesdomain.training.dto.AssignedCourseRegistrationRequestDTO;
import com.bussinesdomain.training.dto.AssignedCourseRegistrationResponseDTO;
import com.bussinesdomain.training.models.AssignedCourseRegistration;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IAssignedCourseRegistrationMapper {

	@Mapping(source = "registerFollow.idRegisterFollow", target = "idRegisterFollow")
	AssignedCourseRegistrationResponseDTO toGetDTO(AssignedCourseRegistration assignedCourseRegistrationEntity);
	
	@InheritInverseConfiguration
	@Mapping(source = "idRegisterFollow", target = "registerFollow.idRegisterFollow")
	@Mapping(source = "durationCourse", target = "durationCourseHrs")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target  = "updatedAt", ignore = true)
	AssignedCourseRegistration toEntity(AssignedCourseRegistrationRequestDTO assignedCourseRegistrationEntityDTO);
	
	List<AssignedCourseRegistrationResponseDTO> listEntityToDto(List<AssignedCourseRegistration> assignedCourseRegistrationEntitys);

	
}
