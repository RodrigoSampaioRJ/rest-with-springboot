package com.rodrigo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
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

import com.rodrigo.dto.BookDto;
import com.rodrigo.infra.exceptions.RequiredObjectIsNullException;
import com.rodrigo.mocks.MockBook;
import com.rodrigo.model.Book;
import com.rodrigo.repository.BookRepository;
import com.rodrigo.util.ObjectMapperUtils;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookServiceTest {
	
	
	MockBook input;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	BookRepository repository;
	
	@Mock
	ObjectMapperUtils omu;

	@BeforeEach
	void setUp() throws Exception {

		input = new MockBook();
		
		MockitoAnnotations.openMocks(this);
		
	}

	@Test
	void testFindAll() {
		List<Book> booksList = input.mockEntityList();
		List<BookDto> bookListDto = input.mockDtoList();
		
		when(repository.findAll()).thenReturn(booksList);
		when(omu.mapAll(booksList, BookDto.class)).thenReturn(bookListDto);
		
		//System.out.println(bookListDto.size());

		var result = service.findAll();
		
		assertNotNull(result);
		assertEquals(14, result.size());
		
		var resultOne = result.get(1);
		
		assertNotNull(resultOne);
		assertNotNull(resultOne.getKey());
		assertNotNull(resultOne.getLinks());
		assertTrue(resultOne.toString().contains("links: [</book/1>;rel=\"self\"]"));
		assertEquals("Title Test1", resultOne.getTitle());
		assertEquals("Author Test1", resultOne.getAuthor());
		assertEquals(new Date(1), resultOne.getLaunchDate());
		assertEquals(new BigDecimal(1), resultOne.getPrice());
		
		var resultFive = result.get(5);
		
		assertNotNull(resultFive);
		assertNotNull(resultFive.getKey());
		assertNotNull(resultFive.getLinks());
		assertTrue(resultFive.toString().contains("links: [</book/5>;rel=\"self\"]"));
		assertEquals("Title Test5", resultFive.getTitle());
		assertEquals("Author Test5", resultFive.getAuthor());
		assertEquals(new Date(5), resultFive.getLaunchDate());
		assertEquals(new BigDecimal(5), resultFive.getPrice());

		

	}

	@Test
	void testFindById() {
		Book book = input.mockEntity(1);
		BookDto bookDto = input.mockDto(1);
		book.setId(1l);
		bookDto.setKey(1l);
		
		when(repository.findById(1l)).thenReturn(Optional.of(book));
		when(omu.map(book, BookDto.class)).thenReturn(bookDto);
		
		var result = service.findById(1l);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</book/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(new Date(1), result.getLaunchDate());
		assertEquals(new BigDecimal(1), result.getPrice());


	}

	@Test
	void testCreate() {
		Book book = input.mockEntity(1);
		Book bookCreated = book;
		bookCreated.setId(1l);
		
		BookDto bookDto = input.mockDto(1);
		bookDto.setKey(1l);
		
		BookDto bookDtoCreated = input.mockDto(1);
		bookDtoCreated.setKey(1l);
		
		when(omu.map(bookDto, Book.class)).thenReturn(book);
		when(repository.save(book)).thenReturn(bookCreated);
		when(omu.map(bookCreated, BookDto.class)).thenReturn(bookDtoCreated);

		
		var result = service.create(bookDtoCreated);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</book/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(new Date(1), result.getLaunchDate());
		assertEquals(new BigDecimal(1), result.getPrice());

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
		Book book = input.mockEntity(1);
		book.setId(1l);
		
		Book bookCreated = book;
		bookCreated.setId(1l);
		
		BookDto bookDto = input.mockDto(1);
		bookDto.setKey(1l);
		
		BookDto bookDtoCreated = input.mockDto(1);
		
		when(repository.findById(1l)).thenReturn(Optional.of(book));
		when(omu.map(bookDto, Book.class)).thenReturn(book);
		when(repository.save(book)).thenReturn(bookCreated);
		when(omu.map(bookCreated, BookDto.class)).thenReturn(bookDtoCreated);

		
		var result = service.update(bookDto);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</book/1>;rel=\"self\"]"));

	}

	@Test
	void testDelete() {
		
	}

}
