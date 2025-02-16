package app.VilaExplorer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa la entidad LugarInteres
 * @autor VilaExplorerAdmin
 * @version 1.0, 29/09/2021
 */

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lugar_interes")
@Schema(description = "Lugar de interés turístico")
public class LugarInteres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lugar_interes")
    @Schema(description = "Identificador único del lugar de interés", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idLugarInteres;

    @Column(name = "nombre_lugar", nullable = false)
    @NotBlank
    @Schema(description = "Nombre del lugar de interés", example = "Playa Paraíso", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreLugar;

    @Column(name = "descripcion", nullable = false)
    @NotBlank
    @Schema(description = "Descripción del lugar de interés", example = "Una hermosa playa con arena dorada y aguas cristalinas.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Column(name = "fecha_alta", nullable = false)
    @NotNull
    @Schema(description = "Fecha de alta del lugar de interés", example = "2023-06-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaAlta;

    //Agregado para calcular la puntuacion media
    @Column(name = "puntuacion_media_lugar")
    @Schema(description = "Puntuacion mecia de Lugar de interes", example = "4.5")
    private double puntuacionMediaLugar;

    @Column(name = "imagen", nullable = false)
    @NotBlank
    @Schema(description = "URL de la imagen representativa del lugar de interés", example = "https://example.com/imagen.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imagen;

    @Column(name = "activo", nullable = false)
    @NotNull
    @Schema(description = "Indica si el lugar de interés está activo", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_tipo_lugar", nullable = false)
    @NotNull
    @Schema(description = "Tipo de lugar de interés", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoLugarInteres tipoLugar;

    @OneToMany(mappedBy = "lugarInteres", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de coordenadas asociadas al lugar de interés")
    private List<Coordenadas> coordenadas;

}