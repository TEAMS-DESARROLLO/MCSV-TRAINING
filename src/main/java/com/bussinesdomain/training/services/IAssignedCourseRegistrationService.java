package com.bussinesdomain.training.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.bussinesdomain.training.commons.IBaseInterfaceService;
import com.bussinesdomain.training.models.AssignedCourseRegistration;

public interface IAssignedCourseRegistrationService extends IBaseInterfaceService<AssignedCourseRegistration,Long>{

    List<AssignedCourseRegistration> findByFollowUpId(@Param("followUpId") Long followUpId);

}
