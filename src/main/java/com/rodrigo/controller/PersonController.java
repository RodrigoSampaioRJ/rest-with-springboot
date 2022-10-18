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
import com.rodrigo.dto.PersonFormDto;
import com.rodrigo.model.Person;
import com.rodrigo.service.PersonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	public ResponseEntity<Person> create(@RequestBody @Valid PersonFormDto personFormDto, UriComponentsBuilder uriBuilder){
		
		Person person = personService.create(personFormDto);
		
		URI uri = uriBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri();
		
		return ResponseEntity.created(uri).body(person);
	}
	
	@GetMapping
	public List<PersonDto> findAll(){
		
		return personService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonDto> findById(@PathVariable Long id){

		return ResponseEntity.ok().body(personService.findById(id));
		
	}
	
	@PutMapping
	public ResponseEntity<Person> update(@RequestBody @Valid Person personUpdated){
		
		personService.update(personUpdated);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Person> delete(@PathVariable Long id){
		
		personService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}