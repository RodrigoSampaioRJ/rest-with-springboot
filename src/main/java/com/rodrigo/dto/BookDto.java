package com.rodrigo.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "author", "launchDate", "price", "title"})
public class BookDto extends RepresentationModel<BookDto>{

	@JsonProperty("id")
	private Long key;
	
	private String author;

	private Date launchDate;

	private BigDecimal price;
	
	private String title;
	
	public BookDto() {
	}
	

	public BookDto(Long key, String author, Date launchDate, BigDecimal price, String title) {
		this.key = key;
		this.author = author;
		this.launchDate = launchDate;
		this.price = price;
		this.title = title;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(key);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDto other = (BookDto) obj;
		return Objects.equals(key, other.key);
	}


	



	
	
	
	
	
	
}
