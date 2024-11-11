package app.VilaExplorer.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Clase que representa la relación entre un usuario y un rol.
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario_rol")
@IdClass(UsuarioRolID.class)
@Schema(description = "Entidad que representa la asignación de un rol a un usuario.")
public class UsuarioRol {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @JsonBackReference
    @Schema(description = "Usuario al cual se le asigna el rol.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @Schema(description = "Rol asignado al usuario.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Rol rol;

    @Column(name = "fecha_asignacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha y hora en que se asignó el rol al usuario.", example = "2024-11-10 13:45:00")
    private LocalDateTime fechaDeAsignacion;
}
