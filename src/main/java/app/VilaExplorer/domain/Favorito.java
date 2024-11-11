package app.VilaExplorer.domain;
import app.VilaExplorer.enums.TipoEntidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * Clase que representa la entidad Favorito.
 * Un favorito es una entidad que representa la relación entre un usuario y una entidad
 * que este ha marcado como favorita.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favoritos")
@Schema(description = "Representación de un favorito")
public class Favorito {

    @Schema(description = "Identificador del favorito", example = "1", requiredMode = RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorito")
    private Long idFavorito;

    @Schema(description = "Usuario asociado al favorito", requiredMode = RequiredMode.REQUIRED)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Schema(description = "Identificador de la entidad marcada como favorita", example = "3", requiredMode = RequiredMode.REQUIRED)
    @NotNull
    @Column(name = "id_entidad", nullable = false)
    private Long idEntidad;

    @Schema(description = "Tipo de entidad marcada como favorita", example = "PLATO", requiredMode = RequiredMode.REQUIRED)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entidad", nullable = false)
    private TipoEntidad tipoEntidad;
}
