package com.bussinesdomain.training.services;

import com.bussinesdomain.training.commons.IBaseInterfaceService;
import com.bussinesdomain.training.models.RegisterFollow;



public interface IRegisterFollowService extends IBaseInterfaceService<RegisterFollow, Long> {
    
    boolean existsRegisterFollowByIdRegister(Long idRegister);

    RegisterFollow getRegisterFollowByIdRegister( Long idRegister);
}
