package demo.service;

import demo.model.Horario;
import demo.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Horario> obtenerTodos() {
        return horarioRepository.findAll();
    }

    @Override
    public Horario guardar(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public void eliminar(Long id) {
        horarioRepository.deleteById(id);
    }

    @Override
    public List<Horario> findByClaseAndFecha(Long claseId, LocalDate fecha) {
        return horarioRepository.findByClaseIdAndFechaHoraBetween(
                claseId,
                fecha.atStartOfDay(),
                fecha.plusDays(1).atStartOfDay());
    }

    @Override
    public Horario obtenerPorId(Long id) {
        return horarioRepository.findById(id).orElse(null);
    }
}
