package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_plato")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPlato {

    @Id
    @GeneratedValue(strategy = GenerationType .IDENTITY)
    @Column(name = "id_tipo_plato")
    private Long idTipoPlato;

    @Column(name = "nombre_tipo", nullable = false)
    private String nombreTipo;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaPlato categoriaPlato;
}
