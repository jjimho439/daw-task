package com.daw.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

public interface Tarea {

	public interface TareaRepository extends ListCrudRepository<Tarea, Integer>{
		
	}
}
