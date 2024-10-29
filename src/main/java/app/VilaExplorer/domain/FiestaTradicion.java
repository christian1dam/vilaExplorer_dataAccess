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
    private Long idFiestaTradicion;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false, length = 255)
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false, foreignKey = @ForeignKey(name = "FK_fiesta_tradicion_usuario"))
    private Usuario autor;
}

