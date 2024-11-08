package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lugarinteres_coordenadas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LugarInteresCoordenadas {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_lugar_interes", referencedColumnName = "id_lugar_interes")
    private LugarInteres lugarInteres;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_coordenada", referencedColumnName = "id_coordenada")
    private Coordenadas coordenada;
}
