package com.rodrigo.infra.beans;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rodrigo.dto.PersonDto;
import com.rodrigo.model.Person;

@Configuration
public class BeanConfiguration {
	
	@Bean
	public ModelMapper getModelMapper() {
		
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<Person, PersonDto>() {

			@Override
			protected void configure() {
				Long id = source.getId(); map().setKey(id);
			}
		});
		return mapper;
		
	}


}
