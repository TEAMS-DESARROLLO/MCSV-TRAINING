package com.bussinesdomain.training.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bussinesdomain.training.commons.PaginationModel;
import com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailRequestDTO;
import com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailResponseDTO;
import com.bussinesdomain.training.mapper.IAssignedCourseRegistrationDetailMapper;
import com.bussinesdomain.training.models.AssignedCourseRegistration;
import com.bussinesdomain.training.models.AssignedCourseRegistrationDetail;
import com.bussinesdomain.training.services.IAssignedCourseRegistrationDetailService;
import com.bussinesdomain.training.services.IAssignedCourseRegistrationService;
import com.bussinesdomain.training.services.impl.AssignedCourseRegistrationDetailPaginationServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assignedCourseRegistrationDetail")
public class AssignedCourseRegistrationDetailController {

	private final IAssignedCourseRegistrationDetailService service;
	private final AssignedCourseRegistrationDetailPaginationServiceImpl servicePagination;
	private final IAssignedCourseRegistrationDetailMapper iPaqueteMapper;
	private final IAssignedCourseRegistrationService iAssignedCourseRegistrationService;

	
	@PostMapping("/pagination")
	public ResponseEntity<?> paginador(@RequestBody PaginationModel pagination) {
		log.info("PAGINATION ....." + pagination);
		Page<AssignedCourseRegistrationDetailResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	

	@PostMapping("/create")
	public ResponseEntity<AssignedCourseRegistrationDetailResponseDTO> save( @RequestBody @Valid AssignedCourseRegistrationDetailRequestDTO dto) {

		Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    
		
		Long idAssinedCourseRegistration = dto.getIdAssignedCourseRegistration();
		AssignedCourseRegistration assignedCourseRegistration = iAssignedCourseRegistrationService.readById(idAssinedCourseRegistration);


		AssignedCourseRegistrationDetail entidad = this.iPaqueteMapper.toEntity(dto);
		entidad.setAssignedCourseRegistration(assignedCourseRegistration);
		
		entidad.setIdUser(idUsuario);

		AssignedCourseRegistrationDetail entidadCreate = service.create(entidad);
		AssignedCourseRegistrationDetailResponseDTO assignedCourseRegistrationDetailDTO = iPaqueteMapper.toGetDTO(entidadCreate);
		return new ResponseEntity<>(assignedCourseRegistrationDetailDTO, HttpStatus.CREATED);
	}

	

	@GetMapping("/all")
	public ResponseEntity<List<AssignedCourseRegistrationDetailResponseDTO>> findByFiltro() {
		List<AssignedCourseRegistrationDetail> entities = service.getAll();
		List<AssignedCourseRegistrationDetailResponseDTO> entitiesDTO = this.iPaqueteMapper.listEntityToDto(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<AssignedCourseRegistrationDetailResponseDTO> findById(@PathVariable("id") Long id) {
		AssignedCourseRegistrationDetail entity = service.readById(id);
		AssignedCourseRegistrationDetailResponseDTO dto = iPaqueteMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/AssignedCourseRegistration/{idAssignedCourseRegistration}")
	public ResponseEntity<List<AssignedCourseRegistrationDetailResponseDTO>> findByIdAssignedCourseRegistration(@PathVariable("idAssignedCourseRegistration") Long id) {
		List<AssignedCourseRegistrationDetail> entitys = service.findByAssignedCourseRegistrationId(id);
		List<AssignedCourseRegistrationDetailResponseDTO> dto = iPaqueteMapper.listEntityToDto(entitys);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}


	@PutMapping("/{id}")
	public ResponseEntity<AssignedCourseRegistrationDetailResponseDTO> update(@PathVariable("id") Long id,@RequestBody @Valid AssignedCourseRegistrationDetailRequestDTO dto) {

		AssignedCourseRegistration assignedCourseRegistration = this.iAssignedCourseRegistrationService.readById(dto.getIdAssignedCourseRegistration());

		Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    

		AssignedCourseRegistrationDetail entity = this.iPaqueteMapper.toEntity(dto);
		entity.setIdUser(idUsuario);
		entity.setAssignedCourseRegistration(assignedCourseRegistration);
		AssignedCourseRegistrationDetail entityUpdated = service.update(entity, id);
		return new ResponseEntity<>(this.iPaqueteMapper.toGetDTO(entityUpdated), HttpStatus.OK);
	}

	

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
