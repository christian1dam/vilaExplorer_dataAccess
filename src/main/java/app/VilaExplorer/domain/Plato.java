package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Clase que representa la entidad Plato en la base de datos
 *
 * @version 1.0
 * @autor VilaExplorerAdmin
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plato")
@Schema(description = "Representación de un plato en la base de datos")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plato_id")
    @Schema(description = "Identificador único del plato", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long platoId;

    @Column(name = "nombre", nullable = false)
    @NotBlank
    @Schema(description = "Nombre del plato", example = "Paella Valenciana", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    @NotBlank
    @Schema(description = "Descripción del plato", example = "Un delicioso plato típico a base de arroz.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Column(name = "ingredientes", nullable = false)
    @NotBlank
    @Schema(description = "Lista de ingredientes del plato", example = "Arroz, pollo, judía verde, azafrán", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ingredientes;

    @Column(name = "receta", nullable = false)
    @NotBlank
    @Schema(description = "Receta detallada del plato", example = "Primero sofría el pollo...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String receta;

    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado de aprobación del plato (true si ha sido aprobado)", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean estado = false; // Inicialmente falso hasta que sea aprobado

    @Column(name = "imagen_path")
    @Schema(description = "Ruta de la imagen del plato", example = "images/platos/paella.jpg")
    private String imagen;

    @Schema(description = "Imagen representativa en formato Base64", example = "data:image/jpeg;base64,...")
    @Transient
    private String imagenBase64;

    @ManyToOne
    @JoinColumn(name = "id_tipo_plato", nullable = false)
    @NotNull
    @Schema(description = "Tipo de plato al que pertenece", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoPlato tipoPlato;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false, foreignKey = @ForeignKey(name = "FK_plato_usuario_autor"))
    @NotNull
    @Schema(description = "Autor que creó el plato", requiredMode = Schema.RequiredMode.REQUIRED)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "id_aprobador", foreignKey = @ForeignKey(name = "FK_plato_usuario_aprobador"))
    @Schema(description = "Usuario que aprobó el plato")
    private Usuario aprobador; //Permite valores nulos hasta que sea aprobado el plato

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
