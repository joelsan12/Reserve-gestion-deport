package demo.repository;

import demo.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByClaseIdAndFechaHoraBetween(Long claseId, LocalDateTime inicio, LocalDateTime fin);

}
