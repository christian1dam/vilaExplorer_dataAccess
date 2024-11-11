package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un tipo de lugar de interés turístico.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipo_lugar_interes")
@Schema(description = "Representación de un tipo de lugar de interés, como parques, monumentos, playas, etc.")
public class TipoLugarInteres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_lugar")
    @Schema(description = "Identificador único del tipo de lugar de interés", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idTipoLugar;

    @Column(name = "nombre_tipo", nullable = false)
    @NotBlank
    @Schema(description = "Nombre del tipo de lugar de interés", example = "Parque Natural", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreTipo;
}
