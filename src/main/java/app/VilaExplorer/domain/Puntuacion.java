package app.VilaExplorer.domain;

import app.VilaExplorer.enums.TipoEntidad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la entidad Puntuacion
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "puntuacion")
@Schema(description = "Representación de una puntuación")
public class Puntuacion {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_puntuacion")
    @Schema(description = "Identificador único de la puntuación", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idPuntuacion;

    @Column(name = "puntuacion", nullable = false)
    @NotNull
    @Min(1)
    @Max(5)
    @Schema(description = "Valor de la puntuación otorgada (entre 1 y 5)", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer puntuacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull
    @Schema(description = "Usuario que realiza la puntuación", requiredMode = Schema.RequiredMode.REQUIRED)
    private Usuario usuario;

    @Column(name = "id_entidad", nullable = false)
    @NotNull
    @Schema(description = "Identificador de la entidad que se está puntuando", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idEntidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entidad", nullable = false)
    @NotNull
    @Schema(description = "Tipo de entidad que se está puntuando (por ejemplo, lugar de interés, plato)", example = "LUGAR_INTERES", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoEntidad tipoEntidad;
}
