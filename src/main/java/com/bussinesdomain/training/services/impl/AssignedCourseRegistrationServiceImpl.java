package com.bussinesdomain.training.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bussinesdomain.training.constants.RegistrationStatus;
import com.bussinesdomain.training.exception.ModelNotFoundException;
import com.bussinesdomain.training.models.AssignedCourseRegistration;
import com.bussinesdomain.training.repository.IAssignedCourseRegistrationRepository;
import com.bussinesdomain.training.repository.IGenericRepository;
import com.bussinesdomain.training.services.IAssignedCourseRegistrationService;
import com.bussinesdomain.training.services.validation.AssignedCourseRegistrationValidation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignedCourseRegistrationServiceImpl extends CRUDImpl<AssignedCourseRegistration, Long> implements IAssignedCourseRegistrationService {

	private final IGenericRepository<AssignedCourseRegistration, Long> repository;
	
	private final AssignedCourseRegistrationValidation assignedCourseRegistrationValidation; 

	private final IAssignedCourseRegistrationRepository iAssignedCourseRegistrationRepository;
	
	@Override
	protected IGenericRepository<AssignedCourseRegistration, Long> getRepo() {
		return this.repository;
	}
	
	@Override
	public List<AssignedCourseRegistration> getAll(){
		return super.getAll().stream()
				.filter( a -> RegistrationStatus.ACTIVE.equals(a.getRegistrationStatus()) )
				.toList();
	}
	
	@Override
	public AssignedCourseRegistration create(AssignedCourseRegistration entity) {
		// assignedCourseRegistrationValidation.checkIdUnitMeasureValid(entity.getIdUnitMeasure());
		return super.create(entity);
		//return null;
	}
	
	@Override
	public AssignedCourseRegistration update( AssignedCourseRegistration entity, Long id ) {
		AssignedCourseRegistration original = this.readById(id);
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
		AssignedCourseRegistration original = this.readById(id);
		if( original == null ) {
			throw new ModelNotFoundException("ID does not exist : "+id);
		}
		original.setRegistrationStatus(RegistrationStatus.INACTIVE);
		super.update(original, id);
	}

	@Override
	public List<AssignedCourseRegistration> findByFollowUpId(Long followUpId) {
		return iAssignedCourseRegistrationRepository.findByFollowUpId(followUpId);
	}
	

}