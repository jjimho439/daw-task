package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;

@Service
public class TareaService {

	@Autowired
	private TareaRepository tareaRepository;

	public List<Tarea> findAll() {

		return this.tareaRepository.findAll();

	}

	public Tarea findById(int idTarea) {

		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No existe la tarea con ID: " + idTarea);

		}

		return this.tareaRepository.findById(idTarea).get();

	}

	public boolean existsById(int idTarea) {

		return this.tareaRepository.existsById(idTarea);

	}

	public void deleteById(int idTarea) {
		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("La tarea con ID: " + idTarea + " no existe");
		}
	}

	public Tarea create(Tarea tarea) {
		tarea.setFechaCreacion(LocalDate.now());
		tarea.setEstado(Estado.PENDIENTE);
		if (tarea.getFechaVencimiento().isBefore(tarea.getFechaCreacion())) {
			throw new TareaException("La fecha de Vencimiento debe ser posterior a la de hoy");
		}
		return this.tareaRepository.save(tarea);
	}

	public Tarea update(int idTarea, Tarea tarea) {
		if (idTarea != tarea.getId()) {
			throw new TareaException(
					"El ID del path (" + idTarea + ") y el id del body (" + tarea.getId() + ") no coinciden");
		}
		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No se ha encontrado la tarea con ID: " + idTarea);
		}
		if (tarea.getFechaCreacion() != null) {
			throw new TareaException("No se permite modificar la fecha de creación.");
		}
		if (tarea.getEstado() != null) {
			throw new TareaException("No se permite modificar el Estado");
		}
		if (tarea.getFechaVencimiento().isBefore(tarea.getFechaCreacion())) {
			throw new TareaException("La fecha de Vencimiento debe ser posterior a la de hoy");
		}

		Tarea tareaBD = this.findById(tarea.getId());
		tareaBD.setTitulo(tarea.getTitulo());
		tareaBD.setDescripcion(tarea.getDescripcion());
		tareaBD.setFechaVencimiento(tarea.getFechaVencimiento());

		return this.tareaRepository.save(tareaBD);
	}

	public boolean deleteDeclarativo(int idTarea) {
		boolean result = false;
		if (this.tareaRepository.existsById(idTarea)) {
			this.tareaRepository.deleteById(idTarea);
			result = true;
		}
		return result;
	}

	public boolean deleteFuncional(int idTarea) {
		return this.tareaRepository.findById(idTarea).map(t -> {
			this.tareaRepository.deleteById(idTarea);
			return true;
		}).orElse(false);
	}

	public Tarea findByIdFuncional(int idTarea) {
		return this.tareaRepository.findById(idTarea)
				.orElseThrow(() -> new TareaNotFoundException("No se ha encontrado la tarea con ID: " + idTarea));
	}

	// EJEMPLOS STREAMS

	// Obtener el numero total de tareas completadas
	public long totalTareasCompletadasFuncional() {
		return this.tareaRepository.findAll().stream().filter(t -> t.getEstado() == Estado.COMPLETADA).count();
	}

	public long totalTareasCompletadas() {
		return this.tareaRepository.countByEstado(Estado.COMPLETADA);
	}

	// Obtener una lista de las fechas de vencimiento de las tareas que esten en
	// progreso
	public List<LocalDate> fechaVencimientoEnProgresoFuncional() {
		return this.tareaRepository.findAll().stream().filter(t -> t.getEstado() == Estado.EN_PROGRESO)
				.map(t -> t.getFechaVencimiento()).collect(Collectors.toList());
	}

	public List<LocalDate> fechasVencimientoEnProgreso() {
		return this.tareaRepository.findByEstado(Estado.EN_PROGRESO).stream().map(t -> t.getFechaVencimiento())
				.collect((Collectors.toList()));
	}

	// Obtener las tareas vencidas
	public List<Tarea> totalTareasVencidasFuncional() {
		return this.tareaRepository.findAll().stream().filter(t -> t.getFechaVencimiento().isBefore(LocalDate.now()))
				.collect(Collectors.toList());
	}

	public List<Tarea> totalTareasVencidas() {
		return this.tareaRepository.findByFechaVencimientoBefore(LocalDate.now());
	}

	// Obtener los titulos de las tareas pendientes
	public void titulosTareasPendientesFuncional() {
		this.tareaRepository.findAll().stream().filter(t -> t.getEstado() == Estado.PENDIENTE).map(t -> t.getTitulo())
				.forEach(t -> System.out.println(t));
	}

	public List<String> titulosTareasPendientes() {
		return this.tareaRepository.findByEstado(Estado.EN_PROGRESO).stream().map(t -> t.getTitulo())
				.collect(Collectors.toList());
	}

	// Obtener las tareas ordenadas por fecha de vencimiento
	public List<Tarea> tareasOrdenadasFechaVencimientoFuncional() {
		return this.tareaRepository.findAll().stream()
				.sorted((t1, t2) -> t1.getFechaVencimiento().compareTo(t2.getFechaVencimiento()))
				.collect(Collectors.toList());
	}

	public List<Tarea> tareasOrdenadasFechaVencimiento() {
		return this.tareaRepository.findAllByOrderByFechaVencimiento();
	}

	// Iniciar una tarea (solo se pueden iniciar tareas PENDIENTES)
	public Tarea iniciarTarea(int idTarea) {
		Tarea tarea = this.findById(idTarea);
		if (this.tareaRepository.findById(idTarea).get().getEstado() != Estado.PENDIENTE) {
			throw new TareaException("La tarea tiene que estar en estado PENDIENTE");
		}
		tarea.setEstado(Estado.EN_PROGRESO);
		return this.tareaRepository.save(tarea);
	}

	// Completar una tarea (solo se pueden completar tareas EN_PROGRESO)
	public Tarea completarTarea(int idTarea) {
		Tarea tarea = this.findById(idTarea);
		if (this.tareaRepository.findById(idTarea).get().getEstado() != Estado.EN_PROGRESO) {
			throw new TareaException("La tarea tiene que estar en estado PENDIENTE");
		}
		tarea.setEstado(Estado.COMPLETADA);
		return this.tareaRepository.save(tarea);
	}

	// Obtener las tareas pendientes
	public List<Tarea> obtenerTareasPendientes() {
		return this.tareaRepository.findByEstado(Estado.PENDIENTE);
	}

	// Obtener las tareas en progreso
	public List<Tarea> obtenerTareasEnProgreso() {
		return this.tareaRepository.findByEstado(Estado.EN_PROGRESO);
	}

	// Obtener las tareas completadas
	public List<Tarea> obtenerTareasCompletadas() {
		return this.tareaRepository.findByEstado(Estado.COMPLETADA);
	}

	// Obtener las tareas vencidas (fecha de vencimiento menos que la de hoy)
	public List<Tarea> obtenerTareasVencidas() {
		return this.tareaRepository.findByFechaVencimientoBefore(LocalDate.now());
	}

	// Obtener las tareas no vencidas (fecha de vencimiento mayor que la de hoy)
	public List<Tarea> obtenerTareasNoVencidas() {
		return this.tareaRepository.findByFechaVencimientoAfter(LocalDate.now());
	}

	// Obtener tareas mediante su título (que contenga el String que se pasa como título)
	public List<Tarea> obtenerTareasTitulo(String titulo) {
		return this.tareaRepository.findByTituloContainingIgnoreCase(titulo);
	}

}
