package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
	
	@Autowired
	private CityRepository repository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll(){
		List<City> result = repository.findAll(Sort.by("name"));
		return result.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CityDTO insert(CityDTO obj) {
		City entity = new City();
		entity.setName(obj.getName());
		entity = repository.save(entity);
		return new CityDTO(entity);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
