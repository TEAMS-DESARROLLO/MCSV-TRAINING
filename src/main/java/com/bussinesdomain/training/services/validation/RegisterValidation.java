package com.bussinesdomain.training.services.validation;

import org.springframework.stereotype.Service;

import com.bussinesdomain.training.client.master.MasterClient;
import com.bussinesdomain.training.exception.FeignExceptionHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterValidation {

	private final MasterClient masterClient;

	public void checkIdCollaboratorValid(Long idCollaborator) {
		try {
			masterClient.findCollaboratorById(idCollaborator);
		} catch (Exception e) {
			FeignExceptionHandler.handleFeignException(e, idCollaborator, "collaborator");
		}
	}

	public void checkIdLeaderValid(Long idLeader, String entityType) {
		try {
			masterClient.findLeaderById(idLeader);
		} catch (Exception e) {
			FeignExceptionHandler.handleFeignException(e, idLeader, entityType);
		}
	}

}
