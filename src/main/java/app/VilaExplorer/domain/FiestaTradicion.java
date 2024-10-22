package app.VilaExplorer.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fiesta_tradicion")
public class FiestaTradicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_fiestaTradicion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;
}
