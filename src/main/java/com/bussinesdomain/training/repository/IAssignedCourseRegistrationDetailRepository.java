package com.bussinesdomain.training.repository;

import java.util.List;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.Query;

import com.bussinesdomain.training.models.AssignedCourseRegistrationDetail;



public interface IAssignedCourseRegistrationDetailRepository extends IGenericRepository<AssignedCourseRegistrationDetail, Long> {

    @Query("SELECT acrd FROM AssignedCourseRegistrationDetail acrd WHERE acrd.assignedCourseRegistration.idAssignedCourseRegistration = :assignedCourseRegistrationId")
    List<AssignedCourseRegistrationDetail> findByAssignedCourseRegistrationId(@Param("assignedCourseRegistrationId") Long assignedCourseRegistrationId);

}
