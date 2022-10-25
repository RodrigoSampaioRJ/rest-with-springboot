package com.rodrigo.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.controller.PersonController;
import com.rodrigo.dto.PersonDto;
import com.rodrigo.infra.ResourceNotFoundException;
import com.rodrigo.model.Person;
import com.rodrigo.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ModelMapper mapper;

	public List<PersonDto> findAll() {
		
		var listDto = personRepository.findAll().stream().map(p -> mapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
		
		listDto
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		return listDto;
	}

	public PersonDto findById(Long id) {

		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));

		PersonDto personDto = mapper.map(person, PersonDto.class);
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

		return personDto;
	}

	public PersonDto create(PersonDto personDto) {

		Person person = mapper.map(personDto, Person.class);		

		PersonDto personDtoCreated = mapper.map(personRepository.save(person), PersonDto.class);
		
		personDtoCreated.add(linkTo(methodOn(PersonController.class).findById(personDtoCreated.getKey())).withSelfRel());

		return personDtoCreated;
	}

	public PersonDto update(Person personUpdated) {

		Person person = personRepository.findById(personUpdated.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));
		
		personRepository.save(personUpdated);
		
		PersonDto personDto = mapper.map(person, PersonDto.class);
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getKey())).withSelfRel());

		return personDto;

	}

	public void delete(Long id) {

		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));

		personRepository.delete(person);

	}



}