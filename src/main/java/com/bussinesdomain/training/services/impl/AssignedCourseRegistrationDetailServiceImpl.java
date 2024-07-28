package com.bussinesdomain.training.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.exception.ModelNotFoundException;
import com.bussinesdomain.training.models.AssignedCourseRegistrationDetail;
import com.bussinesdomain.training.repository.IGenericRepository;
import com.bussinesdomain.training.services.IAssignedCourseRegistrationDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignedCourseRegistrationDetailServiceImpl extends CRUDImpl<AssignedCourseRegistrationDetail, Long> implements IAssignedCourseRegistrationDetailService{

	private final IGenericRepository<AssignedCourseRegistrationDetail, Long> repository;
	
	@Override
	protected IGenericRepository<AssignedCourseRegistrationDetail, Long> getRepo() {
		return repository;
	}
	
	@Override
	public List<AssignedCourseRegistrationDetail> getAll(){
		return super.getAll().stream()
				.filter( a -> RegistrationStatus.ACTIVE.equals(a.getRegistrationStatus()) )
				.toList();
	}
	
	@Override
	public AssignedCourseRegistrationDetail update(AssignedCourseRegistrationDetail entity,Long id) {
		AssignedCourseRegistrationDetail original = this.readById(id);
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
		AssignedCourseRegistrationDetail original = this.readById(id);
		if( original == null ) {
			throw new ModelNotFoundException( "ID does not exist : " + id );
		}
		original.setRegistrationStatus(RegistrationStatus.INACTIVE);
		super.update(original, id);
	}

}
 