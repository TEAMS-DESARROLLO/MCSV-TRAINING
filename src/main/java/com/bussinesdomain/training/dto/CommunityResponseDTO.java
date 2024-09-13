package com.bussinesdomain.training.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommunityResponseDTO {

    private Long idCommunity;
    private String description;

    private Long idRegion;
    private String regionDescription;
}
