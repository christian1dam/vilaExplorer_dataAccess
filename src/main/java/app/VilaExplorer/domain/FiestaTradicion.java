package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * Clase que representa una fiesta o tradición de la región.
 * @autor VilaExplorerAdmin
 * @version 1.0, 29/09/2021
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fiesta_tradicion")
@Schema(description = "Fiesta o tradición de la región")
public class FiestaTradicion {

    @Schema(description = "Identificador de la fiesta o tradición", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fiesta_tradicion")
    private Long idFiestaTradicion;

    @Schema(description = "Nombre de la fiesta o tradición", example = "Carnaval de La Vila Joiosa", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Schema(description = "Descripción de la fiesta o tradición", example = "Una celebración con desfiles, disfraces y actividades culturales", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Schema(description = "URL de la imagen representativa de la fiesta o tradición", example = "https://example.com/images/carnaval.jpg", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "imagen", nullable = false)
    private String imagen;

    @Schema(description = "Usuario autor que ingresó la información de la fiesta o tradición", requiredMode = RequiredMode.REQUIRED)
    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false, foreignKey = @ForeignKey(name = "FK_fiesta_tradicion_usuario"))
    private Usuario autor;
}

