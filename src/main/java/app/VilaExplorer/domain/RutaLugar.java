package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaLugar {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutaLugar;

    @ManyToOne
    @JoinColumn(name = "id_ruta", nullable = false)
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "id_lugar", nullable = false)
    private LugarInteres lugar;
}
