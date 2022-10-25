package com.rodrigo.dto;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "lastName", "address", "gender"})
public class PersonDto extends RepresentationModel<PersonDto>{

	@JsonProperty("id")
	private Long key;
	
	private String name;
	
	private String lastName;
	
	private String gender;
	
	private String address;
	
	public PersonDto() {
	}

	public PersonDto(Long key, String name, String lastName, String gender, String address) {
		this.key = key;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.address = address;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	

}