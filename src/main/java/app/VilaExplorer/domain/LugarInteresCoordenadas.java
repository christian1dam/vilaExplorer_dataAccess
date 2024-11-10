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
/*
Dado que LugarInteresCoordenadas es una tabla de relación que vincula LugarInteres con Coordenadas,
estas no suele requerir un Service y ServiceImpl dedicados a menos que se necesiten operaciones específicas
directamente sobre esta tabla.

Generalmente, las interacciones con una tabla de relación como esta se gestionan indirectamente a través de los servicios
de LugarInteres y Coordenadas. Sin embargo, si necesitamos alguna funcionalidad específica, como añadir o
quitar coordenadas de un lugar de interés directamente desde la tabla de relación, entonces se podría justificar
 un Service y ServiceImpl para facilitar estas operaciones.
 */