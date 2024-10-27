package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_plato;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column
    private String ingredientes;

    @Column(nullable = false)
    private String receta;

    @Column
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "id_aprobador", nullable = false)
    private Usuario aprobador;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaPlato categoria;
}
