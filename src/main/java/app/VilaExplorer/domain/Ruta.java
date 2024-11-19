package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase que representa una ruta en el sistema.
 *
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ruta")
@Schema(description = "Representación de una ruta turística, que contiene un conjunto de coordenadas y está relacionada con un autor")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    @Schema(description = "Identificador único de la ruta", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idRuta;

    @Column(name = "nombre_ruta", nullable = false)
    @NotBlank
    @Schema(description = "Nombre de la ruta", example = "Ruta de los Monumentos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreRuta;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    @NotNull
    @Schema(description = "Autor que creó la ruta", requiredMode = Schema.RequiredMode.REQUIRED)
    private Usuario autor;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de coordenadas que conforman la ruta")
    private List<Coordenadas> coordenadas;

}
