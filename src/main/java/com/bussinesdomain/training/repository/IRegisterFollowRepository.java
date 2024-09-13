package com.bussinesdomain.training.repository;

import org.springframework.data.jpa.repository.Query;
import com.bussinesdomain.training.models.RegisterFollow;
import org.springframework.data.repository.query.Param;


public interface IRegisterFollowRepository extends IGenericRepository<RegisterFollow,Long>{


    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RegisterFollow r WHERE r.register.idRegister = :idRegister")
    boolean existsRegisterFollowByIdRegister(@Param("idRegister") Long idRegister);

    @Query("SELECT r FROM RegisterFollow r WHERE r.register.idRegister = :idRegister")
    RegisterFollow getRegisterFollowByIdRegister(@Param("idRegister") Long idRegister);



}
