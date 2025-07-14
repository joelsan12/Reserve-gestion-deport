package demo.service;

import demo.model.Horario;
import demo.model.Reserva;
import demo.model.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservaService {
    Reserva crearReserva(Usuario usuario, Horario horario);

    void cancelarReserva(Long reservaId, Usuario usuario);

    List<Reserva> obtenerReservasUsuario(Usuario usuario);

    long contarReservasTotales();

    Map<String, Long> contarReservasPorClase();

    Map<LocalDate, Long> contarReservasPorDia();
}
