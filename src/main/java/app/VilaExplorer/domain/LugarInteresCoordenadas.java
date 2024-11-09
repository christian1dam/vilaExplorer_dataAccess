package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@IdClass(LugarInteresCoordenadasID.class)
@Table(name = "lugar_interes_coordenadas")
@AllArgsConstructor
@NoArgsConstructor
public class LugarInteresCoordenadas {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_lugar_interes", referencedColumnName = "id_lugar_interes")
    private LugarInteres lugarInteres;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_coordenadas", referencedColumnName = "id_coordenadas")
    private Coordenadas coordenada;
}
