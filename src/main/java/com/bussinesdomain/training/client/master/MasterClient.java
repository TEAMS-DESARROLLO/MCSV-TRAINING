package com.bussinesdomain.training.client.master;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bussinesdomain.training.client.master.dto.CollaboratorResponseDTO;
import com.bussinesdomain.training.client.master.dto.LeaderResponseDTO;
import com.bussinesdomain.training.client.master.dto.UnitMeasureResponseDTO;

@FeignClient( name="mcsv-master", url="localhost:9091" )
public interface MasterClient {

	 @GetMapping("/leader/{idLeader}")
	 public ResponseEntity<LeaderResponseDTO> findLeaderById(@PathVariable("idLeader") Long idLeader);
	
	 @GetMapping("/collaborator/{idCollaborator}")
	 public ResponseEntity<CollaboratorResponseDTO> findCollaboratorById(@PathVariable("idCollaborator") Long idCollaborator);
	 
	 @GetMapping("/unitmeasure/{id}")
	 public ResponseEntity<UnitMeasureResponseDTO> findUnitMeasureById(@PathVariable("id") Long id);
}
