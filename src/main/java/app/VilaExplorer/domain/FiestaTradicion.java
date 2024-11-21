package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Clase que representa una fiesta o tradición de la región.
 *
 * @version 1.0, 29/09/2021
 * @autor VilaExplorerAdmin
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fiesta_tradicion")
@Schema(description = "Fiesta o tradición de la región")
public class FiestaTradicion {

    @Schema(description = "Identificador de la fiesta o tradición", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fiesta_tradicion")
    private Long idFiestaTradicion;

    @Schema(description = "Nombre de la fiesta o tradición", example = "Carnaval de La Vila Joiosa", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Schema(description = "Fecha del evento", example = "Primer fin de semana de mayo", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Schema(description = "Descripción de la fiesta o tradición", example = "Una celebración con desfiles, disfraces y actividades culturales", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Schema(description = "URL de la imagen representativa de la fiesta o tradición", example = "https://example.com/images/carnaval.jpg", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "imagen", nullable = true)
    private String imagen;

    @Schema(description = "Imagen representativa en formato Base64", example = "data:image/jpeg;base64,...")
    @Transient
    private String imagenBase64;

    @Schema(description = "Indica si la fiesta o tradición está activa", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;


    @Schema(description = "Usuario autor que ingresó la información de la fiesta o tradición", requiredMode = RequiredMode.REQUIRED)
    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false, foreignKey = @ForeignKey(name = "FK_fiesta_tradicion_usuario"))
    private Usuario autor;

    // Método para convertir la imagen a Base64 y devolverla como String
    public String getImagenBase64() {
        Path imagePath = Paths.get(this.imagen);
        try {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            System.out.println("Error al leer la imagen: " + e.getMessage());
            return null;
        }
    }

    // Método para establecer la imagenBase64
    public void setImagenBase64FromPath() {
        this.imagenBase64 = getImagenBase64();
    }
}
