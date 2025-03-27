package com.daw.web.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;

@RestController
@RequestMapping("/tareas")
public class TareaController {

	@GetMapping
	public List<Tarea> list(){
		List<Tarea> listaTareas = new ArrayList<>();
		
		Tarea tarea1 = new Tarea(1, "Tarea de ejemplo 1", "Descripción de la tarea 1", LocalDate.now(), LocalDate.now().plusDays(7), Estado.PENDIENTE);
        Tarea tarea2 = new Tarea(2, "Tarea de ejemplo 2", "Descripción de la tarea 2", LocalDate.now(), LocalDate.now().plusDays(14), Estado.EN_PROGRESO);
        Tarea tarea3 = new Tarea(3, "Tarea de ejemplo 3", "Descripción de la tarea 3", LocalDate.now(), LocalDate.now().plusDays(21), Estado.COMPLETADA);
		
        listaTareas.add(tarea1);
        listaTareas.add(tarea2);
        listaTareas.add(tarea3);
        
        return listaTareas;
	}
	
}
