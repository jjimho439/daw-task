package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;

public interface TareaRepository extends ListCrudRepository<Tarea, Integer> {
	
	//Obetener el numero total de tareas completadas.
	//Obtener los títulos de las tareas pendientes.
	long countByEstado(Estado estado);
	
	
	//Obtener una lista de las fechas de vencimiento de las tareas que esten en progreso.
	List<Tarea> findByEstado(Estado estado);
	
	
	//Obtener las tareas vencidas
	List<Tarea> findByFechaVencimientoBefore(LocalDate fecha);
	
	//Obtener las tareas no vencidas
	List<Tarea> findByFechaVencimientoAfter(LocalDate fecha);
	
	//Obtener las tareas ordenadas por fecha de vencimiento
	List<Tarea> findAllByOrderByFechaVencimiento();
	
	//Obtener los títulos de las tareas
	List<Tarea> findByTituloContainingIgnoreCase(String string);
	
}