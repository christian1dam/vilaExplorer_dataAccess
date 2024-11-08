package app.VilaExplorer.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coordenadas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordenadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenadas")
    private Long idCoordenadas;

    @Column (name = "latitud", nullable = false)
    private Double latitud;

    @Column (name = "longitud", nullable = false)
    private Double longitud;
}
