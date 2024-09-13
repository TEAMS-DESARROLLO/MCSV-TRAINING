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
import com.bussinesdomain.training.dto.RegisterRequestDTO;
import com.bussinesdomain.training.dto.RegisterResponseDTO;
import com.bussinesdomain.training.exception.ServiceException;
import com.bussinesdomain.training.mapper.IRegisterMapper;
import com.bussinesdomain.training.models.Register;
import com.bussinesdomain.training.services.IRegisterService;
import com.bussinesdomain.training.services.impl.RegisterPaginationServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegisterController {

	private final IRegisterService service;

	private final RegisterPaginationServiceImpl servicePagination;

	private final IRegisterMapper iRegisterMapper;

	
	

	@PostMapping("/pagination")
	public ResponseEntity<?> paginator(@RequestBody PaginationModel pagination) {
		log.info("PAGINATION ..... " + pagination);
		Page<RegisterResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	

	@PostMapping("/create")
	public ResponseEntity<RegisterResponseDTO> save(@RequestBody RegisterRequestDTO dto) {
		
		try {
			Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    
			Register entitySave = this.iRegisterMapper.toEntity(dto);
			entitySave.setIdUser(idUsuario);    
			Register entity = this.service.create(entitySave);
			RegisterResponseDTO registerDTO = this.iRegisterMapper.toGetDTO(entity);
			return new ResponseEntity<>(registerDTO, HttpStatus.CREATED);
			
		} catch (Exception e) {
			throw new ServiceException("Error when saving the registration " + e.getMessage(), e.getCause());
		}
	}


	@GetMapping("/all")
	public ResponseEntity<List<RegisterResponseDTO>> findAll() {
		List<Register> entities = this.service.getAll();
		List<RegisterResponseDTO> entitiesDTO = this.iRegisterMapper.listEntityToDTO(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}

	

	@GetMapping("/{id}")
	public ResponseEntity<RegisterResponseDTO> findById(@PathVariable("id") Long id) {
		Register entity = this.service.readById(id);
		RegisterResponseDTO dto = this.iRegisterMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	 
	 

	@PutMapping("/{id}")
	public ResponseEntity<RegisterResponseDTO> update(@PathVariable("id") Long id,@RequestBody @Valid RegisterRequestDTO dto) {
		
		try {
			Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    
			
			Register entity = this.iRegisterMapper.toEntity(dto);
			entity.setIdUser(idUsuario);
			Register entityUpdated = this.service.update(entity, id);
			return new ResponseEntity<>(this.iRegisterMapper.toGetDTO(entityUpdated), HttpStatus.OK);
			
		} catch (Exception e) {
			throw new ServiceException("Error when update the registration " + e.getMessage(), e.getCause());
		}

	}

	  
	

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
