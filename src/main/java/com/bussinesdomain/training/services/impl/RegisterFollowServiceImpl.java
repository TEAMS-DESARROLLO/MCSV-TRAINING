package com.bussinesdomain.training.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.exception.ModelNotFoundException;
import com.bussinesdomain.training.models.RegisterFollow;
import com.bussinesdomain.training.repository.IGenericRepository;
import com.bussinesdomain.training.repository.IRegisterFollowRepository;
import com.bussinesdomain.training.services.IRegisterFollowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterFollowServiceImpl extends CRUDImpl<RegisterFollow, Long> implements IRegisterFollowService {
	
	private final IGenericRepository<RegisterFollow, Long> repository;

	private final IRegisterFollowRepository iRegisterFollowRepository;
	
	@Override
	protected IGenericRepository<RegisterFollow, Long> getRepo() {
		return repository;
	}
	
	@Override
	public List<RegisterFollow> getAll(){
		return super.getAll().stream()
				.filter( r -> RegistrationStatus.ACTIVE.equals(r.getRegistrationStatus()) )
				.toList();
	}
	
	@Override
	public RegisterFollow update(RegisterFollow entity, Long id) {
		RegisterFollow original = this.readById(id);
		if( original == null ) {
			throw new ModelNotFoundException( "ID does not exist : " + id );
		}
		//entity.setCreatedAt( original.getCreatedAt() );
		//entity.setRegistrationStatus( original.getRegistrationStatus().isEmpty() ? RegistrationStatus.ACTIVE : entity.getRegistrationStatus() );
		String [] ignoreProperties = {"createdAt","registrationStatus"};
		BeanUtils.copyProperties(entity, original, ignoreProperties);
		return super.update(original, id);
	}
	
	@Override
	public void deleteById(Long id) {
		RegisterFollow original = this.readById(id);
		if( original == null ) {
			throw new ModelNotFoundException( "ID does not exist : " + id );
		}
		original.setRegistrationStatus(RegistrationStatus.INACTIVE);
		super.update(original, id);
	}

	@Override
	public boolean existsRegisterFollowByIdRegister(Long idRegister) {
		return iRegisterFollowRepository.existsRegisterFollowByIdRegister(idRegister);
	}

	@Override
	public RegisterFollow getRegisterFollowByIdRegister(Long idRegister) {
		return iRegisterFollowRepository.getRegisterFollowByIdRegister(idRegister);
	}



}
