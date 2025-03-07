package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity asociarConciertoAEstadio(Long conciertoId, Long estadioId) throws IllegalArgumentException {
        log.info("Inicia proceso de asociación de concierto a estadio");

        ConciertoEntity concierto = conciertoRepository.findById(conciertoId)
                .orElseThrow(() -> new IllegalArgumentException("Concierto no encontrado"));
        EstadioEntity estadio = estadioRepository.findById(estadioId)
                .orElseThrow(() -> new IllegalArgumentException("Estadio no encontrado"));

        if (concierto.getCapacidadAforo() > estadio.getCapacidadMaxima()) {
            throw new IllegalArgumentException("La capacidad del concierto no puede superar la capacidad del estadio");
        }

        if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()) {
            throw new IllegalArgumentException("El precio de alquiler del estadio no puede superar el presupuesto del concierto");
        }

        LocalDateTime fechaConcierto = LocalDateTime.parse(concierto.getFecha());
        for (ConciertoEntity c : estadio.getConciertos()) {
            LocalDateTime fechaExistente = LocalDateTime.parse(c.getFecha());
            if (Duration.between(fechaExistente, fechaConcierto).toDays() < 2) {
                throw new IllegalArgumentException("Debe existir un tiempo mínimo de 2 días entre los conciertos asociados a un estadio");
            }
        }

        concierto.setEstadio(estadio);
        estadio.getConciertos().add(concierto);

        conciertoRepository.save(concierto);
        estadioRepository.save(estadio);

        log.info("Finaliza proceso de asociación de concierto a estadio");
        return concierto;
    }
}
