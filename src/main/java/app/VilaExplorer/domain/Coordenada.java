package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coordenadas")
public class Coordenada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenada")
    private Long idCoordenada;

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    @OneToOne(mappedBy = "coordenada", cascade = CascadeType.ALL, orphanRemoval = true)
    private LugarInteres lugarInteres;
}
