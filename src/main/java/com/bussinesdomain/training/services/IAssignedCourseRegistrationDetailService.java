package com.bussinesdomain.training.services;



import java.util.List;
import com.bussinesdomain.training.commons.IBaseInterfaceService;
import com.bussinesdomain.training.models.AssignedCourseRegistrationDetail;

public interface IAssignedCourseRegistrationDetailService  extends IBaseInterfaceService<AssignedCourseRegistrationDetail, Long>{

    List<AssignedCourseRegistrationDetail> findByAssignedCourseRegistrationId(Long assignedCourseRegistrationId);

}
