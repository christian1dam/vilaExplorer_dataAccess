package app.VilaExplorer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase que representa un rol dentro del sistema.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
@Schema(description = "Representación de un rol del sistema, el cual define permisos y acceso")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    @Schema(description = "Identificador único del rol", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idRol;

    @Column(name = "nombre", nullable = false)
    @NotBlank
    @Schema(description = "Nombre del rol", example = "Administrador", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    // Campo para indicar si el rol está activo (borrado logico)
    @Column(name = "activo", nullable = false)
    @NotNull
    @Schema(description = "Indica si el rol está activo para uso en el sistema", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activo = true;
}
