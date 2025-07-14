package demo.service;

import demo.model.Clase;

import java.util.List;
import java.util.Optional;

public interface ClaseService {
    List<Clase> findAll();
    Clase save(Clase clase);
    Optional<Clase> findById(Long id);
    void deleteById(Long id);
}
