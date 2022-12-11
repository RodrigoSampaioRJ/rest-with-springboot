package com.rodrigo.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.rodrigo.controller.BookController;
import com.rodrigo.dto.BookDto;
import com.rodrigo.infra.exceptions.RequiredObjectIsNullException;
import com.rodrigo.infra.exceptions.ResourceNotFoundException;
import com.rodrigo.model.Book;
import com.rodrigo.repository.BookRepository;
import com.rodrigo.util.ObjectMapperUtils;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	ObjectMapperUtils omu;
	
	public List<BookDto> findAll(){
	
		List<Book> bookList = bookRepository.findAll();
		
		List<BookDto> bookListDto = null;
				
		bookListDto = omu.mapAll(bookList, BookDto.class);
		
		bookListDto
		.stream()
		.forEach(b -> b.add(linkTo(methodOn(BookController.class)
				.findById(b.getKey()))
				.withSelfRel()));
		
		return bookListDto;
	}
	
	public BookDto findById(Long id) {
		
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No book found with this id"));
		
		BookDto bookDto = omu.map(book, BookDto.class);
		
		bookDto.add(linkTo(methodOn(BookController.class)
				.findById(bookDto.getKey()))
				.withSelfRel());
		
		return bookDto;
	}
	
	public BookDto create(BookDto bookDto) {
		
		if(bookDto == null) {
			throw new RequiredObjectIsNullException();
		}
		
		Book book = omu.map(bookDto, Book.class);
		
		BookDto bookDtoCreated = omu.map(bookRepository.save(book), BookDto.class);
		
		bookDtoCreated.add(linkTo(methodOn(BookController.class)
				.findById(bookDtoCreated.getKey()))
				.withSelfRel());
		
		return bookDtoCreated;
	}

	public BookDto update(BookDto bookDtoUpdated) {

		Book book;
		try {
			book = bookRepository.findById(bookDtoUpdated.getKey())
					.orElseThrow(() -> new ResourceNotFoundException("No book found with this id!"));
		} catch (InvalidDataAccessApiUsageException e) {
			throw new RequiredObjectIsNullException();
		}
		
		book.setAuthor(bookDtoUpdated.getAuthor());
		book.setLaunchDate(bookDtoUpdated.getLaunchDate());
		book.setTitle(bookDtoUpdated.getTitle());
		book.setPrice(bookDtoUpdated.getPrice());;

		BookDto bookDto = omu.map(bookRepository.save(book), BookDto.class);
		
		bookDto.add(linkTo(methodOn(BookController.class).findById(bookDto.getKey())).withSelfRel());

		return bookDto;

	}

	public void delete(Long id) {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No book found with this id!"));

		bookRepository.delete(book);

	}
}
