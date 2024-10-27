package app.VilaExplorer.domain;

import jakarta.persistence.*;

public class FiestaTradicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFiestaTradicion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 255)
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;
}
