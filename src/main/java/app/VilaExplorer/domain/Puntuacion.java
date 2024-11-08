package app.VilaExplorer.domain;

import app.VilaExplorer.enums.TipoEntidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "puntuacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Puntuacion {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_puntuacion")
    private Long idPuntuacion;

    @Column(name = "puntuacion", nullable = false)
    private Integer puntuacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "id_entidad", nullable = false)
    private Long idEntidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entidad", nullable = false)
    private TipoEntidad tipoEntidad;
}
