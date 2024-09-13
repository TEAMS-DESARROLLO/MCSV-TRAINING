package com.bussinesdomain.training.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.dto.RegisterFollowRequestDTO;
import com.bussinesdomain.training.dto.RegisterFollowResponseDTO;
import com.bussinesdomain.training.mapper.IRegisterFollowMapper;
import com.bussinesdomain.training.models.Register;
import com.bussinesdomain.training.models.RegisterFollow;
import com.bussinesdomain.training.services.IRegisterFollowService;
import com.bussinesdomain.training.services.IRegisterService;
import com.bussinesdomain.training.services.impl.RegisterFollowPaginationServiceImpl;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/registrationFollow")
public class RegisterFollowController {

	private final IRegisterFollowService service;

	private final RegisterFollowPaginationServiceImpl servicePagination;

	private final IRegisterFollowMapper iRegisterFollowMapper;

	private final IRegisterService iRegisterService;

	

	@PostMapping("/pagination")
	public ResponseEntity<?> paginator(@RequestBody PaginationModel pagination) {
		log.info("PAGINATION ..... " + pagination);
		Page<RegisterFollowResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	
	

	@PostMapping("/create")
	public ResponseEntity<RegisterFollowResponseDTO> save(@RequestBody @Valid RegisterFollowRequestDTO dto) {
		
		Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    

		RegisterFollow registerFollow = this.iRegisterFollowMapper.toEntity(dto);

		registerFollow.setIdUser(idUsuario);
		
		RegisterFollow entity = this.service.create( registerFollow );

		RegisterFollowResponseDTO registerFollowDTO = this.iRegisterFollowMapper.toGetDTO(entity);
		return new ResponseEntity<>(registerFollowDTO, HttpStatus.CREATED);
	}


	@GetMapping("/all")
	public ResponseEntity<List<RegisterFollowResponseDTO>> findAll() {
		List<RegisterFollow> entities = this.service.getAll();
		List<RegisterFollowResponseDTO> entitiesDTO = this.iRegisterFollowMapper.listEntityToDTO(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<RegisterFollowResponseDTO> findById(@PathVariable("id") Long id) {
		RegisterFollow entity = this.service.readById(id);
		RegisterFollowResponseDTO dto = this.iRegisterFollowMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("search/{id}")
	public ResponseEntity<RegisterFollowResponseDTO> findFollowByIdRegisterAndCreate(@PathVariable("id") Long idRegister) {

		Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    

		boolean existeIdRegisterInRegisterFollow = this.service.existsRegisterFollowByIdRegister(idRegister);

		
		if(existeIdRegisterInRegisterFollow){
			RegisterFollow entity = this.service.getRegisterFollowByIdRegister(idRegister);
			RegisterFollowResponseDTO dto = this.iRegisterFollowMapper.toGetDTO(entity);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}else{

			Register register = this.iRegisterService.readById(idRegister);

			RegisterFollow entity = new RegisterFollow();
			entity.setIdUser(idUsuario);
			entity.setRegister(register);
			entity.setDateStartFollow( LocalDate.now() );
			entity.setRegistrationStatus( RegistrationStatus.ACTIVE ); 
			entity.setObservation("");
			entity = service.create(entity);
			RegisterFollowResponseDTO dto = this.iRegisterFollowMapper.toGetDTO(entity);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		
	}	

	@PutMapping("/{id}")
	public ResponseEntity<RegisterFollowResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid RegisterFollowRequestDTO dto) {
		dto.setIdRegisterFollow(id);
		Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();    
		
		RegisterFollow entity = this.iRegisterFollowMapper.toEntity(dto);
		entity.setIdUser(idUsuario);
		RegisterFollow entityUpdated = this.service.update(entity, id);
		return new ResponseEntity<>(this.iRegisterFollowMapper.toGetDTO(entityUpdated), HttpStatus.OK);
	}

	

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
