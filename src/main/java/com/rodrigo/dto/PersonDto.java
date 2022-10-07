package com.rodrigo.dto;

public class PersonDto {
	
	private Long id;
	
	private String name;
	
	private String lastName;
	
	private String gender;
	
	private String address;

	public PersonDto(Long id, String name, String lastName, String gender, String address) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.address = address;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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