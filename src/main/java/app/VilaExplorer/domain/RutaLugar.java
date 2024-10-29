package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ruta_lugar")
public class RutaLugar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta_lugar")
    private Long idRutaLugar;

    @ManyToOne
    @JoinColumn(name = "id_ruta", nullable = false, foreignKey = @ForeignKey(name = "FK_ruta_lugar_ruta"))
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "id_lugar", nullable = false, foreignKey = @ForeignKey(name = "FK_ruta_lugar_lugar_interes"))
    private LugarInteres lugarInteres;
}