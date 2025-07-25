package demo.service;

import demo.model.Instructor;
import demo.repository.HorarioRepository;
import demo.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    public Instructor findById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + id));
    }

    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteById(Long id) {
        boolean tieneHorarios = horarioRepository.existsByInstructorId(id);
        if (tieneHorarios) {
            throw new RuntimeException("No se puede eliminar. El instructor tiene horarios asignados.");
        }
        instructorRepository.deleteById(id);
    }
}
