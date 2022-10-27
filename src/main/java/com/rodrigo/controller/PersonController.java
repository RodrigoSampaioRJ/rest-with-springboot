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
import com.rodrigo.model.Person;
import com.rodrigo.service.PersonService;
import com.rodrigo.util.MediaType;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	public ResponseEntity<PersonDto> create(@RequestBody PersonDto personDto, UriComponentsBuilder uriBuilder){
		
		PersonDto personDtoCreated = personService.create(personDto);
		
		URI uri = uriBuilder.path("person/{id}").buildAndExpand(personDtoCreated.getKey()).toUri();
		
		return ResponseEntity.created(uri).body(personDtoCreated);
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML})
	public List<PersonDto> findAll(){
		
		return personService.findAll();
	}
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML})
	public ResponseEntity<PersonDto> findById(@PathVariable Long id){

		return ResponseEntity.ok().body(personService.findById(id));
		
	}
	
	@PutMapping
	public ResponseEntity<PersonDto> update(@RequestBody PersonDto personDto){
		
		PersonDto personDtoResponse = personService.update(personDto);
		
		return ResponseEntity.ok().body(personDtoResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Person> delete(@PathVariable Long id){
		
		personService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}