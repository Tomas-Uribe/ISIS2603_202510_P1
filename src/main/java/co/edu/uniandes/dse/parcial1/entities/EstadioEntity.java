package co.edu.uniandes.dse.parcial1.entities;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

//Implemente la relación entre Estadio y Concierto asegurándose que un concierto tenga un solo estadio asignado pero un estadio pueda tener varios conciertos programados.


@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long precioAlquiler;
    private String ciudad;
    private Integer capacidadMaxima;

    @PodamExclude
    @OneToMany(mappedBy = "estadio")
    private List<ConciertoEntity> conciertos = new ArrayList<>();

}
