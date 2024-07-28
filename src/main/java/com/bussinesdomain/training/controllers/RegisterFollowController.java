package com.bussinesdomain.training.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bussinesdomain.training.commons.PaginationModel;
import com.bussinesdomain.training.dto.RegisterFollowRequestDTO;
import com.bussinesdomain.training.dto.RegisterFollowResponseDTO;
import com.bussinesdomain.training.mapper.IRegisterFollowMapper;
import com.bussinesdomain.training.models.RegisterFollow;
import com.bussinesdomain.training.services.IRegisterFollowService;
import com.bussinesdomain.training.services.impl.RegisterFollowPaginationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration_follow")
@Validated
@Tag(name = "RegisterFollow")
public class RegisterFollowController {

	private final IRegisterFollowService service;

	private final RegisterFollowPaginationServiceImpl servicePagination;

	private final IRegisterFollowMapper iRegisterFollowMapper;

	
	
	@Operation(
        summary = "Paginate through Register Follow entities",
        description = "Returns a paginated list of Register Follow entities based on the provided pagination model.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Pagination model to control pagination settings.",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PaginationModel.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved paginated list of Register Follow entities",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request, possibly due to invalid pagination parameters"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            )
        }
    )
	@PostMapping("/pagination")
	public ResponseEntity<?> paginator(@RequestBody PaginationModel pagination) {
		log.info("PAGINATION ..... " + pagination);
		Page<RegisterFollowResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	
	
	@Operation(
        summary = "Create a new Register Follow entity",
        description = "Creates a new Register Follow entity and returns the created entity.",
        tags = {"RegisterFollow"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the Register Follow entity to create",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegisterFollowRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Successfully created the Register Follow entity",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RegisterFollowResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input provided"
            )
        }
    )
	@PostMapping("/create")
	public ResponseEntity<RegisterFollowResponseDTO> save(@RequestBody @Valid RegisterFollowRequestDTO dto) {
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		RegisterFollow entity = this.service.create(this.iRegisterFollowMapper.toEntity(dto));
		RegisterFollowResponseDTO registerFollowDTO = this.iRegisterFollowMapper.toGetDTO(entity);
		return new ResponseEntity<>(registerFollowDTO, HttpStatus.CREATED);
	}

	
	@Operation(
        summary = "Retrieve all Register Follow entities",
        description = "Fetches a list of all Register Follow entities.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of Register Follow entities retrieved successfully",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = RegisterFollowResponseDTO.class))
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            )
        }
    )
	@GetMapping("/all")
	public ResponseEntity<List<RegisterFollowResponseDTO>> findAll() {
		List<RegisterFollow> entities = this.service.getAll();
		List<RegisterFollowResponseDTO> entitiesDTO = this.iRegisterFollowMapper.listEntityToDTO(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}

	
	 @Operation(
        summary = "Find a Register Follow entity by id",
        description = "Retrieves a Register Follow entity identified by the specified id.",
        parameters = {
        	@io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "id of the Register Follow entity to be retrieved",
                required = true,
                schema = @Schema(type = "integer", example = "123")
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Register Follow entity retrieved successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RegisterFollowResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Register Follow entity not found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            )
        }
    )
	@GetMapping("/{id}")
	public ResponseEntity<RegisterFollowResponseDTO> findById(@PathVariable("id") Long id) {
		RegisterFollow entity = this.service.readById(id);
		RegisterFollowResponseDTO dto = this.iRegisterFollowMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
	@Operation(
        summary = "Update an existing Register Follow entity",
        description = "Updates the details of an existing Register Follow entity identified by the id.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "id of the Register Follow entity to be updated",
                required = true,
                schema = @Schema(type = "integer", example = "123")
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body containing the details of the Register Follow entity to be updated",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegisterFollowRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Register Follow entity updated successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RegisterFollowResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input provided"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Register Follow entity not found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            )
        }
    )
	@PutMapping("/{id}")
	public ResponseEntity<RegisterFollowResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid RegisterFollowRequestDTO dto) {
		dto.setIdRegisterFollow(id);
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		RegisterFollow entity = this.iRegisterFollowMapper.toEntity(dto);
		RegisterFollow entityUpdated = this.service.update(entity, id);
		return new ResponseEntity<>(this.iRegisterFollowMapper.toGetDTO(entityUpdated), HttpStatus.OK);
	}

	
	
	@Operation(
        summary = "Delete a Register Follow entity by id",
        description = "Deletes the Register Follow entity identified by the specified id.",
        parameters = {
        		@io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "id of the Register Follow entity to be deleted",
                required = true,
                schema = @Schema(type = "integer", example = "123")
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Register Follow entity deleted successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Register Follow entity not found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            )
        }
    )
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
