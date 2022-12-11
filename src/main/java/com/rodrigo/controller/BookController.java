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

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping
	public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto, UriComponentsBuilder uriBuilder) {
		
		BookDto bookDtoCreated = bookService.create(bookDto);
		
		URI uri = uriBuilder.path("book/id").buildAndExpand(bookDtoCreated.getKey()).toUri();
		
		return ResponseEntity.created(uri).body(bookDtoCreated);
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML})
	public List<BookDto> findAll(){
		
		return bookService.findAll();
		
	}
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML})
	public ResponseEntity<BookDto> findById(@PathVariable Long id) {
		
		return ResponseEntity.ok().body(bookService.findById(id));
		
	}
	
	@PutMapping
	public ResponseEntity<BookDto> update(@RequestBody BookDto bookDtoUpdated){
		
		BookDto bookDto = bookService.update(bookDtoUpdated);
		
		return ResponseEntity.ok().body(bookDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BookDto> delete(@PathVariable Long id ){
		
		bookService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
