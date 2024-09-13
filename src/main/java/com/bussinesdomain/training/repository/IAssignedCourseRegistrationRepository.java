package com.bussinesdomain.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bussinesdomain.training.models.AssignedCourseRegistration;


public interface IAssignedCourseRegistrationRepository extends IGenericRepository<AssignedCourseRegistration, Long> {

    @Query("SELECT acr FROM AssignedCourseRegistration acr WHERE acr.registerFollow.idRegisterFollow = :followUpId")
    List<AssignedCourseRegistration> findByFollowUpId(@Param("followUpId") Long followUpId);

}
