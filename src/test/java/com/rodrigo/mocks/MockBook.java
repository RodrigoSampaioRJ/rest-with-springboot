package com.rodrigo.mocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rodrigo.dto.BookDto;
import com.rodrigo.model.Book;

public class MockBook {

	public Book mockEntity() {
		return mockEntity(0);
	}

	public BookDto mockDto() {
		return mockDto(0);
	}

	public List<Book> mockEntityList() {
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < 14; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}

	public List<BookDto> mockDtoList() {
		List<BookDto> books = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			books.add(mockDto(i));
		}
		return books;
	}

	public Book mockEntity(Integer number) {
		Book book = new Book();
		book.setAuthor("Author Test" + number);
		book.setTitle("Title Test" + number);
		book.setLaunchDate(new Date());
		book.setId(number.longValue());
		book.setPrice(new BigDecimal(number));
		return book;
	}

	public BookDto mockDto(Integer number) {
		BookDto bookDto = new BookDto();
		bookDto.setAuthor("Author Test" + number);
		bookDto.setTitle("Title Test" + number);
		bookDto.setLaunchDate(new Date());
		bookDto.setKey(number.longValue());
		bookDto.setPrice(new BigDecimal(number));
		return bookDto;
    }
}