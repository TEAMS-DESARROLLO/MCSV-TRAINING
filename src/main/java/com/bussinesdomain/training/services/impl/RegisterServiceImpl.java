package com.bussinesdomain.training.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.exception.ModelNotFoundException;
import com.bussinesdomain.training.models.Register;
import com.bussinesdomain.training.repository.IGenericRepository;
import com.bussinesdomain.training.services.IRegisterService;
import com.bussinesdomain.training.services.validation.RegisterValidation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl extends CRUDImpl<Register, Long> implements IRegisterService{
	
	private final IGenericRepository<Register, Long> repository;
	
	private final RegisterValidation registerValidation;
	
	@Override
	protected IGenericRepository<Register, Long> getRepo() {
		return repository;
	}
	
	@Override
	public List<Register> getAll(){
		return super.getAll().stream()
				.filter( r -> RegistrationStatus.ACTIVE.equals(r.getRegistrationStatus()))
				.toList();
	}
	
	@Override
	public Register create(Register entity) {
		// registerValidation.checkIdCollaboratorValid(entity.getIdCollaborator()); 
		// registerValidation.checkIdLeaderValid(entity.getIdLeader(),"leader");
		// registerValidation.checkIdLeaderValid(entity.getIdRegion(),"leaderRegion");
		return super.create(entity);
	}
	
	@Override
	public Register update(Register entity, Long id) {
		Register original = this.readById(id);
		if( original == null ) {
			throw new ModelNotFoundException("ID does not exist : "+id);
		}
		//entity.setIdRegister(id);
		//entity.setCreatedAt( original.getCreatedAt() );
		//entity.setRegistrationStatus( original.getRegistrationStatus().isEmpty() ? RegistrationStatus.ACTIVE : entity.getRegistrationStatus() );
		String[] ignoreProperties = {"createdAt","idRegister","registrationStatus"};
		BeanUtils.copyProperties(entity, original,ignoreProperties);
		return super.update(original, id);
	}
	
	@Override
	public void deleteById(Long id) {
		Register original = this.readById(id);
		if( original == null ) {
			throw new ModelNotFoundException("ID does not exist : "+id);
		}
		original.setRegistrationStatus(RegistrationStatus.INACTIVE);
		super.update(original, id);
	}
	
	

}