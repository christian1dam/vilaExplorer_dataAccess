package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un tipo de plato en la aplicación de turismo gastronómico.
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@Entity
@Table(name = "tipo_plato")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representación de un tipo de plato, como arroces, tapas, postres, etc.")
public class TipoPlato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_plato")
    @Schema(description = "Identificador único del tipo de plato", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idTipoPlato;

    @Column(name = "nombre_tipo", nullable = false)
    @NotBlank
    @Schema(description = "Nombre del tipo de plato", example = "Arroz seco", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreTipo;

    // Campo para indicar si el tipo de plato está activo (borrado lógico)
    @Column(name = "activo", nullable = false)
    @Schema(description = "Indica si el tipo de plato está activo para borrado lógico", example = "true", defaultValue = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @Schema(description = "Categoría a la que pertenece el tipo de plato", requiredMode = Schema.RequiredMode.REQUIRED)
    private CategoriaPlato categoriaPlato;
}
