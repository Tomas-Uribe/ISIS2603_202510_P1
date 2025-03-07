package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Return;

@Slf4j
@Service
public class ConciertoService {

@Autowired
private ConciertoRepository conciertoRepository;

@Transactional
public ConciertoEntity createConcierto(ConciertoEntity concierto) throws IllegalArgumentException {
    log.info("Inicia proceso de creación de concierto");
    LocalDateTime fecha_actual = LocalDateTime.now();
    LocalDateTime fechaConcierto = LocalDateTime.parse(concierto.getFecha());
    if (Duration.between(fecha_actual, fechaConcierto).toDays() < 0) {
        throw new IllegalArgumentException("La fecha del concierto no puede ser anterior a la fecha actual");
    }
    return conciertoRepository.save(concierto);
}

}
