package com.daw.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;


@Service
public class TareaService {
	
	//@Autowired
	// private TareaRepository tareaRepository
	
	
	 public List <Tarea> findAll(){
		 List<Tarea> listaTareas = new ArrayList<>();
			
	        //return this.TareaRepository.findAll();
		 return null;
	 }
}
