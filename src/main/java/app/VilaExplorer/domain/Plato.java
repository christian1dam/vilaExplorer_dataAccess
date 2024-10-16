package app.VilaExplorer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long receta_id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String ingredientes;
    @Column(nullable = false)
    private String receta;
    @Column
    private boolean estado;
}
