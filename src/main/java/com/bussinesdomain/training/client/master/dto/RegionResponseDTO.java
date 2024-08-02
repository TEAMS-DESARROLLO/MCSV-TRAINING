package com.bussinesdomain.training.client.master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegionResponseDTO {

	@EqualsAndHashCode.Include
	private Long idRegion;
	
	private String description;
	
}
