package com.bussinesdomain.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LeaderResponseDTO {

    private Long idLeader;
    private String names;

    private Long idCommunity;
    private String communityDescription;
}
