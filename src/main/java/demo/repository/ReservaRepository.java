package demo.repository;

import demo.model.Horario;
import demo.model.Reserva;
import demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByUsuarioAndHorarioAndEstado(Usuario usuario, Horario horario, String estado);

    long countByHorarioAndEstado(Horario horario, String estado);

    List<Reserva> findByUsuarioAndEstado(Usuario usuario, String estado);

}
