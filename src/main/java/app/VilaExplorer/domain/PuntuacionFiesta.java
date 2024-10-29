package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "puntuacion_fiesta")
public class PuntuacionFiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPuntuacion;

    @ManyToOne
    @JoinColumn(name = "id_fiesta", nullable = false, foreignKey = @ForeignKey(name = "FK_puntuacion_fiesta_fiesta_tradicion")) // Corrected foreign key constraint
    private FiestaTradicion fiesta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_puntuacion_fiesta_usuario"))
    private Usuario usuario;

    @Column(nullable = false)
    private Integer puntuacion;
}
