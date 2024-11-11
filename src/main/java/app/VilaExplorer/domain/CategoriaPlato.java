package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una categoría de plato de la carta de un restaurante
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categoriaPlato")
@Schema(description = "Representación de una categoría de plato de la carta de un restaurante")
public class CategoriaPlato {

    @Schema(description = "Identificador de la categoría de plato", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private long idCategoriaPlato;

    @Schema(description = "Nombre de la categoría", example = "Arroces secos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "nombre_categoria")
    private String nombreCategoria;

    @Schema(description = "Indica si la categoría está activa", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "activo", nullable = false)
    private Boolean activo = true; // campo para indicar si la categoría está activa y que permita eliminarla lógicamente

}
