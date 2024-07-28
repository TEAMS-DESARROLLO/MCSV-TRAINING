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
import com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailRequestDTO;
import com.bussinesdomain.training.dto.AssignedCourseRegistrationDetailResponseDTO;
import com.bussinesdomain.training.mapper.IAssignedCourseRegistrationDetailMapper;
import com.bussinesdomain.training.models.AssignedCourseRegistrationDetail;
import com.bussinesdomain.training.services.IAssignedCourseRegistrationDetailService;
import com.bussinesdomain.training.services.impl.AssignedCourseRegistrationDetailPaginationServiceImpl;

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
@RequestMapping("/assigned_course_registration_detail")
@Validated
@Tag(name="AssignedCourseRegistrationDetail")
public class AssignedCourseRegistrationDetailController {

	private final IAssignedCourseRegistrationDetailService service;
	private final AssignedCourseRegistrationDetailPaginationServiceImpl servicePagination;
	private final IAssignedCourseRegistrationDetailMapper iPaqueteMapper;

	
	@Operation(
        summary = "Paginate an assigned course registration detail",
        description = "Returns a paginated list of assigned course registration detail based on the pagination parameters",
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
		Page<AssignedCourseRegistrationDetailResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	
	@Operation(
			summary = "Save an assigned course registration detail",
			description = "Successfully save an assigned course registration detail",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description= "Request DTO for AssignedCourseRegistrationDetailRequest",
					required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = AssignedCourseRegistrationDetailRequestDTO.class )
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description= "Created",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = AssignedCourseRegistrationDetailResponseDTO.class)
							)
					)
			}
	)
	@PostMapping("/create")
	public ResponseEntity<AssignedCourseRegistrationDetailResponseDTO> save( @RequestBody @Valid AssignedCourseRegistrationDetailRequestDTO dto) {
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		AssignedCourseRegistrationDetail entidad = service.create(this.iPaqueteMapper.toEntity(dto));
		AssignedCourseRegistrationDetailResponseDTO assignedCourseRegistrationDetailDTO = iPaqueteMapper.toGetDTO(entidad);
		return new ResponseEntity<>(assignedCourseRegistrationDetailDTO, HttpStatus.CREATED);
	}

	
	@Operation(
			summary = "Find all assigned course registration detail ",
			description = "Successfully retrieved all assigned course registration detail",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description= "Success retrieve all records",
							content = @Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = AssignedCourseRegistrationDetailResponseDTO.class))
							)
					)
			}
	)
	@GetMapping("/all")
	public ResponseEntity<List<AssignedCourseRegistrationDetailResponseDTO>> findByFiltro() {
		List<AssignedCourseRegistrationDetail> entities = service.getAll();
		List<AssignedCourseRegistrationDetailResponseDTO> entitiesDTO = this.iPaqueteMapper.listEntityToDto(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}

	
	@Operation(
        summary = "Find an assigned course registration detail by id",
        description = "Fetches a single an assigned course registration detail based on the provided id",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the assigned course registration detail",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the assigned course registration detail",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssignedCourseRegistrationDetailResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Register entity not found",
                content = @Content(
                    mediaType = "application/json"
                )
            )
        }
    )
	@GetMapping("/{id}")
	public ResponseEntity<AssignedCourseRegistrationDetailResponseDTO> findById(@PathVariable("id") Long id) {
		AssignedCourseRegistrationDetail entity = service.readById(id);
		AssignedCourseRegistrationDetailResponseDTO dto = iPaqueteMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
	@Operation(
        summary = "Update an assigned course registration detail by id",
        description = "Updates an existing an assigned course registration detail with the provided details",
        parameters = {
        	@io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the assigned course registration detail to be updated",
                required = true,
                example = "1"
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated details of the assigned course registration detail",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AssignedCourseRegistrationDetailRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully updated the assigned course registration detail",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssignedCourseRegistrationDetailResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Assigned course registration detail not found",
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
	public ResponseEntity<AssignedCourseRegistrationDetailResponseDTO> update(@PathVariable("id") Long id,@RequestBody @Valid AssignedCourseRegistrationDetailRequestDTO dto) {
		dto.setIdAssignedCourseRegistrationDetail(id);
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		AssignedCourseRegistrationDetail entity = this.iPaqueteMapper.toEntity(dto);
		AssignedCourseRegistrationDetail entityUpdated = service.update(entity, id);
		return new ResponseEntity<>(this.iPaqueteMapper.toGetDTO(entityUpdated), HttpStatus.OK);
	}

	
	@Operation(
        summary = "Delete an assigned course registration detail by id",
        description = "Deletes an assigned course registration detail identified by the id",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the assigned course registration detail to be deleted",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Successfully deleted the assigned course registration detail"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Assigned course registration detail not found"
            )
        }
    )
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
