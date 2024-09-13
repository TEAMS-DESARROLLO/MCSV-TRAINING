package com.bussinesdomain.training.client.master;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.bussinesdomain.training.dto.CollaboratorResponseDTO;
import com.bussinesdomain.training.dto.CommunityResponseDTO;
import com.bussinesdomain.training.dto.LeaderResponseDTO;
import com.bussinesdomain.training.dto.RegionResponseDTO;
import com.bussinesdomain.training.dto.UnitMeasureResponseDTO;


@FeignClient(name="mcsv-master")
public interface MasterClient {

	

	 @GetMapping("/leader/{idLeader}")
	 public ResponseEntity<LeaderResponseDTO> findLeaderById(@PathVariable("idLeader") Long idLeader);

	 @GetMapping("/leader")
	 public ResponseEntity<LeaderResponseDTO> findLeaderById( @RequestParam("idLiders") List<Long> idLeaders);
	
	 @GetMapping("/collaborator/{idCollaborator}")
	 public ResponseEntity<CollaboratorResponseDTO> findCollaboratorById(@PathVariable("idCollaborator") Long idCollaborator);
	 
	 @GetMapping("/unitmeasure/{id}")
	 public ResponseEntity<UnitMeasureResponseDTO> findUnitMeasureById(@PathVariable("id") Long id);

	 @PostMapping( value = "/collaborator/collaboratorsByIds", consumes = "application/json")
	 public List<CollaboratorResponseDTO> collaboratorsByIds(@RequestHeader("Authorization") String authorization , @RequestParam("ids") List<Long> ids);

	 @PostMapping(value = "/leader/leadersByIds", consumes = "application/json")
	 public List<LeaderResponseDTO> findLeaderByIds(@RequestHeader("Authorization") String authorization , @RequestParam("ids") List<Long> ids);

	 @PostMapping("/region/regionByIds")
	 public List<RegionResponseDTO> findRegionByIds(@RequestHeader("Authorization") String authorization , @RequestParam("ids") List<Long> ids);

	 @PostMapping("/community/communityByIds")
	 public List<CommunityResponseDTO> findCommunityByIds(@RequestHeader("Authorization") String authorization , @RequestParam("ids") List<Long> ids);	 

	 
}
