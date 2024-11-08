package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plato")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plato_id")
    private Long platoId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "ingredientes", nullable = false)
    private String ingredientes;

    @Column(name = "receta", nullable = false)
    private String receta;

    @Column(name = "estado", nullable = false)
    private boolean estado = false; // Inicialmente falso hasta que sea aprobado

    @ManyToOne
    @JoinColumn(name = "id_tipo_plato", nullable = false)
    private TipoPlato tipoPlato;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false, foreignKey = @ForeignKey(name = "FK_plato_usuario_autor"))
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "id_aprobador", foreignKey = @ForeignKey(name = "FK_plato_usuario_aprobador"))
    private Usuario aprobador; //Permite valores nulos hasta que sea aprobado el plato
}
