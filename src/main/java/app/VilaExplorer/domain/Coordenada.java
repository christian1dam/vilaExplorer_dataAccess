package app.VilaExplorer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore // Evita la recursividad al ignorar la relación inversa
    @ManyToOne
    @JoinColumn(name = "id_lugar_interes", nullable = false)
    private LugarInteres lugarInteres;
}
