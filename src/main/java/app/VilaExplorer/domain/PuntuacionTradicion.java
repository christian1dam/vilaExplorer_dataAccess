package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PuntuacionTradicionID.class)
public class PuntuacionTradicion {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_fiesta", nullable = false)
    private FiestaTradicion fiesta;

    @Column(nullable = false)
    private int puntuacion;
}