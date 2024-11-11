package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
    /**
     * Clase que representa un Articulo escrito pr un redactor
     * @author VilaExplorerAdmin
     * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articulo")
@Schema(description = "Representación de un artículo escrito por un redactor.")
public class Articulo {

    @Schema(description = "Identificador del artículo", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private Long idArticulo;

    @Schema(description = "Contenido del artículo", example = "Este es el contenido del artículo.", required = true)
    @Column(name = "contenido")
    private String contenido;

    @Schema(description = "Fecha de publicación del artículo", example = "2024-11-10")
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Schema(description = "Título del artículo", example = "Explorando La Vila Joiosa", required = true)
    @Column(name = "titulo")
    private String titulo;

    @Schema(description = "Autor del artículo", required = true)
    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false, foreignKey = @ForeignKey(name = "FK_articulo_usuario"))
    private Usuario autor;
}