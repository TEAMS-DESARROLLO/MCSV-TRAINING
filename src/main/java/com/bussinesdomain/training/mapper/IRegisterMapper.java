package com.bussinesdomain.training.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.bussinesdomain.training.dto.RegisterRequestDTO;
import com.bussinesdomain.training.dto.RegisterResponseDTO;
import com.bussinesdomain.training.models.Register;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRegisterMapper {

	RegisterResponseDTO toGetDTO(Register register);
	
	@InheritInverseConfiguration
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target  = "updatedAt", ignore = true)
	Register toEntity(RegisterRequestDTO registerRequestDTO);
	
	List<RegisterResponseDTO> listEntityToDTO(List<Register> registerEntities);
}
