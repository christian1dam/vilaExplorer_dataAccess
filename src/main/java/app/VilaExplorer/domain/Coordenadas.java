package app.VilaExplorer.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * Clase que representa la entidad Coordenadas
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coordenadas")
@Schema(description = "Representación de las coordenadas de un lugar de interés o de una ruta")
public class Coordenadas {

    @Schema(description = "Identificador único de las coordenadas", example = "1", requiredMode = RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenadas")
    private Long idCoordenadas;

    @Schema(description = "Latitud de las coordenadas", example = "38.536", requiredMode = RequiredMode.REQUIRED)
    @NotNull
    @Column (name = "latitud", nullable = false)
    private Double latitud;

    @Schema(description = "Longitud de las coordenadas", example = "-0.134", requiredMode = RequiredMode.REQUIRED)
    @NotNull
    @Column (name = "longitud", nullable = false)
    private Double longitud;

    @Schema(description = "Lugar de interés asociado con las coordenadas", example = "1")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_lugar_interes")
    private LugarInteres lugarInteres;

    @Schema(description = "Ruta asociada con las coordenadas", example = "1")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_ruta")
    private Ruta ruta;
}
