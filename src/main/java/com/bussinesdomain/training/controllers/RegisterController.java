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
import com.bussinesdomain.training.dto.RegisterRequestDTO;
import com.bussinesdomain.training.dto.RegisterResponseDTO;
import com.bussinesdomain.training.mapper.IRegisterMapper;
import com.bussinesdomain.training.models.Register;
import com.bussinesdomain.training.services.IRegisterService;
import com.bussinesdomain.training.services.impl.RegisterPaginationServiceImpl;

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
@RequestMapping("/registration")
@Validated
@Tag(name = "Register")
public class RegisterController {

	private final IRegisterService service;

	private final RegisterPaginationServiceImpl servicePagination;

	private final IRegisterMapper iRegisterMapper;

	
	
    @Operation(
        summary = "Paginate register",
        description = "Returns a paginated list of registers based on the pagination parameters",
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
	public ResponseEntity<?> paginator(@RequestBody PaginationModel pagination) {
		log.info("PAGINATION ..... " + pagination);
		Page<RegisterResponseDTO> lst = servicePagination.pagination(pagination);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	
	@Operation(
			summary = "Save a register",
			description = "Successfully save a register",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description= "Request DTO for RegisterRequest",
					required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = RegisterRequestDTO.class )
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description= "Created",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = RegisterResponseDTO.class)
							)
					)
			}
	)
	@PostMapping("/create")
	public ResponseEntity<RegisterResponseDTO> save(@Valid @RequestBody RegisterRequestDTO dto) {
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		Register entity = this.service.create(this.iRegisterMapper.toEntity(dto));
		RegisterResponseDTO registerDTO = this.iRegisterMapper.toGetDTO(entity);
		return new ResponseEntity<>(registerDTO, HttpStatus.CREATED);
	}

	
	
	
	
	
	@Operation(
			summary = "Find all registers",
			description = "Successfully retrieved all records",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description= "Success retrieve all records",
							content = @Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = RegisterResponseDTO.class))
							)
					)
			}
	)
	@GetMapping("/all")
	public ResponseEntity<List<RegisterResponseDTO>> findAll() {
		List<Register> entities = this.service.getAll();
		List<RegisterResponseDTO> entitiesDTO = this.iRegisterMapper.listEntityToDTO(entities);
		return new ResponseEntity<>(entitiesDTO, HttpStatus.OK);
	}

	
	
	
	
   @Operation(
        summary = "Find a register by id",
        description = "Fetches a single register based on the provided id",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the register",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the register",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RegisterResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Register not found",
                content = @Content(
                    mediaType = "application/json"
                )
            )
        }
    )
	@GetMapping("/{id}")
	public ResponseEntity<RegisterResponseDTO> findById(@PathVariable("id") Long id) {
		Register entity = this.service.readById(id);
		RegisterResponseDTO dto = this.iRegisterMapper.toGetDTO(entity);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	 
	 
	  
	  
	 @Operation(
        summary = "Update a register by id",
        description = "Updates an existing register with the provided details",
        parameters = {
        	@io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the register to be updated",
                required = true,
                example = "1"
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated details of the register",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegisterRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully updated the register",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RegisterResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Register not found",
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
	public ResponseEntity<RegisterResponseDTO> update(@PathVariable("id") Long id,@RequestBody @Valid RegisterRequestDTO dto) {
		dto.setIdRegister(id);
		dto.setIdUser(1L);//FIXME:Obtener el usuario id desde el contexto de seguridad
		Register entity = this.iRegisterMapper.toEntity(dto);
		Register entityUpdated = this.service.update(entity, id);
		return new ResponseEntity<>(this.iRegisterMapper.toGetDTO(entityUpdated), HttpStatus.OK);
	}

	  
	
	  
	 @Operation(
        summary = "Delete a register by id",
        description = "Deletes a register identified by the id",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "id",
                description = "Unique identifier of the register to be deleted",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Successfully deleted the register "
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Register not found"
            )
        }
    )
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
