package demo.service;

import demo.model.Clase;
import demo.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseRepository claseRepository;
    
    @Override
    public Optional<Clase> findById(Long id) {
        return claseRepository.findById(id);
    }


    @Override
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    @Override
    public Clase save(Clase clase) {
        return claseRepository.save(clase);
    }

    @Override
    public void deleteById(Long id) {
        claseRepository.deleteById(id);
    }

    @Override
    public Clase obtenerPorId(Long id) {
        Optional<Clase> optional = claseRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Clase no encontrada con id: " + id);
        }
    }
}
