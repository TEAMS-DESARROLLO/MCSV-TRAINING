package com.bussinesdomain.training.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.bussinesdomain.training.dto.RegisterFollowRequestDTO;
import com.bussinesdomain.training.dto.RegisterFollowResponseDTO;
import com.bussinesdomain.training.models.RegisterFollow;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRegisterFollowMapper {
	
	@Mapping(source = "idRegister.idRegister", target = "idRegister")
	RegisterFollowResponseDTO toGetDTO(RegisterFollow registerFollow);
	
	@InheritInverseConfiguration	
	@Mapping(source = "idRegister", target = "idRegister.idRegister")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target  = "updatedAt", ignore = true)
	RegisterFollow toEntity(RegisterFollowRequestDTO registerFollowRequestDTO);
	
	List<RegisterFollowResponseDTO> listEntityToDTO(List<RegisterFollow> registerFollows);

}
