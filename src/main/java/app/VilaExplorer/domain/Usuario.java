package app.VilaExplorer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase que representa un Usuario en el sistema.
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @Schema(description = "Identificador único del usuario", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idUsuario;

    @Column(nullable = false)
    @NotBlank
    @Schema(description = "Nombre del usuario", example = "Juan Perez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email no válido")
    @Schema(description = "Correo electrónico único del usuario", example = "juan.perez@ejemplo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Column(nullable = false)
    @NotBlank
    @Schema(description = "Contraseña del usuario", example = "passwordSeguro123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del usuario en el sistema", example = "2023-11-10", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaCreacion;

    @Column(name = "activo", nullable = false)
    @Schema(description = "Indica si el usuario está activo", example = "true", defaultValue = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activo = true;

    // Relación muchos a muchos a través de UsuarioRol
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // FetchType.LAZY en lugar de EAGER
    @JsonManagedReference
    @Schema(description = "Lista de roles históricos asociados al usuario")
    private List<UsuarioRol> roles = new ArrayList<>();

    // Rol actual del usuario
    @ManyToOne
    @JoinColumn(name = "id_rol_actual")
    @Schema(description = "Rol actual asignado al usuario")
    private Rol rolActual;
}