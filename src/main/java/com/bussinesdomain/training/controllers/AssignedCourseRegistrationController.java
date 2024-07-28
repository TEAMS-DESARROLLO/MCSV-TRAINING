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
import com.bussinesdomain.training.dto.AssignedCourseRegistrationRequestDTO;
import com.bussinesdomain.training.dto.AssignedCourseRegistrationResponseDTO;
import com.bussinesdomain.training.mapper.IAssignedCourseRegistrationMapper;
import com.bussinesdomain.training.models.AssignedCourseRegistration;
import com.bussinesdomain.training.services.IAssignedCourseRegistrationService;
import com.bussinesdomain.training.services.impl.AssignedCourseRegistrationPaginationServiceImpl;

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
@RequestMapping("/assigned_course_registration")
@Validated
@Tag(name = "AssignedCourseRegistration")
public class AssignedCourseRegistrationController {

	private final IAssignedCourseRegistrationService service;
	private final AssignedCourseRegistrationPaginationServiceImpl servicePagination;
	private final IAssignedCourseRegistrationMapper iPaqueteMapper;

	
	 @Operation(
        summary = "Paginate an assigned course registration",
        description = "Returns a paginated list of assigned course registration based on the pagination parameters",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Pagination parameters",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PaginationModel.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved paginated list",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = Page.class,
                        type = "array",
                        example = "{ 'content': [{ 'id': 1, 'name': 'Entity Name' }], 'totalPages': 10, 'totalElements': 100 }"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid pagination parameters"
            )
        }
    )
	@PostMapping("/pagination")
	public ResponseEntity<?> paginador(@RequestBody PaginationModel pagination) {
		log.info("PAGINATION ....." + pagination);
		Page<AssignedCourseRegistrationResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	
	@Operation(
			summary = "Save an assigned course registration entity",
			description = "Successfully save an assigned course registration entity",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description= "Request DTO for AssignedCourseRegistrationRequest",
					required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = AssignedCourseRegistrationRequestDTO.class )
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description= "Created",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = AssignedCourseRegistrationResponseDTO.class)
							)
					)
			}
	)
	@PostMapping("/create")
	public ResponseEntity<AssignedCourseRegistrationResponseDTO> save(@RequestBody @Valid AssignedCourseRegistrationRequestDTO dto) {
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		AssignedCourseRegistration entity = this.service.create(this.iPaqueteMapper.toEntity(dto));
		AssignedCourseRegistrationResponseDTO assignedCourseRegistrationDTO = this.iPaqueteMapper.toGetDTO(entity);
		return new ResponseEntity<>(assignedCourseRegistrationDTO, HttpStatus.CREATED);
	}

	
	@Operation(
			summary = "Find all assigned courses registration entity ",
			description = "Successfully retrieved all records",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description= "Success retrieve all records",
							content = @Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = AssignedCourseRegistrationResponseDTO.class))
							)
					)
			}
	)
	@GetMapping("/all")
	public ResponseEntity<List<AssignedCourseRegistrationResponseDTO>> findByFiltro() {
		List<AssignedCourseRegistration> entities = this.service.getAll();
		List<AssignedCourseRegistrationResponseDTO> entitiesDTO = this.iPaqueteMapper.listEntityToDto(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}

	
	@Operation(
        summary = "Find an assigned course registration by id",
        description = "Fetches a single assigned course registration based on the provided id",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the assigned course registration",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the assigned course registration",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssignedCourseRegistrationResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Assigned course registration not found",
                content = @Content(
                    mediaType = "application/json"
                )
            )
        }
    )
	@GetMapping("/{id}")
	public ResponseEntity<AssignedCourseRegistrationResponseDTO> findById(@PathVariable("id") Long id) {
		AssignedCourseRegistration entity = this.service.readById(id);
		AssignedCourseRegistrationResponseDTO dto = this.iPaqueteMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
	@Operation(
        summary = "Update an assined course registration by id",
        description = "Updates an existing assined course registratoin with the provided details",
        parameters = {
        	@io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the assigned course registration to be updated",
                required = true,
                example = "1"
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated details of the assigned course registration",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AssignedCourseRegistrationRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully updated the assigned course registration",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssignedCourseRegistrationResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Assigned course registration not found",
                content = @Content(
                    mediaType = "application/json"
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input",
                content = @Content(
                    mediaType = "application/json"
                )
            )
        }
    )
	@PutMapping("/{id}")
	public ResponseEntity<AssignedCourseRegistrationResponseDTO> update(@PathVariable("id") Long id,@RequestBody @Valid AssignedCourseRegistrationRequestDTO dto) {
		dto.setIdAssignedCourseRegistration(id);
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		AssignedCourseRegistration entity = this.iPaqueteMapper.toEntity(dto);
		AssignedCourseRegistration entityUpdated = service.update(entity, id);
		return new ResponseEntity<>(this.iPaqueteMapper.toGetDTO(entityUpdated), HttpStatus.OK);
	}
	
	
	@Operation(
        summary = "Delete an assigned course registration by id",
        description = "Deletes an assigned course registration identified by the id",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the assigned course registration to be deleted",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Successfully deleted the assigned course registration"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Assigned course registration not found"
            )
        }
    )
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
