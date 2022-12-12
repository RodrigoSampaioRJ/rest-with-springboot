package com.rodrigo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rodrigo.dto.PersonDto;
import com.rodrigo.service.PersonService;
import com.rodrigo.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/person")
@Tag(name = "People", description = "Endpoint for managing people")
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping
	@Operation(summary = "Creates a new person", description = "Creates a new person",
	tags = {"People" },
	responses = {
		@ApiResponse(description = "Created", responseCode = "201",
				content = @Content(schema = @Schema(implementation = PersonDto.class))
				),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<PersonDto> create(@RequestBody PersonDto personDto, UriComponentsBuilder uriBuilder) {

		PersonDto personDtoCreated = personService.create(personDto);

		URI uri = uriBuilder.path("person/{id}").buildAndExpand(personDtoCreated.getKey()).toUri();

		return ResponseEntity.created(uri).body(personDtoCreated);
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML })
	@Operation(summary = "Finds all registered people", description = "Finds all registered people",
		tags = {"People" },
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PersonDto.class))
						) 
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			})
	public List<PersonDto> findAll() {

		return personService.findAll();
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_YAML })
	@Operation(summary = "Finds a registered person", description = "Finds a registered person",
	tags = {"People" },
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = PersonDto.class))
				),
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<PersonDto> findById(@PathVariable Long id) {

		return ResponseEntity.ok().body(personService.findById(id));

	}

	@PutMapping
	@Operation(summary = "Updates a person", description = "Updates a person",
	tags = {"People" },
	responses = {
		@ApiResponse(description = "Updated", responseCode = "200",
				content = @Content(schema = @Schema(implementation = PersonDto.class))
				),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<PersonDto> update(@RequestBody PersonDto personDto) {

		PersonDto personDtoResponse = personService.update(personDto);

		return ResponseEntity.ok().body(personDtoResponse);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes a person", description = "Deletes a person",
	tags = {"People" },
	responses = {
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<?> delete(@PathVariable Long id) {

		personService.delete(id);

		return ResponseEntity.noContent().build();
	}

}