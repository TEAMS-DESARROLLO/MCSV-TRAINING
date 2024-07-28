package com.bussinesdomain.training.client.master.dto;

import com.bussinesdomain.training.constants.ValidationMessage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CollaboratorResponseDTO {

    @EqualsAndHashCode.Include
    @NotNull(message = ValidationMessage.CAN_T_BE_NULL)
    private Long idCollaborator;

    @NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
    private String lastName;
    @NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
    private String names;
    @NotEmpty(message = ValidationMessage.NONEMPTY_STRING)
    @NotBlank(message = ValidationMessage.NOWHITESPACES_STRING)
    private String email;

    private Integer state;


    @NotNull(message = ValidationMessage.CAN_T_BE_NULL)
    private Long idLeader;
    private String leaderNames;


    @NotNull(message = ValidationMessage.CAN_T_BE_NULL)
    private Long idRol;
    private String rolDescription;

    @NotNull(message = ValidationMessage.CAN_T_BE_NULL)
    private Long idRegion;
    private String regionDescription;

    @NotNull(message = ValidationMessage.CAN_T_BE_NULL)
    private Long idFunctionalLeader;
    private String functionalLeaderNames;

    private Long idCommunity;
    private String communityDescription;

}