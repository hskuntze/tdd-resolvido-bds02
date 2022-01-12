package com.devsuperior.bds02.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO event) {
		try {
			Event obj = repository.getOne(id);
			Optional<City> result = cityRepository.findById(event.getCityId());
			obj.setName(event.getName());
			obj.setUrl(event.getUrl());
			obj.setCity(result.get());
			obj.setDate(event.getDate());
			obj = repository.save(obj);
			return new EventDTO(obj);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}
}
