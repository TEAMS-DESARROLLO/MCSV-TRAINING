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
import com.bussinesdomain.training.dto.AssignedCourseRegistrationRequestDTO;
import com.bussinesdomain.training.dto.AssignedCourseRegistrationResponseDTO;
import com.bussinesdomain.training.mapper.IAssignedCourseRegistrationMapper;
import com.bussinesdomain.training.models.AssignedCourseRegistration;
import com.bussinesdomain.training.services.IAssignedCourseRegistrationService;
import com.bussinesdomain.training.services.impl.AssignedCourseRegistrationPaginationServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assignedCourseRegistration")

public class AssignedCourseRegistrationController {

	private final IAssignedCourseRegistrationService service;
	private final AssignedCourseRegistrationPaginationServiceImpl servicePagination;
	private final IAssignedCourseRegistrationMapper iPaqueteMapper;

    //private final IAssignedCourseRegistrationService iAssignedCourseRegistrationService;


	@PostMapping("/pagination")
	public ResponseEntity<?> paginador(@RequestBody PaginationModel pagination) {
		log.info("PAGINATION ....." + pagination);
		Page<AssignedCourseRegistrationResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}


	@PostMapping("/create")
	public ResponseEntity<AssignedCourseRegistrationResponseDTO> save(@RequestBody @Valid AssignedCourseRegistrationRequestDTO dto) {

        Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    

		 AssignedCourseRegistration entidad = this.iPaqueteMapper.toEntity(dto);
         entidad.setIdUser(idUsuario);

		AssignedCourseRegistration entity = this.service.create(entidad);
		AssignedCourseRegistrationResponseDTO assignedCourseRegistrationDTO = this.iPaqueteMapper.toGetDTO(entity);
		return new ResponseEntity<>(assignedCourseRegistrationDTO, HttpStatus.CREATED);
	}

	
	@GetMapping("/all")
	public ResponseEntity<List<AssignedCourseRegistrationResponseDTO>> findByFiltro() {
		List<AssignedCourseRegistration> entities = this.service.getAll();
		List<AssignedCourseRegistrationResponseDTO> entitiesDTO = this.iPaqueteMapper.listEntityToDto(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<AssignedCourseRegistrationResponseDTO> findById(@PathVariable("id") Long id) {
		AssignedCourseRegistration entity = this.service.readById(id);
		AssignedCourseRegistrationResponseDTO dto = this.iPaqueteMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

    @GetMapping("/followUp/{idFollowUp}")
    public ResponseEntity<List<AssignedCourseRegistrationResponseDTO>> findByFollowUpId(@PathVariable("idFollowUp") Long idFollowUp) {
        List<AssignedCourseRegistration> entities = this.service.findByFollowUpId(idFollowUp);
        List<AssignedCourseRegistrationResponseDTO> entitiesDTO = this.iPaqueteMapper.listEntityToDto(entities);
        return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
    }


	@PutMapping("/{id}")
	public ResponseEntity<AssignedCourseRegistrationResponseDTO> update(@PathVariable("id") Long id,@RequestBody @Valid AssignedCourseRegistrationRequestDTO dto) {
		
		
		AssignedCourseRegistration entity = this.iPaqueteMapper.toEntity(dto);
		AssignedCourseRegistration entityUpdated = service.update(entity, id);
		return new ResponseEntity<>(this.iPaqueteMapper.toGetDTO(entityUpdated), HttpStatus.OK);
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
