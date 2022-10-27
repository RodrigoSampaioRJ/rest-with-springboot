package com.rodrigo.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.rodrigo.controller.PersonController;
import com.rodrigo.dto.PersonDto;
import com.rodrigo.infra.exceptions.RequiredObjectIsNullException;
import com.rodrigo.infra.exceptions.ResourceNotFoundException;
import com.rodrigo.model.Person;
import com.rodrigo.repository.PersonRepository;
import com.rodrigo.util.ObjectMapperUtils;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	ObjectMapperUtils omu;

	public List<PersonDto> findAll() {
		
		List<Person> peopleList = personRepository.findAll();
		
		List<PersonDto> peopleListDto = null;
		
		peopleListDto = omu.mapAll(peopleList, PersonDto.class);
		
		peopleListDto
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		return peopleListDto;
	}

	public PersonDto findById(Long id) {

		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));

		PersonDto personDto = omu.map(person, PersonDto.class);
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

		return personDto;
	}

	public PersonDto create(PersonDto personDto) {
		
		if (personDto == null) {
			throw new RequiredObjectIsNullException();
		}

		Person person = omu.map(personDto, Person.class);		

		PersonDto personDtoCreated = omu.map(personRepository.save(person), PersonDto.class);
		
		personDtoCreated.add(linkTo(methodOn(PersonController.class).findById(personDtoCreated.getKey())).withSelfRel());

		return personDtoCreated;
	}

	public PersonDto update(PersonDto personDtoUpdated) {

		Person person;
		try {
			person = personRepository.findById(personDtoUpdated.getKey())
					.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));
		} catch (InvalidDataAccessApiUsageException e) {
			throw new RequiredObjectIsNullException();
		}
		
		person.setAddress(personDtoUpdated.getAddress());
		person.setGender(personDtoUpdated.getGender());
		person.setLastName(personDtoUpdated.getLastName());
		person.setName(personDtoUpdated.getName());;

		PersonDto personDto = omu.map(personRepository.save(person), PersonDto.class);
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getKey())).withSelfRel());

		return personDto;

	}

	public void delete(Long id) {

		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));

		personRepository.delete(person);

	}



}