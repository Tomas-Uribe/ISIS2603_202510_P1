package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;





public class CreacionConciertoTest {

    @Autowired
    private ConciertoEstadioService conciertoEstadioService;

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Test
    public void testCrearConciertoExitoso() {
        EstadioEntity estadio = new EstadioEntity();
        estadio.setNombre("Estadio Nemesio Camacho El Campin");
        estadio.setCapacidadMaxima(600000);
        estadio.setPrecioAlquiler(1000000L);
        estadioRepository.save(estadio);

        ConciertoEntity concierto = new ConciertoEntity();
        concierto.setNombre("Concierto de conciertos");
        concierto.setPresupuesto(3000000L);
        concierto.setFecha(LocalDateTime.now().plusDays(10).toString());
        concierto.setCapacidadAforo(40000);
        conciertoRepository.save(concierto);

        ConciertoEntity result = conciertoEstadioService.asociarConciertoAEstadio(concierto.getId(), estadio.getId());
        assertNotNull(result);
        assertNotNull(result.getEstadio());
    }

    @Test
    public void testCrearConciertoFallaPorCapacidad() {
        EstadioEntity estadio = new EstadioEntity();
        estadio.setNombre("Estadio el campin");
        estadio.setCapacidadMaxima(20000);
        estadio.setPrecioAlquiler(1000000L);
        estadioRepository.save(estadio);

        ConciertoEntity concierto = new ConciertoEntity();
        concierto.setNombre("Shakira");
        concierto.setPresupuesto(2000000L);
        concierto.setFecha(LocalDateTime.now().plusDays(10).toString());
        concierto.setCapacidadAforo(30000);
        conciertoRepository.save(concierto);

        assertThrows(IllegalArgumentException.class, () -> {
            conciertoEstadioService.asociarConciertoAEstadio(concierto.getId(), estadio.getId());
        });
    }

    @Test
    public void testCrearConciertoFallaPorPresupuesto() {
        EstadioEntity estadio = new EstadioEntity();
        estadio.setNombre("Estadio Nacional");
        estadio.setCapacidadMaxima(50000);
        estadio.setPrecioAlquiler(3000000L);
        estadioRepository.save(estadio);

        ConciertoEntity concierto = new ConciertoEntity();
        concierto.setNombre("Concierto de Rock");
        concierto.setPresupuesto(2000000L);
        concierto.setFecha(LocalDateTime.now().plusDays(10).toString());
        concierto.setCapacidadAforo(30000);
        conciertoRepository.save(concierto);

        assertThrows(IllegalArgumentException.class, () -> {
            conciertoEstadioService.asociarConciertoAEstadio(concierto.getId(), estadio.getId());
        });
    }
}
