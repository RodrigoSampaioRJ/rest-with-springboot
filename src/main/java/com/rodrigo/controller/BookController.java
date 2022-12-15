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

import com.rodrigo.dto.BookDto;
import com.rodrigo.service.BookService;
import com.rodrigo.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/book")
@Tag(name = "Books", description = "Endpoint for managing books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping
	@Operation(summary = "Creates a new book", description = "Creates a new book",
	tags = {"Books" },
	responses = {
		@ApiResponse(description = "Created", responseCode = "201",
				content = @Content(schema = @Schema(implementation = BookDto.class))
				),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto, UriComponentsBuilder uriBuilder) {
		
		BookDto bookDtoCreated = bookService.create(bookDto);
		
		URI uri = uriBuilder.path("book/id").buildAndExpand(bookDtoCreated.getKey()).toUri();
		
		return ResponseEntity.created(uri).body(bookDtoCreated);
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML})
	@Operation(summary = "Finds all registered books", description = "Finds all registered books",
	tags = {"Books" },
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
					) 
				}),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public List<BookDto> findAll(){
		
		return bookService.findAll();
		
	}
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML})
	@Operation(summary = "Finds a registered book", description = "Finds a registered book",
	tags = {"Books" },
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = BookDto.class))
				),
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<BookDto> findById(@PathVariable Long id) {
		
		return ResponseEntity.ok().body(bookService.findById(id));
		
	}
	
	@PutMapping
	@Operation(summary = "Updates a book", description = "Updates a book",
	tags = {"Books" },
	responses = {
		@ApiResponse(description = "Updated", responseCode = "200",
				content = @Content(schema = @Schema(implementation = BookDto.class))
				),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<BookDto> update(@RequestBody BookDto bookDtoUpdated){
		
		BookDto bookDto = bookService.update(bookDtoUpdated);
		
		return ResponseEntity.ok().body(bookDto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes a book", description = "Deletes a book",
	tags = {"Books" },
	responses = {
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<?> delete(@PathVariable Long id ){
		
		bookService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
