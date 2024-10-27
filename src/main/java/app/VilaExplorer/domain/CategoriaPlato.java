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
public class CategoriaPlato {
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_categoria;
    @Column(nullable = false)
    private String nombre_categoria;
}
