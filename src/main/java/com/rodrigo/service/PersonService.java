package com.rodrigo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	public List<Person> findAll(){
		
		return personRepository.findAll();
	}
	
	
	public Person findById(Long id) {
		
		return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));
	}
	
	
	public Person create(PersonFormDto personFormDto) {
		
		Person person = mapper.map(personFormDto, Person.class);
		
		return personRepository.save(person);
	}
	
	public Person update(Person person) {
		
		var entity = personRepository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No user found with this id!"));
		
		return personRepository.save(entity);
		
	}
	
	
	
	
	
	
	
	
	
	
	

}