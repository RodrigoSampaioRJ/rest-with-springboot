package com.rodrigo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.dto.PersonDto;
import com.rodrigo.dto.PersonFormDto;
import com.rodrigo.infra.ResourceNotFoundException;
import com.rodrigo.model.Person;
import com.rodrigo.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	
	@Autowired
	private ModelMapper mapper;
	
	
	public List<PersonDto> findAll(){
		
		return personRepository.findAll()
				.stream().map(p -> mapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}
	
	
	public PersonDto findById(Long id) {
		
		Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));
		
		PersonDto personDto = mapper.map(person, PersonDto.class);
		
		return personDto;
	}
	
	public Person create(PersonFormDto personFormDto) {
		
		Person person = mapper.map(personFormDto, Person.class);
		
		return personRepository.save(person);
	}
	
	public Person update(Person personUpdated) {
		
		personRepository.findById(personUpdated.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));
		
		return personRepository.save(personUpdated);
		
	}
	
	public void delete(Long id) {
		
		Person person = personRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));
		
		personRepository.delete(person);
		
	}
	
	
	
	
	
	
	
	
	
	
	

}