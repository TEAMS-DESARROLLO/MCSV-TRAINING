package com.bussinesdomain.training.client.master;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.bussinesdomain.training.client.master.dto.CollaboratorResponseDTO;
import com.bussinesdomain.training.client.master.dto.LeaderResponseDTO;
import com.bussinesdomain.training.client.master.dto.RegionResponseDTO;
import com.bussinesdomain.training.client.master.dto.UnitMeasureResponseDTO;


@FeignClient( name="mcsv-master", url="localhost:9002" )
public interface MasterClient {

	@GetMapping("/leader/{idLeader}")
	public ResponseEntity<LeaderResponseDTO> findLeaderById(@RequestHeader("Authorization") String authorization,@PathVariable("idLeader") Long idLeader);
	
	@GetMapping("/leader/")
	public ResponseEntity<List<LeaderResponseDTO>> findLeadersByListId(@RequestHeader("Authorization") String authorization,@RequestParam("idLeaderList") List<Long> idLeaderList);

	@GetMapping("/collaborator/{idCollaborator}")
	public ResponseEntity<CollaboratorResponseDTO> findCollaboratorById(@RequestHeader("Authorization") String authorization,@PathVariable("idCollaborator") Long idCollaborator);
	
	@GetMapping("/collaborator/")
	public ResponseEntity<List<CollaboratorResponseDTO>> findCollaboratorsByListId(@RequestHeader("Authorization") String authorization,@RequestParam("idCollaboratorList") List<Long> idCollaboratorList);
	

	@GetMapping("/region/")
	public ResponseEntity<List<RegionResponseDTO>> findRegionsByListId(@RequestHeader("Authorization") String authorization,@RequestParam("idRegionList") List<Long> idRegionList);
	
	@GetMapping("/unitmeasure/{id}")
	public ResponseEntity<UnitMeasureResponseDTO> findUnitMeasureById(@RequestHeader("Authorization") String authorization,@PathVariable("id") Long id);
	
	@GetMapping("/unitmeasure/")
	public ResponseEntity<List<UnitMeasureResponseDTO>> findUnitMeasuresByListId(@RequestHeader("Authorization") String authorization,@RequestParam("idUnitMeasureList") List<Long> idUnitMeasuresLst);
}
