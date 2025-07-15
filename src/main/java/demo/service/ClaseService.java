package demo.service;

import demo.model.Clase;

import java.util.List;
import java.util.Optional;

public interface ClaseService {

	Optional<Clase> findById(Long id);

	
    List<Clase> findAll();

    Clase save(Clase clase);

    void deleteById(Long id);

    // Método personalizado que devuelve Clase o lanza excepción si no existe
    Clase obtenerPorId(Long id);
}
