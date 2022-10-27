package com.rodrigo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.rodrigo.dto.PersonDto;
import com.rodrigo.infra.exceptions.RequiredObjectIsNullException;
import com.rodrigo.mocks.MockPerson;
import com.rodrigo.model.Person;
import com.rodrigo.repository.PersonRepository;
import com.rodrigo.util.ObjectMapperUtils;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PersonServiceTest {
	
	
	MockPerson input;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PersonRepository repository;
	
	@Mock
	ObjectMapperUtils omu;

	@BeforeEach
	void setUp() throws Exception {

		input = new MockPerson();
		
		MockitoAnnotations.openMocks(this);
		
	}

	@Test
	void testFindAll() {
		List<Person> peopleList = input.mockEntityList();
		List<PersonDto> peopleListDto = input.mockDtoList();
		
		when(repository.findAll()).thenReturn(peopleList);
		when(omu.mapAll(peopleList, PersonDto.class)).thenReturn(peopleListDto);
		
		System.out.println(peopleListDto.size());

		var result = service.findAll();
		
		assertNotNull(result);
		assertEquals(14, result.size());
		
		var resultOne = result.get(1);
		
		assertNotNull(resultOne);
		assertNotNull(resultOne.getKey());
		assertNotNull(resultOne.getLinks());
		assertTrue(resultOne.toString().contains("links: [</person/1>;rel=\"self\"]"));
		assertEquals("Address Test1", resultOne.getAddress());
		assertEquals("First Name Test1", resultOne.getName());
		assertEquals("Last Name Test1", resultOne.getLastName());
		assertEquals("Female", resultOne.getGender());
		
		

	}

	@Test
	void testFindById() {
		Person person = input.mockEntity(1);
		PersonDto personDto = input.mockDto(1);
		person.setId(1l);
		personDto.setKey(1l);
		
		when(repository.findById(1l)).thenReturn(Optional.of(person));
		when(omu.map(person, PersonDto.class)).thenReturn(personDto);
		
		var result = service.findById(1l);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());


	}

	@Test
	void testCreate() {
		Person person = input.mockEntity(1);
		Person personCreated = person;
		personCreated.setId(1l);
		
		PersonDto personDto = input.mockDto(1);
		personDto.setKey(1l);
		
		PersonDto personDtoCreated = input.mockDto(1);
		personDtoCreated.setKey(1l);
		
		when(omu.map(personDto, Person.class)).thenReturn(person);
		when(repository.save(person)).thenReturn(personCreated);
		when(omu.map(personCreated, PersonDto.class)).thenReturn(personDtoCreated);

		
		var result = service.create(personDtoCreated);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testCreateWithNullObject() {
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "It is not allowed to persist a null objetct!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testUpdate() {
		Person person = input.mockEntity(1);
		person.setId(1l);
		
		Person personCreated = person;
		personCreated.setId(1l);
		
		PersonDto personDto = input.mockDto(1);
		personDto.setKey(1l);
		
		PersonDto personDtoCreated = input.mockDto(1);
		
		when(repository.findById(1l)).thenReturn(Optional.of(person));
		when(omu.map(personDto, Person.class)).thenReturn(person);
		when(repository.save(person)).thenReturn(personCreated);
		when(omu.map(personCreated, PersonDto.class)).thenReturn(personDtoCreated);

		
		var result = service.update(personDto);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/1>;rel=\"self\"]"));

	}

	@Test
	void testDelete() {
		
	}

}
