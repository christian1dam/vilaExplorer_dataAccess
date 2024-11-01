package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Identificador del plato", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plato_id", nullable = false)
    private Long platoId;

    @Schema(description = "Nombre del plato", example = "Cordon Bleu")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Descripción del plato", example = "El cordón bleu es una fórmula que no ha sucumbido antes otras modernidades similares, como es el caso de los sanjacobos o los nuggets")
    @Column(nullable = false)
    private String descripcion;

    @Schema(description = "Ingredientes del plato", example = "Filetes de ternera, queso Jamón York...", required = true)
    @Column(nullable = false)
    private String ingredientes;

    @Schema(description = "Receta del plato", example = "1: " + "Salpimienta los filetes de ternera.\n" + "2" + "Coloca una loncha de jamón york y una de queso en cada filete...", required = true)
    @Column(nullable = false)
    private String receta;

    @Schema(description = "Estado del plato. Hace referencia a si se ha aprobado su publicación en la aplicación", example = "false", required = true)
    @Column(nullable = false)
    private boolean estado;

    @Schema(description = "Referencia hacia quién ha creado este plato", example = "Juan", required = true)
    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;

    @Schema(description = "Referencia hacia quién ha aprobado su publicación", example = "Administrador", required = true)
    @ManyToOne
    @JoinColumn(name = "id_aprobador", nullable = false)
    private Usuario aprobador;

    @Schema(description = "Identifica la categoría del plato", example = "Cocina clasica francesa", required = true)
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaPlato categoria;
}
