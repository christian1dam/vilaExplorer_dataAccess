package app.VilaExplorer.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaCreacion;
}
