package app.VilaExplorer.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PuntuacionPlatoID.class)
@Table(name = "puntuacion_plato")
public class PuntuacionPlato {
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_plato", nullable = false)
    private Plato plato;

    @Column(nullable = false)
    private int puntuacion;
}