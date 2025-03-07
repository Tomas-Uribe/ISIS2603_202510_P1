package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

// Punto 1 (20%) Entidades y persistencia
// Usted acaba de ser contratado por una empresa que se encarga de gestionar conciertos a lo largo de toda Colombia. El proyecto inició recientemente y ya se encuentran desarrollados las entidades Concierto y Estadio. Su tarea es continuar con el proceso iniciado, para esto tenga en cuenta:

// (10%) Complete las entidades con los siguientes atributos:
// Concierto: nombre del artista, fecha del concierto y capacidad de aforo del concierto.
// Estadio: nombre de la ciudad del estadio y capacidad máxima del estadio.
// (10%) Implemente la relación entre Estadio y Concierto asegurándose que un concierto tenga un solo estadio asignado pero un estadio pueda tener varios conciertos programados.

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;
    private String fecha;
    private Integer capacidadAforo;
 
    @PodamExclude
    @OneToOne(mappedBy = "concierto")
    private EstadioEntity estadio;
}
