package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Clase que representa la clave compuesta para la relación entre un usuario y un rol.
 * Esta clase se utiliza como identificador en la entidad {@link UsuarioRol}.
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Clave compuesta para representar la relación entre un usuario y un rol.")
public class UsuarioRolID implements Serializable {
    @Schema(description = "ID del usuario relacionado.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long usuario;
    @Schema(description = "ID del rol relacionado.", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long rol;
}
