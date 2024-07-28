package com.bussinesdomain.training.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailRequestDTO;
import com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailResponseDTO;
import com.bussinesdomain.training.models.AssignedCourseRegistrationDetail;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IAssignedCourseRegistrationDetailMapper {

	@Mapping(source = "idAssignedCourseRegistration.idAssignedCourseRegistration", target = "idAssignedCourseRegistration")
	AssignedCourseRegistrationDetailResponseDTO toGetDTO(AssignedCourseRegistrationDetail assignedCourseRegistrationDetail);

	@InheritInverseConfiguration
	@Mapping(source = "idAssignedCourseRegistration", target = "idAssignedCourseRegistration.idAssignedCourseRegistration")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target  = "updatedAt", ignore = true)
	AssignedCourseRegistrationDetail toEntity(AssignedCourseRegistrationDetailRequestDTO assignedCourseRegistrationDetailRequestDTO);

	List<AssignedCourseRegistrationDetailResponseDTO> listEntityToDto(List<AssignedCourseRegistrationDetail> assignedCourseRegistrationDetailsEntities);

}
