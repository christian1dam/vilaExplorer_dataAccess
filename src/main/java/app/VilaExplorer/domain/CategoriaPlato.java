package app.VilaExplorer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categoriaPlato")
public class CategoriaPlato {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id_categoria;

    @Column(nullable = false)
    private String nombreCategoria;

}
