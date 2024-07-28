package com.bussinesdomain.training.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.exception.ModelNotFoundException;
import com.bussinesdomain.training.models.RegisterFollow;
import com.bussinesdomain.training.repository.IGenericRepository;
import com.bussinesdomain.training.services.IRegisterFollowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterFollowServiceImpl extends CRUDImpl<RegisterFollow, Long> implements IRegisterFollowService {
	
	private final IGenericRepository<RegisterFollow, Long> repository;
	
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
		entity.setCreatedAt( original.getCreatedAt() );
		entity.setRegistrationStatus( original.getRegistrationStatus().isEmpty() ? RegistrationStatus.ACTIVE : entity.getRegistrationStatus() );
		BeanUtils.copyProperties(entity, original);
		return super.update(entity, id);
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

}
