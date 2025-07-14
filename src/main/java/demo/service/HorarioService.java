package demo.service;

import demo.model.Horario;

import java.time.LocalDate;
import java.util.List;

public interface HorarioService {
    List<Horario> obtenerTodos();
    Horario guardar(Horario horario);
    void eliminar(Long id);
    List<Horario> findByClaseAndFecha(Long claseId, LocalDate fecha);
    Horario obtenerPorId(Long id);
}
