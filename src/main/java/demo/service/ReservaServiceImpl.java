package demo.service;

import demo.model.Horario;
import demo.model.Reserva;
import demo.model.Usuario;
import demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Reserva crearReserva(Usuario usuario, Horario horario) {
        // Verificar si ya existe reserva activa para usuario y horario
        boolean yaReservado = reservaRepository.existsByUsuarioAndHorarioAndEstado(usuario, horario, "ACTIVA");
        if (yaReservado) {
            throw new RuntimeException("Ya tienes una reserva activa para este horario.");
        }

        // Verificar si hay cupo disponible
        long reservasActivas = reservaRepository.countByHorarioAndEstado(horario, "ACTIVA");
        if (reservasActivas >= horario.getCupoMaximo()) {
            throw new RuntimeException("No hay cupo disponible para este horario.");
        }

        // Crear nueva reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setHorario(horario);
        reserva.setEstado("ACTIVA");
        return reservaRepository.save(reserva);
    }

    @Override
    public void cancelarReserva(Long reservaId, Usuario usuario) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!reserva.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No puedes cancelar reservas de otros usuarios.");
        }

        reserva.setEstado("CANCELADA");
        reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> obtenerReservasUsuario(Usuario usuario) {
        return reservaRepository.findByUsuarioAndEstado(usuario, "ACTIVA");
    }

    @Override
    public long contarReservasTotales() {
        return reservaRepository.count();
    }

    @Override
    public Map<String, Long> contarReservasPorClase() {
        return reservaRepository.findAll().stream()
                .filter(r -> "ACTIVA".equals(r.getEstado()))
                .collect(Collectors.groupingBy(
                        r -> r.getHorario().getClase().getNombre(),
                        Collectors.counting()
                ));
    }

    @Override
    public Map<LocalDate, Long> contarReservasPorDia() {
        return reservaRepository.findAll().stream()
                .filter(r -> "ACTIVA".equals(r.getEstado()))
                .collect(Collectors.groupingBy(
                        r -> r.getHorario().getFechaHora().toLocalDate(),
                        Collectors.counting()
                ));
    }
}
