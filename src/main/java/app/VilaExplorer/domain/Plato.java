package app.VilaExplorer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la entidad Plato en la base de datos
 * @autor VilaExplorerAdmin
 * @version 1.0
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
}
